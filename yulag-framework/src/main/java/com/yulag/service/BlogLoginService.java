package com.yulag.service;

import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.User;

public interface BlogLoginService{
    ResponseResult login(User user);

    ResponseResult logout();
}
