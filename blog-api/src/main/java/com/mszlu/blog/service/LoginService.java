package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/10 14:31
 */

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;

/**
 * @Auther Song Kang
 * @Date 2022/2/10
 */
public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     */
    Result logout(String token);
}
