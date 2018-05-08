package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleCollectionMapper;
import org.linlinjava.litemall.db.domain.ArticleCollection;
import org.linlinjava.litemall.db.domain.ArticleCollectionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCollectionService {
    @Resource
    private ArticleCollectionMapper articleCollectionMapper;
    /**
     *@Author:LeiQiang
     *@Description:图文-收藏接口实现
     *@Date:23:24 2018/5/4
     */
    public void add(ArticleCollection collection) {
        articleCollectionMapper.insertSelective(collection);
    }

    public void update(ArticleCollection collection) {
        articleCollectionMapper.updateByPrimaryKeySelective(collection);
    }

    public List<ArticleCollection> selectByUserId(Integer userId) {
        ArticleCollectionExample example = new ArticleCollectionExample();
        ArticleCollectionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        criteria.example().setOrderByClause("create_date");

        return articleCollectionMapper.selectByExample(example);
    }

    public List<ArticleCollection> querySelective(Integer articleId, Integer userId,Integer page, Integer size, String sort, String order) {
        ArticleCollectionExample example = new ArticleCollectionExample();
        ArticleCollectionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        criteria.example().setOrderByClause("create_date desc");

        PageHelper.startPage(page, size);
        return articleCollectionMapper.selectByExample(example);
    }

    public int countSeletive(Integer articleId, Integer userId,Integer page, Integer size, String sort, String order) {
        ArticleCollectionExample example = new ArticleCollectionExample();
        ArticleCollectionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }

        return (int) articleCollectionMapper.countByExample(example);
    }

    public void hidden(Integer id) {
        ArticleCollection articleCollection = articleCollectionMapper.selectByPrimaryKey(id);
        if(articleCollection == null){
            return;
        }
        articleCollection.setIsView(false);
        articleCollectionMapper.updateByPrimaryKey(articleCollection);
    }

    public void deleteById(Integer id) {
        articleCollectionMapper.deleteByPrimaryKey(id);
    }
}
