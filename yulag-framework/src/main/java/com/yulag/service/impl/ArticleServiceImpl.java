package com.yulag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.constants.SystemConstants;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.dto.AddArticleDto;
import com.yulag.domain.dto.ArticleDto;
import com.yulag.domain.entity.Article;
import com.yulag.domain.entity.ArticleTag;
import com.yulag.domain.entity.Category;
import com.yulag.domain.vo.*;
import com.yulag.mapper.ArticleMapper;
import com.yulag.service.ArticleService;
import com.yulag.service.ArticleTagService;
import com.yulag.service.CategoryService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 获取正常状态的文章
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<Article> list = list(lambdaQueryWrapper);

        // redis里的才是最新的数据, 这里不要用mysql里的数据
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");

        list.stream().map(
                article -> {
                    Integer viewCount = cacheMap.get(article.getId().toString());
                    article.setViewCount(viewCount.longValue());
                    return article;
                }).collect(Collectors.toList());

        lambdaQueryWrapper.orderByDesc(Article::getViewCount);

        // 分页
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT, SystemConstants.ARTICLE_STATUS_SIZE);
        page.setRecords(list);
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

        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");

        for (Article article : articles){
            String categoryName = categoryService.getById(article.getCategoryId()).getName();
            article.setCategoryName(categoryName);
            // 数据库的浏览量每3分钟才刷新, redis缓存才是实时的
            Integer viewCount = cacheMap.get(article.getId().toString());
            article.setViewCount(viewCount.longValue());
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

    @Override
    public ResponseResult add(AddArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle, article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary, article.getSummary());

        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Article> articles = page.getRecords();

        //这里偷懒没写VO的转换 应该转换完在设置到最后的pageVo中

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(articles);
        return pageVo;
    }

    @Override
    public ArticleVo getInfo(Long id) {
        Article article = getById(id);

        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<Long> tags = articleTagService.list(queryWrapper).stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());

        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        articleVo.setTags(tags);

        return articleVo;
    }

    @Override
    public void edit(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
        //删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(articleTagLambdaQueryWrapper);
        //添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
    }
}
