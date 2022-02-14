package com.mszlu.blog.vo;

import lombok.Data;


/**
 * Author Peekaboo
 * Date 2022/2/13 22:24
 */
@Data
public class CategoryVo {

    private String id;

    /**
     * 图标路径
     */
    private String avatar;

    /**
     * 类别名称
     */
    private String categoryName;

    private String description;


}
