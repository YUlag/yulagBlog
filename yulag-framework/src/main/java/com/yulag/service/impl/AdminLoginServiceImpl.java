package com.yulag.service.impl;

import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.LoginUser;
import com.yulag.domain.entity.User;
import com.yulag.service.AdminLoginService;
import com.yulag.utils.JwtUtil;
import com.yulag.utils.RedisCache;
import com.yulag.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 获取认证用户信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (authenticate == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        // 生成jwt
        String jwt = JwtUtil.createJWT(userId);

        redisCache.setCacheObject("login:" + userId, loginUser);

        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //TODO
//        Authentication autentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) autentication.getPrincipal();
//
//        Long userId = loginUser.getUser().getId();
//        redisCache.deleteObject("bloglogin:" + userId);
//
//        return ResponseResult.okResult();
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
