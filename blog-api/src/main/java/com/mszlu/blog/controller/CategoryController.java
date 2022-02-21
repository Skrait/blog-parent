package com.mszlu.blog.controller;

import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 * Date 2022/2/15 8:57
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }
}
