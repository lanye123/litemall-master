package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleCollectionMapper;
import org.linlinjava.litemall.db.domain.ArticleCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
