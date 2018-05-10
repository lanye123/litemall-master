package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.PraiseCommentMapper;
import org.linlinjava.litemall.db.domain.PraiseComment;
import org.linlinjava.litemall.db.domain.PraiseCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
}
