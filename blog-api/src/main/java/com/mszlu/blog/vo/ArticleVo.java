package com.mszlu.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ArticleVo
 * @Description TODO
 * @Author 23389
 * @Date 2021/12/30 15:37
 * @Version 1.0
 */
@Data
public class ArticleVo {

        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

        private String title;

        //简介
        private String summary;

        private Integer commentCounts;

        private Integer viewCounts;

        private Integer weight;
        /**
         * 创建时间
         */
        private String createDate;

        private String author;

        private ArticleBodyVo body;

        private List<TagVo> tags;

        private CategoryVo category;


}
