package com.mszlu.blog.vo.params;

import lombok.Data;

/**
 * Author Peekaboo
 * Date 2022/2/14 21:25
 */
@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
