package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.FeedbackMapper;
import org.linlinjava.litemall.db.domain.Feedback;
import org.linlinjava.litemall.db.domain.FeedbackExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    public Feedback findById(Integer id) {

        return feedbackMapper.selectByPrimaryKey(id);
    }

    public void update(Feedback feedback) {
        feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    public List<Feedback> queryBySelective(String title,String content,Integer type,Integer userId, Integer page, Integer limit, String sort, String order) {
        FeedbackExample example=new FeedbackExample();
        FeedbackExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(content))
            criteria.andContentLike("%" + content + "%");
        if(!StringUtils.isEmpty(type))
            criteria.andTypeEqualTo(type);
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        if(page!=null && limit!=null){
            PageHelper.startPage(page,limit);
        }
        return feedbackMapper.selectByExample(example);
    }

    public int countSelective(String title,String content,Integer type,Integer userId, Integer page, Integer limit, String sort, String order) {
        FeedbackExample example=new FeedbackExample();
        FeedbackExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(content))
            criteria.andContentLike("%" + content + "%");
        if(!StringUtils.isEmpty(type))
            criteria.andTypeEqualTo(type);
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);

        return (int) feedbackMapper.countByExample(example);
    }

    public void add(Feedback feedback) {
        feedbackMapper.insertSelective(feedback);
    }

    public void deleteById(Integer id) {
        feedbackMapper.deleteByPrimaryKey(id);
    }
}
