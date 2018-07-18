package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsJieguoMapper;
import org.linlinjava.litemall.db.domain.CsJieguo;
import org.linlinjava.litemall.db.domain.CsJieguoExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName CsJieguoService
 * Description TODO
 * Author leiqiang
 * Date 2018/7/18 15:35
 * Version 1.0
 **/
@Service
public class CsJieguoService {
  @Resource
  private CsJieguoMapper csJieguoMapper;

  public CsJieguo queryById(Integer testId, Integer userId) {
    CsJieguoExample example=new CsJieguoExample();
    CsJieguoExample.Criteria criteria=example.createCriteria();
    if(testId!=null)
    criteria.andTestIdEqualTo(testId);
    if(userId!=null)
    criteria.andUserIdEqualTo(userId);
    return csJieguoMapper.selectOneByExample(example);
  }

  public void add(CsJieguo csjg) {
    csJieguoMapper.insertSelective(csjg);
  }

  public void update(CsJieguo jg) {
    csJieguoMapper.updateByPrimaryKey(jg);
  }
}
