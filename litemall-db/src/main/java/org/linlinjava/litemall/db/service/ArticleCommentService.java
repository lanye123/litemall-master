package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleCommentMapper;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<ArticleComment> querySelective(Integer article_id,String flag) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(article_id!=null)
            criteria.andArticleIdEqualTo(article_id);
        example.setOrderByClause("create_date desc");//按时间倒序排序
        if("0".equals(flag)){
            example.setOrderByClause("create_date desc");//按时间倒序排序
        }
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

    public List<ArticleComment> query(Integer articleId, String categoryName, Integer categoryId, String content, Integer fromUserid, Integer page, Integer size, String sort, String order) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryName)){
            criteria.andCategoryNameEqualTo(categoryName);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }
        if(page!=null&&size!=null){
            PageHelper.startPage(page, size);
        }
        return articleCommentMapper.selectByExample(example);
    }

    public int count(Integer articleId, String categoryName,Integer categoryId,String content,Integer fromUserid,Integer page, Integer size, String sort, String order) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryName)){
            criteria.andCategoryNameEqualTo(categoryName);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }

        return (int) articleCommentMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        articleCommentMapper.deleteByPrimaryKey(id);
    }

    public void update(ArticleComment articleComment) {
        articleCommentMapper.updateByPrimaryKeySelective(articleComment);
    }

    public ArticleComment queryById(Integer id) {
        return articleCommentMapper.selectByPrimaryKey(id);
    }
}
