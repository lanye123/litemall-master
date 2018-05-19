package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleCategoryMapper;
import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.domain.ArticleCategoryExample;
import org.linlinjava.litemall.db.domain.LitemallCategory;
import org.linlinjava.litemall.db.domain.LitemallCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
/**
 *@Author:LeiQiang
 *@Description:全部图文-分类列表实现
 *@Date:22:15 2018/5/4
 */
@Service
public class ArticleCategoryService {
    @Resource
    private ArticleCategoryMapper ArticleCategoryMapper;

    public List<ArticleCategory> queryAllList() {
        ArticleCategoryExample example=new ArticleCategoryExample();
        return ArticleCategoryMapper.selectByExample(example);
    }

    public List<ArticleCategory> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        ArticleCategoryExample example=new ArticleCategoryExample();
        ArticleCategoryExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        PageHelper.startPage(page, limit);
        return ArticleCategoryMapper.selectByExample(example);
    }

    public int countSelective(String name, Integer page, Integer limit, String sort, String order) {
        ArticleCategoryExample example=new ArticleCategoryExample();
        ArticleCategoryExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        PageHelper.startPage(page, limit);
        return (int) ArticleCategoryMapper.countByExample(example);
    }

    public void updateById(ArticleCategory category) {
        ArticleCategoryMapper.updateByPrimaryKeySelective(category);
    }

    public void add(ArticleCategory category) {
        ArticleCategoryMapper.insertSelective(category);
    }

    public ArticleCategory findById(Integer categoryId) {
        return ArticleCategoryMapper.selectByPrimaryKey(categoryId);
    }

    public void deleteById(Integer categoryId) {
        ArticleCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    public List<ArticleCategory> queryByList(Integer userId){
      return  ArticleCategoryMapper.queryByList(userId);
    }
}
