package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.TagMapper;
import com.mszlu.blog.dao.pojo.Tag;
import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;
import javafx.scene.control.Tab;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2022/2/9 9:58
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 根据文章ID查询标签列表
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
//        MP无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);

        return copyList(tags);
    }


    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tags){
        List<TagVo> tagVoList = new ArrayList<TagVo>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public Result hots(int limit) {
        /**
         * 最热文章评定
         * 1、标签所拥有文章数量最多 最热标签
         * 2、根据tag_id分组，计数，从大到小 排列 取前limit个
         */
        List<Long> tagIds = tagMapper.findHotTagId(limit);
        if (CollectionUtils.isEmpty(tagIds)){
            //返回一个空数组
            return Result.success(Collections.emptyList());
        }

        List<Tag> tagList =  tagMapper.findTagsByTagId(tagIds);
        return Result.success(tagList);
    }

    /**
     * 查询所有标签
     * @return
     */
    @Override
    public Result findAll() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }
}
