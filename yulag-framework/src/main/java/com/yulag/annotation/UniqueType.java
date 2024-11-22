package com.yulag.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yulag.core.validator.UniqueExtend;
import com.yulag.core.validator.UniqueValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {UniqueValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface UniqueType {
  Class<?>  entity();
  // 访问数据库的服务
  Class<? extends BaseMapper>  mapper();
  /**
   * 是否开启校验
   *
   * @return 是否强制校验的boolean值
   */
  boolean required() default true;
  String message() default "{数据库已存在}";
 
  Class<?>[] groups() default {};
 
  Class<? extends Payload>[] payload() default {};
  /** 不得更改默认值 **/
  Class<? extends UniqueExtend> extend() default UniqueExtend.class;
}