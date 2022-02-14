package com.mszlu.blog.controller;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.util.UserThreadLocal;
import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Peekaboo
 * Date 2022/2/11 15:57
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);

        return Result.success(null);
    }
}
