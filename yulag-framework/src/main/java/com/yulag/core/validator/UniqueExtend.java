package com.yulag.core.validator;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import java.lang.reflect.Field;
 
public interface UniqueExtend {
 
 
  /**
   * 应用拓展 修改查询SQL
   * @author xgz
   * @date 2023/10/28
   * @param wrapper 默认的 warpper
   * @param entity 表对应的实体对象，注意这是一个空对象。
   *               但是可以通过反射获取 表的字段名等信息。
   * @param obj Bo对象，有各个前端传入的值
   * @param field 当前需要校验的字段，是Bo对象标注了@UniqueField 注解的字段。
   * @return com.baomidou.mybatisplus.core.conditions.Wrapper
  **/
  AbstractWrapper apply(AbstractWrapper wrapper, Object entity, Object obj, Field field);
}