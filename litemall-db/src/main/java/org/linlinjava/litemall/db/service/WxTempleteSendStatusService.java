package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxTempleteSendStatusMapper;
import org.linlinjava.litemall.db.domain.WxTempleteSendStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName WxTempleteSendStatusService
 * Description TODO
 * Author leiqiang
 * Date 2018/8/2 17:10
 * Version 1.0
 **/
@Service
public class WxTempleteSendStatusService {
    @Resource
    private WxTempleteSendStatusMapper wxTempleteSendStatusMapper;

    public void create(WxTempleteSendStatus sendStatus){
        wxTempleteSendStatusMapper.insertSelective(sendStatus);
    }
}
