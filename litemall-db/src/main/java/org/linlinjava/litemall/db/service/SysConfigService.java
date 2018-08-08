package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysConfigMapper;
import org.linlinjava.litemall.db.domain.SysConfig;
import org.linlinjava.litemall.db.domain.SysConfigExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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

  public List<SysConfig> queryByCode(String code) {
    SysConfigExample example = new SysConfigExample();
    SysConfigExample.Criteria criteria = example.createCriteria();
    if(!StringUtils.isEmpty(code)){
      criteria.andCodeEqualTo(code);
    }else{
      return null;
    }
    return sysConfigMapper.selectByExample(example);
  }
}
