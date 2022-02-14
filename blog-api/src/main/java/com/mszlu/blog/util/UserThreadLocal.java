package com.mszlu.blog.util;

import com.mszlu.blog.dao.pojo.SysUser;

/**
 * Author Peekaboo
 * Date 2022/2/13 14:46
 */
public class UserThreadLocal {

    //创建一个私有的构造方法，达到不能new的效果

    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}

