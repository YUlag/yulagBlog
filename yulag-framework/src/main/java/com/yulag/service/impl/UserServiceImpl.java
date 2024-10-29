package com.yulag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.User;
import com.yulag.mapper.UserMapper;
import com.yulag.service.UserService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.utils.SecurityUtils;
import com.yulag.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();

        User user = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    // 添加到数据库的密码不应该是明文
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return ResponseResult.okResult();
    }
}
