package com.yulag.annotation;

import com.yulag.core.validator.UniqueExtend;
import com.yulag.enums.AppHttpCodeEnum;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 
/**
 * 数据库唯一校验
 * 场景：比如要求 用户名唯一，但是数据库用户名没有设置唯一键。
 * @author xgz
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface UniqueField {
 
  /**
   * 是否开启校验
   *
   * @return 是否强制校验的boolean值
   */
  boolean required() default true;
  String message();

  /**
   * 拓展类  比如 SQL还需要拼接上 appId 字段进行查询
   * 不得更改extend的默认值
   * **/
  Class<? extends UniqueExtend> extend() default UniqueExtend.class;
}