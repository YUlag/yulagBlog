package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
