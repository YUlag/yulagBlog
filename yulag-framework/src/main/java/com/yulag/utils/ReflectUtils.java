package com.yulag.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static Field[] getFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    public static Object invokeGetter(Object obj, String fieldName) {
        try {
            Method getter = obj.getClass().getMethod("get" + capitalize(fieldName));
            return getter.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke getter for field: " + fieldName, e);
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of class: " + clazz.getName(), e);
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}