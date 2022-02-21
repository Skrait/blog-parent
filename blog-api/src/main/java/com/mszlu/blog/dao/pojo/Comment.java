package com.mszlu.blog.dao.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2022/2/14 14:47
 */
@Data
public class Comment implements Serializable
{
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
