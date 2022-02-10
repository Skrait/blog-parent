package com.mszlu.blog.dao.dos;

import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/10 9:52
 * 数据库查询结果，非持久化对象
 */
@Data
public class Archives {
    private Integer year;

    private Integer month;

    private Long count;

}
