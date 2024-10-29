package com.yulag.controller;

import com.yulag.annotation.MySystemlog;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Article;
import com.yulag.mapper.ArticleMapper;
import com.yulag.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yulag.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/list")
    @MySystemlog(xxbusinessName = "查询所有文章")
    public List<Article> list() {
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    @MySystemlog(xxbusinessName = "查询浏览量前十的文章")
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @MySystemlog(xxbusinessName = "查询指定文章列表")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId, @RequestParam(required = false) String search) {
//        if (!search.isEmpty()){
//            List<ArticleListVo> articleListVos = articleMapper.searchList(search);
//            System.out.println(articleListVos.toString());
//            if (articleListVos.size()!=0) {
//                PageVo pageVo = new PageVo(articleListVos, (long) articleListVos.size());
//                List<ArticleListVo> list = pageVo.getRows();
//                for (ArticleListVo one : list) {
//                    Category category = categoryMapper.selectById(one.getId());
//                    one.setCategoryName(category.getName());
//                    one.setCategoryId(category.getId());
//                }
//
//                return ResponseResult.okResult(pageVo);
//            }
//
//        }
        ResponseResult result = articleService.articleList(pageNum, pageSize, categoryId);
        return result;
    }

    @GetMapping("/{id}")
    @MySystemlog(xxbusinessName = "查询文章详情")
    public ResponseResult getArticleDetail(@PathVariable Long id) {
        ResponseResult result = articleService.getArticleDetail(id);
        return result;
    }

    @PutMapping("/updateViewCount/{id}")
    @MySystemlog(xxbusinessName = "根据文章id从mysql查询文章")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
