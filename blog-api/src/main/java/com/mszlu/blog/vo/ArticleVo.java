package com.mszlu.blog.vo;

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

        private Long id;

        private String title;

        //简介
        private String summary;

        private int commentCounts;

        private int viewCounts;

        private int weight;
        /**
         * 创建时间
         */
        private String createDate;

        private String author;

        private ArticleBodyVo body;

        private List<TagVo> tags;

        private CategoryVo category;


}
