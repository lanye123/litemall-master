package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LearnPlannerMapper;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.linlinjava.litemall.db.domain.LearnPlannerExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LearnPlannerService {
    @Resource
    private LearnPlannerMapper plannerMapper;

    public void add(LearnPlanner planner) {
        if(planner == null || StringUtils.isEmpty(planner.getOpenid())){
            return;
        }
        LearnPlannerExample example = new LearnPlannerExample();
        example.or().andOpenidEqualTo(planner.getOpenid());
        if(plannerMapper.selectOneByExample(example) != null) {
            return;
        }
        plannerMapper.insertSelective(planner);
    }

    public LearnPlanner queryByOpenid(String openid) {
        LearnPlannerExample example = new LearnPlannerExample();
        example.or().andOpenidEqualTo(openid);
        return plannerMapper.selectOneByExample(example);
    }
}
