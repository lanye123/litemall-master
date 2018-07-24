package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysConfigMapper;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName SysConfigService
 * Description TODO 下程序各项动态参数配置类
 * Author leiqiang
 * Date 2018/7/24 11:24
 * Version 1.0
 **/
@Service
public class SysConfigService {
  @Resource
  private SysConfigMapper sysConfigMapper;
}
