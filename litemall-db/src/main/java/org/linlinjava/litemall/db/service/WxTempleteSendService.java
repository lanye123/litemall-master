package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxTempleteSendMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName WxTempleteSendService
 * Description TODO 模板消息待发送消息列表
 * Author leiqiang
 * Date 2018/8/2 11:31
 * Version 1.0
 **/
@Service
public class WxTempleteSendService {
    @Resource
    private WxTempleteSendMapper wxTempleteSendMapper;
}
