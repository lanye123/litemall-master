package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleCommentMapper;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleCommentExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;

    /**
     * @autor by leiqiang
     * @param article_id
     * @date 2018-5-7 15:51:12
     * @return
     */
    public List<ArticleComment> querySelective(Integer article_id) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(article_id!=null)
            criteria.andArticleIdEqualTo(article_id);
        example.setOrderByClause("create_date desc");//按时间倒序排序
        return articleCommentMapper.selectByExample(example);
    }

    /**
     * leiqiang
     * 文章评论数量
     * 2018-5-7 16:21:03
     * @param article_id
     * @return
     */
    public Long countSelective(Integer article_id) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(article_id!=null)
            criteria.andArticleIdEqualTo(article_id);
        return articleCommentMapper.countByExample(example);
    }

    public void add(ArticleComment comment) {
        articleCommentMapper.insertSelective(comment);
    }
}
