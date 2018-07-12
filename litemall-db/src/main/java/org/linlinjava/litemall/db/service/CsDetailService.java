package org.linlinjava.litemall.db.service;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.linlinjava.litemall.db.dao.CsDetailMapper;
import org.linlinjava.litemall.db.domain.CsDetail;
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
}
