package com.mszlu.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2022/2/14 16:44
 */
@Data
public class UserVo implements Serializable {

    private String nickname;

    private String avatar;

    private String id;
}
