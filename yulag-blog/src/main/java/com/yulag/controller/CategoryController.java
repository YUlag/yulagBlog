package com.yulag.controller;

import com.yulag.annotation.MySystemlog;
import com.yulag.domain.ResponseResult;
import com.yulag.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @MySystemlog(xxbusinessName = "获取分类列表")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();
    }
}
