package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.MedalMapper;
import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedalService {
    @Resource
    private MedalMapper medalMapper;
    /**
     *@Author:lanye
     *@Description:勋章实现接口
     *@Date:23:24 2018/5/7
     */
    public void add(Medal medal) {
        medalMapper.insertSelective(medal);
    }

    public void update(Medal medal) {
        medalMapper.updateByPrimaryKeySelective(medal);
    }

    public List<Medal> getMedal(Integer id) {
        MedalExample example = new MedalExample();
        MedalExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(id);
        }

        return medalMapper.selectByExample(example);
    }

    public List<Medal> querySelective(String name, Integer max, Integer min, Integer page, Integer size, String sort, String order) {
        MedalExample example = new MedalExample();
        MedalExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(max)){
            criteria.andMaxEqualTo(max);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo("%" + name + "%");
        }
        if(!StringUtils.isEmpty(min)){
            criteria.andMinEqualTo(min);
        }
        criteria.example().setOrderByClause("create_date desc");

        PageHelper.startPage(page, size);
        return medalMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer max, Integer min, Integer page, Integer size, String sort, String order) {
        MedalExample example = new MedalExample();
        MedalExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(max)){
            criteria.andMaxEqualTo(max);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo("%" + name + "%");
        }
        if(!StringUtils.isEmpty(min)){
            criteria.andMinEqualTo(min);
        }

        return (int) medalMapper.countByExample(example);
    }

    public void hidden(Integer id) {
        Medal medal = medalMapper.selectByPrimaryKey(id);
        if(medal == null){
            return;
        }
        medal.setIsView((byte)0);
        medalMapper.updateByPrimaryKey(medal);
    }

    public void deleteById(Integer id) {
        medalMapper.deleteByPrimaryKey(id);
    }

}
