package com.mszlu.blog.config;

import com.mszlu.blog.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.rmi.registry.Registry;

/**
 * Author Peekaboo
 * Date 2022/2/8 16:47
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置(即允许前端8080端口访问后端8088域名,这就是跨域)
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截类
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test");
        //registry.addInterceptor(loginInterceptor).excludePathPatterns("/login").excludePathPatterns("/register");

    }
}
