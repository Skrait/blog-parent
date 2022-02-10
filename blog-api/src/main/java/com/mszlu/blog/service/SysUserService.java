package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/9 10:29
 */

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;

/**
 * @Auther Song Kang
 * @Date 2022/2/9
 */
public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);
}
