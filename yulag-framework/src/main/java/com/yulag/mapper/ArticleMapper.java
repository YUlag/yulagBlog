package com.yulag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yulag.domain.entity.Article;
import com.yulag.vo.ArticleListVo;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleListVo> searchList(String search);
}
