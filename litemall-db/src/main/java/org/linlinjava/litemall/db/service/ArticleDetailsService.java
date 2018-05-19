package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleDetailsMapper;
import org.linlinjava.litemall.db.domain.ArticleDetails;
import org.linlinjava.litemall.db.domain.ArticleDetailsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleDetailsService {
    @Resource
    private ArticleDetailsMapper articleDetailsMapper;
    /**
     *@Author:lanye
     *@Description:用户浏览流水接口
     *@Date:23:24 2018/5/7
     */
    public void add(ArticleDetails articleDetails) {
        articleDetailsMapper.insertSelective(articleDetails);
    }

    public void update(ArticleDetails articleDetails) {
        articleDetailsMapper.updateByPrimaryKeySelective(articleDetails);
    }

    public List<ArticleDetails> selectList(Integer userId,Integer categoryId,Integer articleId,Integer notesId,String categoryIds) {
        ArticleDetailsExample example = new ArticleDetailsExample();
        ArticleDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(notesId)){
            criteria.andNotesIdEqualTo(notesId);
        }
        if(!StringUtils.isEmpty(categoryIds)){
            criteria.andCategoryIdsContainsTo(categoryIds);
        }
        criteria.example().setOrderByClause("create_date desc");

        return articleDetailsMapper.selectByExample(example);
    }

    public List<ArticleDetails> querySelective(Integer categoryId,Integer articleId,Integer notesId, Integer userId,String categoryIds,Integer page, Integer size, String sort, String order) {
        ArticleDetailsExample example = new ArticleDetailsExample();
        ArticleDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(notesId)){
            criteria.andNotesIdEqualTo(notesId);
        }
        if(!StringUtils.isEmpty(categoryIds)){
            criteria.andCategoryIdsContainsTo(categoryIds);
        }
        criteria.example().setOrderByClause("create_date desc");

        PageHelper.startPage(page, size);
        return articleDetailsMapper.selectByExample(example);
    }

    public int countSeletive(Integer userId, Integer categoryId, Integer articleId, Integer notesId,String categoryIds) {
        ArticleDetailsExample example = new ArticleDetailsExample();
        ArticleDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(notesId)){
            criteria.andNotesIdEqualTo(notesId);
        }
        if(!StringUtils.isEmpty(categoryIds)){
            criteria.andCategoryIdsContainsTo(categoryIds);
        }

        return (int) articleDetailsMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        articleDetailsMapper.deleteByPrimaryKey(id);
    }
}
