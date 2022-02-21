package com.mszlu.blog.dao.pojo;

import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/15 9:58
 * 文章——标签关联表实体类
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
