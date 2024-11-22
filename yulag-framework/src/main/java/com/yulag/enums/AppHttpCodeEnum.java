package com.yulag.enums;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(serializeEnumAsJavaBean = true)
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506, "发送的评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传jpg/png文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_Format_ERROR(510, "密码格式错误"),
    EMAIL_FORMAT_ERROR(511, "邮箱格式错误"),
    NICKNAME_EXIST(512, "昵称已经存在"),
    FILE_SIZE_ERROR(413, "文件大小不能超出2MB"),
    LOGIN_ERROR(505,"用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
