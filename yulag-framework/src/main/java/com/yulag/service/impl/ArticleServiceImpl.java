package com.yulag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.constants.SystemConstants;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Article;
import com.yulag.domain.entity.Category;
import com.yulag.mapper.ArticleMapper;
import com.yulag.service.ArticleService;
import com.yulag.service.CategoryService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.utils.RedisCache;
import com.yulag.vo.ArticleDetailVo;
import com.yulag.vo.ArticleListVo;
import com.yulag.vo.HotArticleVo;
import com.yulag.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<Article> list = list(lambdaQueryWrapper);

        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");

        list.stream().map(
                article -> {
                    Integer viewCount = cacheMap.get(article.getId().toString());
                    article.setViewCount(viewCount.longValue());
                    return article;
                }).collect(Collectors.toList());

        lambdaQueryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT, SystemConstants.ARTICLE_STATUS_SIZE);
        page(page, lambdaQueryWrapper);
        List<Article> articles = page.getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 当categoryId为空时,查询所有文章
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);

        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();

        for (Article article : articles){
            String categoryName = categoryService.getById(article.getCategoryId()).getName();
            article.setCategoryName(categoryName);
        }

        List<ArticleListVo> articlesVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articlesVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);

        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        articleDetailVo.setCategoryName(category.getName());

        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}
