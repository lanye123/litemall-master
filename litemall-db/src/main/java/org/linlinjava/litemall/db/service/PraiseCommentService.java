package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.PraiseCommentMapper;
import org.linlinjava.litemall.db.domain.PraiseComment;
import org.linlinjava.litemall.db.domain.PraiseCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PraiseCommentService {
    @Resource
    private PraiseCommentMapper praiseCommentMapper;

    public Integer count(Integer id) {
        PraiseCommentExample example=new PraiseCommentExample();
        PraiseCommentExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andCommentIdEqualTo(id);
        }
        return (int)praiseCommentMapper.countByExample(example);
    }

    public void add(PraiseComment comment) {
        praiseCommentMapper.insertSelective(comment);
    }

    public List<PraiseComment> querySelective(Integer comment_id) {
        PraiseCommentExample example=new PraiseCommentExample();
        PraiseCommentExample.Criteria criteria=example.createCriteria();
        criteria.andCommentIdEqualTo(comment_id);
        return praiseCommentMapper.selectByExample(example);

    }

    public void update(PraiseComment comment1) {
        praiseCommentMapper.updateByPrimaryKey(comment1);
    }
}
