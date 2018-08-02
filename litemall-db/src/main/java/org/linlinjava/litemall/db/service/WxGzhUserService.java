package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxGzhUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName WxGzhUserService
 * Description TODO 微信公众号用户信息列表
 * Author leiqiang
 * Date 2018/8/2 10:23
 * Version 1.0
 **/
@Service
public class WxGzhUserService {
    @Resource
    private WxGzhUserMapper wxGzhUserMapper;

    //获取用户列表最后一个openid,id倒序排列取第一个

    //获取已关注用户列表
}
