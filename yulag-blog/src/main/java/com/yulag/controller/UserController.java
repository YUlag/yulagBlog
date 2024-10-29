package com.yulag.controller;

import com.yulag.annotation.MySystemlog;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.User;
import com.yulag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    //UserService是我们在huanf-framework工程写的接口
    private UserService userService;

    @GetMapping("/userInfo")
    @MySystemlog(xxbusinessName = "获取用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @MySystemlog(xxbusinessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @MySystemlog(xxbusinessName = "注册用户")
    public ResponseResult register(@Valid @RequestBody User user){
        //注册功能
        return userService.register(user);
    }
}
