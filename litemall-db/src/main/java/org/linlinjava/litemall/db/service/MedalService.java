package org.linlinjava.litemall.db.service;

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

}
