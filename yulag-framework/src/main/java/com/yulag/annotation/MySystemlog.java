package com.yulag.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 35238
 * @date 2023/7/30 0030 21:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySystemlog {
    //为controller提供接口的描述信息，用于'日志记录'功能
    String xxbusinessName();

}