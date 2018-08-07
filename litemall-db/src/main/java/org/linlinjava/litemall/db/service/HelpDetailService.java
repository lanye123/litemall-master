package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.HelpDetailMapper;
import org.linlinjava.litemall.db.dao.HelpOrderMapper;
import org.linlinjava.litemall.db.domain.HelpDetail;
import org.linlinjava.litemall.db.domain.HelpDetailExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName HelpDetailService
 * Description TODO 助力模块订单从表参与人员信息记录
 * Author leiqiang
 * Date 2018/8/7 11:15
 * Version 1.0
 **/
@Service
public class HelpDetailService {
    @Resource
    private HelpDetailMapper helpDetailMapper;

    public void create(HelpDetail detail) {
        helpDetailMapper.insertSelective(detail);
    }

    public List<HelpDetail> list(Integer orderId) {
        HelpDetailExample example=new HelpDetailExample();
        HelpDetailExample.Criteria criteria=example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        example.setOrderByClause("create_date asc");
       return helpDetailMapper.selectByExample(example);
    }

    public HelpDetail validate(Integer userId, Integer orderId) {
        HelpDetailExample example=new HelpDetailExample();
        HelpDetailExample.Criteria criteria=example.createCriteria();
        criteria.andOrderIdEqualTo(orderId).andUserIdEqualTo(userId);
        return helpDetailMapper.selectOneByExample(example);
    }
}
