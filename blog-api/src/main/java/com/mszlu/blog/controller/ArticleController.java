package com.mszlu.blog.controller;

import com.mszlu.blog.comon.aop.LogAnnotation;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.ArticleParam;
import com.mszlu.blog.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:32
 */
//JSON数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    @LogAnnotation(module="文章",operator="获取文章")
    public Result listArticle(@RequestBody PageParams pageParams){
//        int i = 10/0;
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章,首页标题
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章,首页标题
     * @return
     */
    @PostMapping("/new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        int limit = 5;
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    /**
     * 文章发布
     * @param articleParam
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
