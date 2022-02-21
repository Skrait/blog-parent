package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CommentsMapper;
import com.mszlu.blog.dao.pojo.Comment;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.util.UserThreadLocal;
import com.mszlu.blog.vo.CommentVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;
import com.mszlu.blog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2022/2/14 14:50
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private SysUserService sysUserService;

    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {
        //通过ThreadLocal获取当前用户
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());//设置当前时间戳
        comment.setAuthorId(sysUser.getId());
        Long parentId = commentParam.getParent();
        if (parentId == null || parentId == 0){
            comment.setLevel(1);
        }else {
            //由于限制最多二级评论,因此这里Level值无非也就是2了
            comment.setLevel(2);
        }
        comment.setParentId(parentId == null ? 0 : parentId);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentsMapper.insert(comment);
        return Result.success(null);
    }

    /**
     * 根据文章id查看评论内容(包括整个Vo信息)
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1、根据文章id在comment表中查询评论内容
         * 2、根据作者id查询作者信息
         * 3、判断，if level=1则代表存在子评论
         * 4、如果有，根据评论id进行查询(parent_id)
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);

    }

    /**
     * 利用For循环遍历和BeanUtils.copyProperties将Comment转换成CommentVo
     */
    private List<CommentVo> copyList(List<Comment> comments){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
                commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //给剩余的author、childrens、toUser
        //查询作者Vo信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (level == 1){
             Long id = comment.getId();
             List<CommentVo> commentVoList = findCommentsByParentId(id);
             commentVo.setChildrens(commentVoList);
        }
        //给谁评论
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(authorId);
            commentVo.setToUser(userVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentsMapper.selectList(queryWrapper));
    }
}
