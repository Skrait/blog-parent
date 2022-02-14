package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.ArticleVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Peekaboo
 * Date 2022/2/8 17:52
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private TagService tagService;

    @Resource
    private CategoryService categoryService;
    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //一次行丢进2个参数，先按指定排序然后按照时间进行倒序排列
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //这个records只是数据库数据,并不一定是我们想要的返回数据,因此可再新建一个VO对象
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records,true,true);

        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;

    }

    /**
     * 巧妙利用重载！！！！
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag, boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;

    }

    private ArticleVo copy(Article article,boolean isTag, boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
//      并不是所有接口都需要标签，作者信息
        if (isTag){
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (isAuthor){
            articleVo.setAuthor((sysUserService.findUserById(article.getAuthorId()).getNickname()));
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(sysUserService.findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            /**
             * 这里由于Category和Article在业务关系上并不是强绑定,
             * 因此对于Category我们单独创建一个Service层
             */
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        //last指拼接在租后
        queryWrapper.last("limit " + limit);
        //Sql: select id,title from article order by view_count desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }


    /**
     * 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Article> articleList  = articleMapper.listArchives();
        return Result.success(articleList);
    }


    /**
     * 查看文章详情
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 根据ID查询article,
         * 根据BodyId和CategoryId 做对应关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);

        /**
         * 查看完文章了，新增阅读数，有没有问题呢？
         * 查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
         * 更新 增加了此次接口的 耗时 如果一旦更新出问题,不能影响 查看文章的操作
         * 线程池  以把更新操作 扔到线程池中去执行，和主线程就不相关了
         */
        
        return Result.success(articleVo);
    }
}
