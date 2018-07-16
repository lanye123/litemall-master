package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsResultMapper;
import org.linlinjava.litemall.db.domain.CsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName CsResultService
 * Description TODO
 * Author leiqiang
 * Date 2018/7/16 17:27
 * Version 1.0
 **/
@Service
public class CsResultService {
  @Resource
  private CsResultMapper csResultMapper;

  public CsResult getPicUrl(Integer testId, Integer account) {
   return csResultMapper.getPicUrl(testId,account);
  }
}
