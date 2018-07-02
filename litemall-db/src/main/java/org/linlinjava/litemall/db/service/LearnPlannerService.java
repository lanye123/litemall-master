package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LearnPlannerMapper;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.linlinjava.litemall.db.domain.LearnPlannerExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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

    public void deleteById(Integer id) {
        if(id == null){
            return;
        }
        LearnPlanner planner = plannerMapper.selectByPrimaryKey(id);
        if(planner == null){
            return;
        }
        plannerMapper.deleteByPrimaryKey(id);
    }

    public void update(LearnPlanner planner) {
        plannerMapper.updateByPrimaryKeySelective(planner);
    }

    public LearnPlanner findById(Integer id) {
        return plannerMapper.selectByPrimaryKey(id);
    }

    public List<LearnPlanner> querySelective(String telephone, String openid,Integer deptId,Integer buId,Integer corpsId,Integer transitionId,Integer page, Integer size, String sort, String order) {
        LearnPlannerExample example = new LearnPlannerExample();
        LearnPlannerExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(telephone)){
            criteria.andTelephoneLike("%" + telephone + "%");
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenidEqualTo(openid);
        }
        if(!StringUtils.isEmpty(deptId)){
            criteria.andBuIdEqualTo(deptId);
        }
        if(!StringUtils.isEmpty(buId)){
            criteria.andBuIdEqualTo(buId);
        }
        if(!StringUtils.isEmpty(corpsId)){
            criteria.andCorpsIdEqualTo(corpsId);
        }
        if(!StringUtils.isEmpty(transitionId)){
            criteria.andTransitionIdEqualTo(transitionId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return plannerMapper.selectByExample2(example);
    }

    public List<LearnPlanner> querySelective2(String telephone, String openid,Integer deptId,Integer buId,Integer corpsId,Integer transitionId,Integer page, Integer size, String sort, String order) {
        LearnPlannerExample example = new LearnPlannerExample();
        LearnPlannerExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(telephone)){
            criteria.andLTelephoneLike("%" + telephone + "%");
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenidEqualTo(openid);
        }
        if(!StringUtils.isEmpty(deptId)){
            criteria.andLBuIdEqualTo(deptId);
        }
        if(!StringUtils.isEmpty(buId)){
            criteria.andLBuIdEqualTo(buId);
        }
        if(!StringUtils.isEmpty(corpsId)){
            criteria.andLCorpsIdEqualTo(corpsId);
        }
        if(!StringUtils.isEmpty(transitionId)){
            criteria.andTransitionIdEqualTo(transitionId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return plannerMapper.selectByExample2(example);
    }

    public int countSeletive(String telephone, String openid,Integer deptId,Integer buId,Integer corpsId,Integer transitionId, Integer page, Integer size, String sort, String order) {
        LearnPlannerExample example = new LearnPlannerExample();
        LearnPlannerExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(telephone)){
            criteria.andTelephoneLike("%" + telephone + "%");
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenidEqualTo(openid);
        }
        if(!StringUtils.isEmpty(deptId)){
            criteria.andDeptIdEqualTo(deptId);
        }
        if(!StringUtils.isEmpty(buId)){
            criteria.andBuIdEqualTo(buId);
        }
        if(!StringUtils.isEmpty(corpsId)){
            criteria.andCorpsIdEqualTo(corpsId);
        }
        if(!StringUtils.isEmpty(transitionId)){
            criteria.andTransitionIdEqualTo(transitionId);
        }

        return (int) plannerMapper.countByExample(example);
    }
}
