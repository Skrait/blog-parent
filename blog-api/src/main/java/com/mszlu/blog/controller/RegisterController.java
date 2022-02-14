package com.mszlu.blog.controller;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author Peekaboo
 * Date 2022/2/11 8:45
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam LoginParam){
        //SSO 单点登录，后期可以把登录注册功能单独作为一个服务提出来
        return loginService.register(LoginParam);
    }
}
