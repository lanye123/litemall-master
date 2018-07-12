package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CsTestMapper;
import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTestExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CsTestService {
  @Resource
  private CsTestMapper csTestMapper;

  public List<CsTest> list(Integer isHot) {
    CsTestExample example=new CsTestExample();
    CsTestExample.Criteria criteria=example.createCriteria();
    criteria.andDeletedEqualTo(0);
    if(isHot!=null)
      criteria.andIsHotEqualTo(1);
    criteria.example().setOrderByClause("sort_no asc");
    return csTestMapper.selectByExample(example);
  }

  public CsTest read(Integer id) {
    return csTestMapper.selectByPrimaryKey(id);
  }

  public CsTest cascate(Integer id) {
    return csTestMapper.cascate(id);
  }
}
