package org.linlinjava.litemall.db.service;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.linlinjava.litemall.db.dao.CsDetailMapper;
import org.linlinjava.litemall.db.domain.CsDetail;
import org.linlinjava.litemall.db.domain.CsDetailExample;
import org.linlinjava.litemall.db.domain.CsTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CsDetailService {
  @Resource
  private CsDetailMapper csDetailMapper;

  public void addDetail(CsDetail detail) {
    csDetailMapper.insertSelective(detail);
  }

  public CsDetail retrieve(Integer userId, Integer testId, Integer titleId) {
    CsDetailExample example=new CsDetailExample();
    CsDetailExample.Criteria criteria=example.createCriteria();
    criteria.andUserIdEqualTo(userId).andTestIdEqualTo(testId).andTitleIdEqualTo(titleId);
    return csDetailMapper.selectOneByExample(example);
  }

  public void update(CsDetail detail1) {
    csDetailMapper.updateByPrimaryKey(detail1);
  }

  public Integer sumAccount(Integer testId, Integer userId) {
    return csDetailMapper.sumAccount(testId,userId);
  }
}
