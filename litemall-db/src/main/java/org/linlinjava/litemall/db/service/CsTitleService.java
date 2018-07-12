package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsTitleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CsTitleService {
  @Resource
  private CsTitleMapper csTitleMapper;
}
