package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LetterReplyMapper;
import org.linlinjava.litemall.db.domain.LetterReply;
import org.linlinjava.litemall.db.domain.LetterReplyExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LetterReplyService {
    @Resource
    private LetterReplyMapper letterReplyMapper;

    public LetterReply findById(Integer id) {
        return letterReplyMapper.selectByPrimaryKey(id);
    }

    public void add(LetterReply LetterReply) {
        letterReplyMapper.insertSelective(LetterReply);
    }

    public void update(LetterReply LetterReply) {
        letterReplyMapper.updateByPrimaryKeySelective(LetterReply);
    }

    public List<LetterReply> querySelective(Integer letterId, String content, Integer status, Integer page,  Integer size, String sort, String order) {
        LetterReplyExample example = new LetterReplyExample();
        LetterReplyExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(letterId)){
            criteria.andLetterIdEqualTo(letterId);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(status) && "wxCreateDate".equals(order)){
            criteria.example().setOrderByClause("create_date desc");
        }
        //criteria.andIsViewNotEqualTo(0);

        PageHelper.startPage(page, size);
        return letterReplyMapper.selectByExample(example);
    }

    public int countSeletive(Integer letterId, String content, Integer status, Integer page,  Integer size, String sort, String order) {
        LetterReplyExample example = new LetterReplyExample();
        LetterReplyExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(letterId)){
            criteria.andLetterIdEqualTo(letterId);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        //criteria.andIsViewNotEqualTo(0);

        return (int) letterReplyMapper.countByExample(example);
    }

    public int count() {
        LetterReplyExample example = new LetterReplyExample();
        example.or().andIsViewNotEqualTo(0);

        return (int)letterReplyMapper.countByExample(example);
    }

    public void hidden(Integer id) {
        LetterReply LetterReply = letterReplyMapper.selectByPrimaryKey(id);
        if(LetterReply == null){
            return;
        }
        LetterReply.setIsView(0);
        letterReplyMapper.updateByPrimaryKey(LetterReply);
    }

    public void deleteById(Integer id) {
        letterReplyMapper.deleteByPrimaryKey(id);
    }
}
