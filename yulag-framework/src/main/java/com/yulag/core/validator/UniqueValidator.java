package com.yulag.core.validator;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yulag.annotation.UniqueField;
import com.yulag.annotation.UniqueType;
import com.yulag.utils.ReflectUtils;

import com.yulag.constants.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author xgz
 */
@Slf4j
public class UniqueValidator implements ConstraintValidator<UniqueType, Object> {

    /**
     * 是否强制校验
     */
    private boolean required;

    /**
     * 操作数据库的服务类
     **/
    private Class<? extends BaseMapper> mapper;

    private Class entity;
    private UniqueExtend extend;
    /**
     * 实体类中 主键名称
     * <p>
     */
    private String primaryKeyName;

    /**
     * 实体类中 主键值
     */
    private Object primaryKeyValue;

    @Override
    public void initialize(UniqueType anno) {
        this.required = anno.required();
        this.mapper = anno.mapper();
        if (null == mapper) {
            throw new RuntimeException("service不能为空");
        }
        this.entity = anno.entity();
        Class<? extends UniqueExtend> defaultVal = anno.extend();
        this.extend = getUniqueExtend(defaultVal);
    }

    /**
     * 返回true 表示验证成功 返回false 表示验证失败，会抛出异常
     **/
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (required) {
            BaseMapper bean = SpringUtil.getBean(mapper);
            if (null == bean) {
                throw new RuntimeException("Unique 注解的 mapper 必须是Bean");
            }
                return doValid(bean, obj, context);
        }
        return true;
    }

    private boolean doValid(BaseMapper mapper, Object obj, ConstraintValidatorContext context) {
        // 尝试获取表的主键名 和传递过来的主键值。通过是否有主键值判断是新增还是更新。
        getPrimaryNameAndValue(obj);

        Field[] fields = ReflectUtils.getFields(obj.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(UniqueField.class)) {
                UniqueField anno = field.getAnnotation(UniqueField.class);
                String message = anno.message();
                String name = field.getName();
                Class<? extends UniqueExtend> filedExtend = anno.extend();
                UniqueExtend uniqueExtendObj = getUniqueExtend(filedExtend);
                String dbField = toDbField(name);
                Object value = ReflectUtils.invokeGetter(obj, name);
                if (null == value) {
                    continue;
                }
                Object entity = ReflectUtils.newInstance(this.entity);
                AbstractWrapper wrapper = new QueryWrapper(entity).eq(true, dbField, value)
                        .ne(Objects.nonNull(primaryKeyValue), toDbField(primaryKeyName), primaryKeyValue);
                // 执行拓展
                try {
                    if (uniqueExtendObj == null) {
                        if (this.extend != null) {
                            wrapper = this.extend.apply(wrapper, entity, obj, field);
                        }
                    } else {
                        wrapper = uniqueExtendObj.apply(wrapper, entity, obj, field);
                    }
                } catch (Exception e) {
                    log.error("执行拓展出错！", e);
                    wrapper = new QueryWrapper(entity).eq(true, dbField, value)
                            .ne(Objects.nonNull(primaryKeyValue), toDbField(primaryKeyName), primaryKeyValue);
                }
                int i = mapper.selectCount(wrapper).intValue();
                if (i > 0) {
                    log.error(message);
                    //禁用默认message值,不禁用会在原有默认的message的基础上进行拼接
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取 拓展类对象
     * 优先在 容器中获取
     **/
    @Nullable
    private static UniqueExtend getUniqueExtend(Class<? extends UniqueExtend> extendClass) {
        UniqueExtend uniqueExtendObj = null;
        if (!UniqueExtend.class.equals(extendClass)) {
            uniqueExtendObj = SpringUtil.getBean(extendClass);
            if (null == uniqueExtendObj) {
                uniqueExtendObj = ReflectUtil.newInstance(extendClass);
            }
        }
        return uniqueExtendObj;
    }

    private void getPrimaryNameAndValue(Object obj) {
        Field[] fields = ReflectUtils.getFields(entity);
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableId.class)) {
                this.primaryKeyName = field.getName();
                primaryKeyValue = ReflectUtils.invokeGetter(obj, this.primaryKeyName);
                break;
            }
        }
    }

    private String toDbField(String name) {
        if (CharSequenceUtil.isBlank(name)) {
            throw new RuntimeException("要验证的字段名不能为空");
        } else {
            if (name.contains(SystemConstants.UNDERLINE)) {
                return name;
            } else {
                return CharSequenceUtil.toUnderlineCase(name);
            }
        }
    }
}