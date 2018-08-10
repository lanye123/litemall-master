package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.HelpOrderMapper;
import org.linlinjava.litemall.db.domain.HelpOrder;
import org.linlinjava.litemall.db.domain.HelpOrderExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName HelpOrderService
 * Description TODO 助力模块订单主表记录
 * Author leiqiang
 * Date 2018/8/7 11:14
 * Version 1.0
 **/
@Service
public class HelpOrderService {
    @Resource
    private HelpOrderMapper helpOrderMapper;

    public void addOrder(HelpOrder order) {
        helpOrderMapper.insertSelective(order);
    }

    public int countByOrderSn(Integer userId, String orderSn){
        HelpOrderExample example = new HelpOrderExample();
        example.or().andUserIdEqualTo(userId).andSnoEqualTo(orderSn);
        return (int)helpOrderMapper.countByExample(example);
    }

    public HelpOrder queryById(Integer userId, Integer goodsId) {
        HelpOrderExample example = new HelpOrderExample();
        HelpOrderExample.Criteria criteria=example.createCriteria();
        criteria.andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
       return helpOrderMapper.selectOneByExample(example);
    }

    public HelpOrder load(Integer orderId) {
        return helpOrderMapper.selectByPrimaryKey(orderId);
    }

    public void update(HelpOrder order) {
        helpOrderMapper.updateByPrimaryKey(order);
    }
}
