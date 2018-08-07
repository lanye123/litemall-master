package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.HelpOrderMapper;
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
}
