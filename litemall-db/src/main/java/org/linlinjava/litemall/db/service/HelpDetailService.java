package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.HelpDetailMapper;
import org.linlinjava.litemall.db.domain.HelpDetailExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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

    public int countByGoodsId(Integer goodsId) {
        HelpDetailExample example = new HelpDetailExample();
        HelpDetailExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }

        return (int)helpDetailMapper.countByExample(example);
    }
}
