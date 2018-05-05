package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LetterMapper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.domain.Letter;
import org.linlinjava.litemall.db.domain.LetterExample;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LetterService {
    @Resource
    private LetterMapper letterMapper;

    public Letter findById(Integer id) {
        return letterMapper.selectByPrimaryKey(id);
    }

    public void add(Letter letter) {
        letterMapper.insertSelective(letter);
    }

    public void update(Letter letter) {
        letterMapper.updateByPrimaryKeySelective(letter);
    }

    public List<Letter> querySelective(Integer userId, String title, Integer status, Integer page, Integer size, String sort, String order) {
        LetterExample example = new LetterExample();
        LetterExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(status) && "wxStatus".equals(order)){
            criteria.example().setOrderByClause("status");
        }
       // criteria.andIsViewNotEqualTo(0);

        PageHelper.startPage(page, size);
        return letterMapper.selectByExample(example);
    }

    public int countSeletive(Integer userId, String title, Integer status, Integer page, Integer size, String sort, String order) {
        LetterExample example = new LetterExample();
        LetterExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        //criteria.andIsViewNotEqualTo(0);

        return (int) letterMapper.countByExample(example);
    }

    public int count() {
        LetterExample example = new LetterExample();
        example.or().andIsViewNotEqualTo(0);

        return (int)letterMapper.countByExample(example);
    }

    public void hidden(Integer id) {
        Letter letter = letterMapper.selectByPrimaryKey(id);
        if(letter == null){
            return;
        }
        letter.setIsView(0);
        letterMapper.updateByPrimaryKey(letter);
    }

    public void deleteById(Integer id) {
        letterMapper.deleteByPrimaryKey(id);
    }
}
