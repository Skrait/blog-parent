package com.mszlu.blog.service;
import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;

/**
 * @Auther Song Kang
 * @Date 2022/2/13
 */
public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    /**
     *
     * @return
     */
    Result findAll();
}
