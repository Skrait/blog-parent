package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/14 14:49
 */

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.CommentParam;

/**
 * @Auther Song Kang
 * @Date 2022/2/14
 */
public interface CommentsService {

    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
