package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.IntegretionDetailMapper;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.domain.IntegretionDetailExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IntegretionDetailService {
    @Resource
    private IntegretionDetailMapper integretionDetailMapper;

    public List<IntegretionDetail> querySelective(String userId) {
        IntegretionDetailExample example=new IntegretionDetailExample();
        example.or().andUserIdEqualTo(userId);
        example.setOrderByClause("create_date desc");
        return integretionDetailMapper.selectByExample(example);
    }

    public IntegretionDetail querySelective2(String userId, String dayStartString, String dayEndString,Integer type) {
        IntegretionDetailExample example=new IntegretionDetailExample();
        example.or().andUserIdEqualTo(userId).andCreateDateGreaterThanOrEqualTo(LocalDateTime.parse(dayStartString)).andCreateDateLessThanOrEqualTo(LocalDateTime.parse(dayEndString));
        if(type==null)
            example.or().andTypeIsNull();
        else
            example.or().andTypeEqualTo((byte)type.intValue());//勋章id
        return integretionDetailMapper.selectOneByExample(example);
    }

    public int countSeletive(String userId, Integer type) {
        IntegretionDetailExample example = new IntegretionDetailExample();
        IntegretionDetailExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo((byte)type.intValue());
        }

        return (int) integretionDetailMapper.countByExample(example);
    }

    public void add(IntegretionDetail integretionDetail) {
        integretionDetailMapper.insertSelective(integretionDetail);
    }

    public int days(IntegretionDetail integretionDetail){
        return integretionDetailMapper.days(integretionDetail);
    }

    //查询打卡时间离当前日期最近的7笔记录
    public List<IntegretionDetail> queryByLimit(String userId) {
        return integretionDetailMapper.queryByLimit(userId);
    }

    public Integer sumByUserid(String userId) {

        return integretionDetailMapper.sumByUserid(userId);
    }
}
