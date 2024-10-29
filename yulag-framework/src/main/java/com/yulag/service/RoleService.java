package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
