package com.mszlu.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Author Peekaboo
 * Date 2022/2/14 9:53
 */
@Component
public class ThreadService {

    /**
     * (@Async注解)扔到线程池运行，不会影响原有的主线程比如会等待5秒,
     * 需要在线程池里做个配置,即ThreadPoolConfig类
     */
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        //为了在多线程环境保证数据一致性,即保证了线程安全
        queryWrapper.eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleUpdate,queryWrapper);
        try {
            //睡眠5秒钟
            Thread.sleep(5000);
            System.out.println("更新完成了....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
