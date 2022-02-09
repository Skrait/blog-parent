package com.mszlu.blog.dao.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:20
 */
@Data
public class SysUser implements Serializable {
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}