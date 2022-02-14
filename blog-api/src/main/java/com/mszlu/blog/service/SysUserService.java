package com.mszlu.blog.service;/**
 * Author Peekaboo
 * Date 2022/2/9 10:29
 */

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.ArticleBodyVo;
import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;

import java.util.List;

/**
 * @Auther Song Kang
 * @Date 2022/2/9
 */
public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAcount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    ArticleBodyVo findArticleBodyById(Long bodyId);

}
