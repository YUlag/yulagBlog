package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Category;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}
