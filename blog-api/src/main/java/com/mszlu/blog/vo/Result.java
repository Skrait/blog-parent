package com.mszlu.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:41
 * 定义统一返回值
 */
@Data
@AllArgsConstructor
public class Result {
    /**是否成功*/
    private boolean sussess;
    /**编码*/
    private int code;
    /**信息*/
    private String msg;
    /**数据*/
    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }

}
