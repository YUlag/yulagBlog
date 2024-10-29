package com.yulag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.domain.entity.ArticleTag;
import com.yulag.mapper.ArticleTagMapper;
import com.yulag.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
