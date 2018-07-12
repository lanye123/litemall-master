package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsOptionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CsOptionService {
  @Resource
  private CsOptionMapper csOptionMapper;
}
