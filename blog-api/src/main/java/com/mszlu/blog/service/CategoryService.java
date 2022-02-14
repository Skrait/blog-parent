package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/13 22:49
 */

import com.mszlu.blog.vo.CategoryVo;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2022/2/13
 */
public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
