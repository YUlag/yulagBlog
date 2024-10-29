package com.yulag.controller;

import com.yulag.annotation.MySystemlog;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.User;
import com.yulag.enums.AppHttpCodeEnum;
import com.yulag.exception.SystemException;
import com.yulag.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @MySystemlog(xxbusinessName = "用户登录")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @MySystemlog(xxbusinessName = "用户退出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
