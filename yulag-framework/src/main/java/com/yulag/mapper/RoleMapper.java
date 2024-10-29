package com.yulag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yulag.domain.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
