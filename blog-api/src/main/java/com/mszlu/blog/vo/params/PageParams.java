package com.mszlu.blog.vo.params;

import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:38
 */
@Data
public class PageParams {


    private int page = 1;


    private int pageSize = 10;
}
