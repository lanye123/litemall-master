package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleReplyMapper;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.domain.ArticleReplyExample;
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

    public List<ArticleReply> querySelective2(Integer commentId, Integer replyId, String replyType, String content, Integer fromUserid, Integer toUserid,String startDate, String endDate,String nickName,Integer type, Integer page, Integer size, String sort, String order) {
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
            criteria.andAContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(nickName)){
            criteria.andCNickNameLike("%" + nickName + "%");
        }
        if(!StringUtils.isEmpty(startDate)){
            criteria.andACreateDateGreaterThanOrEqualTo(startDate);
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andACreateDateLessThanOrEqualTo(endDate);
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order+" desc");
        }
        if(!StringUtils.isEmpty(type)){
            if(type==0){
                criteria.andCAccountIsNotNull();
            }else if(type == 1){
                criteria.andCAccountIsNull();
            }
        }

        PageHelper.startPage(page, size);
        return articleReplyMapper.selectByExample2(example);
    }

    public int countSelective(Integer commentId, Integer replyId, String replyType, String content, Integer fromUserid,Integer toUserid,String startDate, String endDate,String nickName,Integer type,Integer page, Integer size, String sort, String order) {
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
        if(!StringUtils.isEmpty(startDate)){
            criteria.andACreateDateGreaterThanOrEqualTo(startDate);
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andACreateDateLessThanOrEqualTo(endDate);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andAContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(nickName)){
            criteria.andCNickNameLike("%" + nickName + "%");
        }
        if(!StringUtils.isEmpty(type)){
            if(type==0){
                criteria.andCAccountIsNotNull();
            }else if(type == 1){
                criteria.andCAccountIsNull();
            }
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

    public ArticleReply queryById(Integer id) {
        return articleReplyMapper.selectByPrimaryKey(id);
    }
}
