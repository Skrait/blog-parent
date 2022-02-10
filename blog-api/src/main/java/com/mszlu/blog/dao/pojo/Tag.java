package com.mszlu.blog.dao.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:20
 */
@Data
public class Tag implements Serializable {
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 标签
     */
    private String tagName;
}
