package com.mszlu.blog.dao.mapper;/**
 * Author Peekaboo
 * Date 2022/2/8 17:29
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2022/2/8
 */
@Component
public interface TagMapper extends BaseMapper<Tag> {


    List<Long> findHotTagId(int limit);

    List<Tag> findTagsByArticleId(@Param("articleId") Long articleId);

    List<Tag> findTagsByTagId(List<Long> tagIds);
}
