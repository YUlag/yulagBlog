package com.yulag.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.yulag.annotation.UniqueField;
import com.yulag.annotation.UniqueType;
import com.yulag.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
@UniqueType(entity = User.class, mapper = UserMapper.class, required = true)
public class User {
    @TableId(type = IdType.AUTO)
    //主键@TableId
    private Long id;

    //用户名
    @NotBlank(message = "USERNAME_NOT_NULL")
    @UniqueField(message = "USERNAME_EXIST")
    private String userName;
    //昵称
    @NotBlank(message = "NICKNAME_NOT_NULL")
    @UniqueField(message = "NICKNAME_EXIST")
    private String nickName;
    //密码
    @Pattern(regexp = "^(\\w){6,12}$", message = "PASSWORD_Format_ERROR")
    private String password;
    //邮箱
    @Email(message = "EMAIL_FORMAT_ERROR")
    @UniqueField(message = "EMAIL_EXIST")
    private String email;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    @TableField(exist = false)
    private Long[] roleIds;
}