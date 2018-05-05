package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleCategoryMapper;
import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.domain.ArticleCategoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCategoryService {
    @Resource
    private ArticleCategoryMapper ArticleCategoryMapper;

    /**
    *@Author:LeiQiang
    *@Description:全部图文-分类列表实现
    *@Date:22:15 2018/5/4
    */
    public List<ArticleCategory> queryAllList() {
        ArticleCategoryExample example=new ArticleCategoryExample();
        return ArticleCategoryMapper.selectByExample(example);
    }
}
