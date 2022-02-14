package com.mszlu.blog.service;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;

/**
 * Author Peekaboo
 * Date 2022/2/10 14:31
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

    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
