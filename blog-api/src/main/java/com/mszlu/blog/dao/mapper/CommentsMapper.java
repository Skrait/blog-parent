package com.mszlu.blog.dao.mapper;/**
 * Author Peekaboo
 * Date 2022/2/14 16:35
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Comment;
import org.springframework.stereotype.Component;

/**
 * @Auther Song Kang
 * @Date 2022/2/14
 */
@Component
public interface CommentsMapper extends BaseMapper<Comment> {
}
