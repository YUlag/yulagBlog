package com.yulag.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySystemlog {
    //为controller提供接口的描述信息，用于'日志记录'功能
    String xxbusinessName();

}