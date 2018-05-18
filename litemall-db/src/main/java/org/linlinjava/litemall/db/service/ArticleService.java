package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleMapper;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.domain.ArticleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    @Resource
    private ArticleMapper articleMapper;
/**
    *@Author:LeiQiang
    *@Description:全部图文模块列表接口实现
    *@Date:22:45 2018/5/4
    */
    public List<Article> querySelective(String categoryIds,String flag) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(categoryIds))
            criteria.andCategoryIdsContainsTo(categoryIds);
        //criteria.andStatusNotEqualTo(1);//0启用1禁用
        //人气排序
        if(!StringUtils.isEmpty(flag)&&flag.equals("reader"))
            criteria.example().setOrderByClause("reader desc");
        //时间排序倒序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date1"))
            criteria.example().setOrderByClause("create_date desc");
        //时间排序正序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date2"))
            criteria.example().setOrderByClause("create_date asc");
        return articleMapper.selectByExample(example);
    }


    public Article findById(Integer article_id) {

        return articleMapper.selectByPrimaryKey(article_id);
    }
/**
    *@Author:LeiQiang
    *@Description:图文-用户收藏后阅读数量+1
    *@Date:23:34 2018/5/4
    */
    public void update(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
    }

    public List<Article> queryBySelective(String title,String author,Integer articleId, Integer page, Integer limit, String sort, String order) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(author))
            criteria.andTitleLike("%" + author + "%");
        if(!StringUtils.isEmpty(articleId))
            criteria.andArticleIdEqualTo(articleId);
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        return articleMapper.selectByExample(example);
    }

    public int countSelective(String title, String author, Integer articleId,Integer page, Integer limit, String sort, String order) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(author))
            criteria.andTitleLike("%" + author + "%");
        if(!StringUtils.isEmpty(articleId))
            criteria.andArticleIdEqualTo(articleId);
        return (int) articleMapper.countByExample(example);
    }

    public void add(Article article) {
        articleMapper.insertSelective(article);
    }

    public void deleteById(Integer articleId) {
        articleMapper.deleteByPrimaryKey(articleId);
    }

    public void updateById(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
    }
}
