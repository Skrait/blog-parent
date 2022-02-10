package com.mszlu.blog.controller;

import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 * Date 2022/2/9 14:18
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Resource
    private TagService tagService;

    @GetMapping("hot")
    public Result hot(){
        //定义最热的6个标签
        int limit = 6;
        return tagService.hots(limit);
    }
}
