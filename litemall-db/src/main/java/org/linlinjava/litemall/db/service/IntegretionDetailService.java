package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.IntegretionDetailMapper;
import org.linlinjava.litemall.db.domain.Integretion;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.domain.IntegretionDetailExample;
import org.linlinjava.litemall.db.domain.IntegretionExample;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IntegretionDetailService {
    @Resource
    private IntegretionDetailMapper IntegretionDetailMapper;

    public List<IntegretionDetail> querySelective(String userId) {
        IntegretionDetailExample example=new IntegretionDetailExample();
        example.or().andUserIdEqualTo(userId);
        example.setOrderByClause("create_date desc");
        return IntegretionDetailMapper.selectByExample(example);
    }

    public IntegretionDetail querySelective2(String userId, String dayStartString, String dayEndString,Integer type) {
        IntegretionDetailExample example=new IntegretionDetailExample();
        example.or().andUserIdEqualTo(userId).andCreateDateGreaterThanOrEqualTo(LocalDateTime.parse(dayStartString)).andCreateDateLessThanOrEqualTo(LocalDateTime.parse(dayEndString));
        if(type==null)
            example.or().andTypeIsNull();
        else
            example.or().andTypeEqualTo((byte)type.intValue());//勋章id
        return IntegretionDetailMapper.selectOneByExample(example);
    }

    public void add(IntegretionDetail integretionDetail) {
        IntegretionDetailMapper.insertSelective(integretionDetail);
    }

    public int days(IntegretionDetail integretionDetail){
        return IntegretionDetailMapper.days(integretionDetail);
    }
}
