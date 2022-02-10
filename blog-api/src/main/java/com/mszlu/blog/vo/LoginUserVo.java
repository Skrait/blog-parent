package com.mszlu.blog.vo;

import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/10 16:22
 * 存放获取用户信息Vo
 */
@Data
public class LoginUserVo {
    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}
