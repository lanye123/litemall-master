package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleCategoryStatMapper;
import org.linlinjava.litemall.db.domain.ArticleCategoryStat;
import org.linlinjava.litemall.db.domain.ArticleCategoryStatExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCategoryStatService {
    @Resource
    private ArticleCategoryStatMapper articleCategoryStatMapper;

    public ArticleCategoryStat findById(Integer id) {

        return articleCategoryStatMapper.selectByPrimaryKey(id);
    }

    public void update(ArticleCategoryStat ArticleCategoryStat) {
        articleCategoryStatMapper.updateByPrimaryKeySelective(ArticleCategoryStat);
    }

    public List<ArticleCategoryStat> queryBySelective(Integer categoryId,Integer article_id,Integer page, Integer limit, String sort, String order) {
        ArticleCategoryStatExample example=new ArticleCategoryStatExample();
        ArticleCategoryStatExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(categoryId))
            criteria.andCategoryIdEqualTo(categoryId);
        if(!StringUtils.isEmpty(article_id))
            criteria.andArticleIdEqualTo(article_id);
        return articleCategoryStatMapper.selectByExample(example);
    }

    public int countSelective(Integer categoryId,Integer article_id,Integer page, Integer limit, String sort, String order) {
        ArticleCategoryStatExample example=new ArticleCategoryStatExample();
        ArticleCategoryStatExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(categoryId))
            criteria.andCategoryIdEqualTo(categoryId);
        if(!StringUtils.isEmpty(article_id))
            criteria.andArticleIdEqualTo(article_id);
        return (int) articleCategoryStatMapper.countByExample(example);
    }

    public void add(ArticleCategoryStat ArticleCategoryStat) {
        articleCategoryStatMapper.insertSelective(ArticleCategoryStat);
    }

    public void deleteById(Integer articleId) {
        articleCategoryStatMapper.deleteByPrimaryKey(articleId);
    }

    public void deleteByExample(Integer articleId) {
        ArticleCategoryStatExample example=new ArticleCategoryStatExample();
        ArticleCategoryStatExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(articleId))
            criteria.andArticleIdEqualTo(articleId);
        articleCategoryStatMapper.deleteByExample(example);
    }

    public void updateById(ArticleCategoryStat ArticleCategoryStat) {
        articleCategoryStatMapper.updateByPrimaryKeySelective(ArticleCategoryStat);
    }

}
