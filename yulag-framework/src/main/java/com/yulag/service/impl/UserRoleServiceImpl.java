package com.yulag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.domain.entity.UserRole;
import com.yulag.mapper.UserRoleMapper;
import com.yulag.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
