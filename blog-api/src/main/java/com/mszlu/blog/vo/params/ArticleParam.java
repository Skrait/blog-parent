package com.mszlu.blog.vo.params;

import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.TagVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2022/2/15 9:42
 */
@Data
public class ArticleParam implements Serializable {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
