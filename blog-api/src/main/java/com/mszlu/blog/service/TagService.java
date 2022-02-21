package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/9 9:55
 */

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2022/2/9
 */
public interface TagService {

    /**
     * 根据文章ID查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 返回最热的limit条标签
     * @param limit
     * @return
     */
    Result hots(int limit);


    Result findAll();
}
