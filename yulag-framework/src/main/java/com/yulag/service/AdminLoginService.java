package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.User;

public interface AdminLoginService{
    ResponseResult login(User user);

    ResponseResult logout();
}
