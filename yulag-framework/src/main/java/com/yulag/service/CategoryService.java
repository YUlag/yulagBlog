package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Category;
import com.yulag.domain.vo.CategoryVo;
import com.yulag.domain.vo.PageVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
