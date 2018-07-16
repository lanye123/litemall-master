package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsTitleMapper;
import org.linlinjava.litemall.db.domain.CsTitle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CsTitleService {
  @Resource
  private CsTitleMapper csTitleMapper;

  public CsTitle option(Integer pid) {
    return csTitleMapper.selectByPid(pid);
  }
}
