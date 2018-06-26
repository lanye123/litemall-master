package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LearnPlannerMapper;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LearnPlannerService {
    @Resource
    private LearnPlannerMapper plannerMapper;

    public void add(LearnPlanner planner) {
        plannerMapper.insertSelective(planner);
    }
}
