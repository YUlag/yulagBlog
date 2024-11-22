package com.yulag.handler.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yulag.domain.ResponseResult;
import com.yulag.enums.AppHttpCodeEnum;
import com.yulag.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@ControllerAdvice //对controller层的增强
//@ResponseBody

//或者用下面一个注解代替上面的两个注解
@RestControllerAdvice

//使用Lombok提供的Slf4j注解，实现日志功能
@Slf4j

//全局异常处理。最终都会在这个类进行处理异常
public class GlobalExceptionHandler {

    //SystemException是我们写的类。用户登录的异常交给这里处理
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){

        //打印异常信息，方便我们追溯问题的原因。{}是占位符，具体值由e决定
        log.error("出现了异常! {}",e);

        //从异常对象中获取提示信息封装，然后返回。ResponseResult是我们写的类
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    //其它异常交给这里处理
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){

        //打印异常信息，方便我们追溯问题的原因。{}是占位符，具体值由e决定
        log.error("出现了异常! {}",e);

        //从异常对象中获取提示信息封装，然后返回。ResponseResult、AppHttpCodeEnum是我们写的类
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());//枚举值是500
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseResult paramExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        AppHttpCodeEnum result = null;

        if (exceptions.hasErrors()) {
            // 只取第一个错误
            ObjectError error = exceptions.getAllErrors().get(0);
            String defaultMessage = error.getDefaultMessage();

            try {
                Field field = AppHttpCodeEnum.class.getField(defaultMessage);
                result = (AppHttpCodeEnum) field.get(null);
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        // 将所有错误信息拼接成一个字符串返回
        if (result != null) {
            return ResponseResult.errorResult(result.getCode(), result.getMsg());
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}