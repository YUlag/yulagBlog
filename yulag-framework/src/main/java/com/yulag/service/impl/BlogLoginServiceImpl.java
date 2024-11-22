package com.yulag.service.impl;

import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.LoginUser;
import com.yulag.domain.entity.User;
import com.yulag.service.BlogLoginService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.utils.JwtUtil;
import com.yulag.utils.RedisCache;
import com.yulag.domain.vo.BlogUserLoginVo;
import com.yulag.domain.vo.UserInfoVo;
import com.yulag.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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

        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();

        redisCache.deleteObject("bloglogin:" + userId);

        return ResponseResult.okResult();
    }
}
