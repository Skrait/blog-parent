package com.mszlu.blog.comon.aop;/**
 * Author Peekaboo
 * Date 2022/2/15 14:47
 */

import java.lang.annotation.*;

/**
 * @Auther Song Kang
 * @Date 2022/2/15
 * Type 代表可以放在类上面,METHOD代表可以放在方法上
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";


}
