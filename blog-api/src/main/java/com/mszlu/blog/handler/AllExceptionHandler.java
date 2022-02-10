package com.mszlu.blog.handler;

import com.mszlu.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author Peekaboo
 * Date 2022/2/9 17:07
 * @ControllerAdvice对加了@Controller注解的方法进行拦截处理，底层采用AOP实现
 */
@Slf4j
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody //放回JSON数据
    public Result doException(Exception e){
//        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
