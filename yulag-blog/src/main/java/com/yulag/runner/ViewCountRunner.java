package com.yulag.runner;

import com.yulag.domain.entity.Article;
import com.yulag.mapper.ArticleMapper;
import com.yulag.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    //用于操作redis
    private RedisCache redisCache;

    @Autowired
    //用于操作数据库的article表
    private ArticleMapper articleMapper;

    @Override
    //下面的写法是stream流+方法引用+Lambda
    public void run(String... args) throws Exception {
        //查询数据库中的博客信息，注意只需要查询id、viewCount字段的博客信息
        List<Article> articles = articleMapper.selectList(null);//为null即无条件，表示查询所有
        Map<String, Integer> viewCountMap = articles.stream()
                //由于我们需要key、value的数据，所有可以通过stream流

                //下面toMap方法的第一个参数用了方法引用，第二个参数用了Lambda
                //.collect(Collectors.toMap(Article::getId, article -> article.getViewCount().intValue()));

                //由于上面那行Article::getId返回值是Long类型，而我们需要String类型，为了方便转换类型，我们要写成Lambda表达式
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));


        //把查询到的id作为key，viewCount作为value，一起存入Redis
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
