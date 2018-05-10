package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleReplyMapper;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.domain.ArticleReplyExample;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleReplyService {
    @Resource
    private ArticleReplyMapper articleReplyMapper;

    public List<ArticleReply> querySelective(Integer commentId, Integer replyId, String replyType, String content, Integer fromUserid, Integer toUserid, Integer page, Integer size, String sort, String order) {
        ArticleReplyExample example = new ArticleReplyExample();
        ArticleReplyExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(replyType)){
            criteria.andReplyTypeLike("%"+replyType+"%");
        }
        if(!StringUtils.isEmpty(replyId)){
            criteria.andReplyIdEqualTo(replyId);
        }
        if(!StringUtils.isEmpty(commentId)){
            criteria.andCommentIdEqualTo(commentId);
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(toUserid)){
            criteria.andToUseridEqualTo(toUserid);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order+" desc");
        }

        PageHelper.startPage(page, size);
        return articleReplyMapper.selectByExample(example);
    }

    public int countSelective(Integer commentId, Integer replyId, String replyType, String content, Integer fromUserid,Integer toUserid,Integer page, Integer size, String sort, String order) {
        ArticleReplyExample example = new ArticleReplyExample();
        ArticleReplyExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(replyType)){
            criteria.andReplyTypeLike("%"+replyType+"%");
        }
        if(!StringUtils.isEmpty(replyId)){
            criteria.andReplyIdEqualTo(replyId);
        }
        if(!StringUtils.isEmpty(commentId)){
            criteria.andCommentIdEqualTo(commentId);
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(toUserid)){
            criteria.andToUseridEqualTo(toUserid);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }

        return (int) articleReplyMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        articleReplyMapper.deleteByPrimaryKey(id);
    }

    public void update(ArticleReply articleReply) {
        articleReplyMapper.updateByPrimaryKeySelective(articleReply);
    }

    public void add(ArticleReply reply) {
        articleReplyMapper.insertSelective(reply);
    }

    public Integer countReply(Integer id) {
        ArticleReplyExample example = new ArticleReplyExample();
        ArticleReplyExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andCommentIdEqualTo(id);
        }
        return (int)articleReplyMapper.countByExample(example);
    }

    public List<ArticleReply> queryByCommentId(Integer id) {
        ArticleReplyExample example = new ArticleReplyExample();
        ArticleReplyExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andCommentIdEqualTo(id);
        }
        return articleReplyMapper.selectByExample(example);
    }
    //自定义sql试图查询示例
    public List<ArticleReply> queryByList(ArticleReply reply){

        return  articleReplyMapper.queryByCommentId(reply);
    }
}
