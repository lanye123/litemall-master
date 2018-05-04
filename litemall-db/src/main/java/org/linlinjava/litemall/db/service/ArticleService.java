package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleMapper;
import org.linlinjava.litemall.db.domain.Article;
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

    public List<Article> querySelective(Integer category_id) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(category_id))
            criteria.andCategoryIdLike("%" + category_id + "%");
        //criteria.andStatusNotEqualTo(1);//0启用1禁用
        return articleMapper.selectByExample(example);
    }

    public Article findById(Integer artitle_id) {

        return articleMapper.selectByPrimaryKey(artitle_id);
    }
}
