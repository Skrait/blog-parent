package com.mszlu.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2022/2/8 23:10
 */
@Data
public class TagVo implements Serializable {

    private Long id;

    private String tagName;
}
