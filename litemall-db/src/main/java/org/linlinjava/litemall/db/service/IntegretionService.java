package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.IntegretionMapper;
import org.linlinjava.litemall.db.domain.Integretion;
import org.linlinjava.litemall.db.domain.IntegretionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IntegretionService {
    @Resource
    private IntegretionMapper IntegretionMapper;

    public List<Integretion> querySelective(String userId) {
        IntegretionExample example=new IntegretionExample();
        example.or().andUserIdEqualTo(userId);
        example.setOrderByClause("order by create_date desc");
        return IntegretionMapper.selectByExample(example);
    }

    public List<Integretion> querySelective2(String userId, String dayStartString, String dayEndString) {
        IntegretionExample example=new IntegretionExample();
        example.or().andUserIdEqualTo(userId).andCreateDateGreaterThanOrEqualTo(LocalDateTime.parse(dayStartString)).andCreateDateLessThanOrEqualTo(LocalDateTime.parse(dayEndString));
        return IntegretionMapper.selectByExample(example);
    }
}
