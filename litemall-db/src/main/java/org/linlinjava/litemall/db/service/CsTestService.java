package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CsTestMapper;
import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTestExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

  public List<CsTest> querySelective(String title, Integer type, Integer categoryId,Integer page, Integer size, String sort, String order) {
    CsTestExample example = new CsTestExample();
    CsTestExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(title)){
      criteria.andTitleLike("%" + title + "%");
    }
    if(!StringUtils.isEmpty(type)){
      criteria.andTypeEqualTo(type);
    }
    if(!StringUtils.isEmpty(categoryId)){
      criteria.andCategoryIdEqualTo(categoryId);
    }
    if(!StringUtils.isEmpty(order)){
      example.setOrderByClause(order);
    }

    if(page!=null && size!=null){
      PageHelper.startPage(page, size);
    }
    return csTestMapper.selectByExample(example);
  }

  public int countSelective(String title, Integer type, Integer categoryId) {
    CsTestExample example = new CsTestExample();
    CsTestExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(title)){
      criteria.andTitleLike("%" + title + "%");
    }
    if(!StringUtils.isEmpty(type)){
      criteria.andTypeEqualTo(type);
    }
    if(!StringUtils.isEmpty(categoryId)){
      criteria.andCategoryIdEqualTo(categoryId);
    }

    return (int) csTestMapper.countByExample(example);
  }

    public void add(CsTest csTest) {
        csTestMapper.insert(csTest);
    }

  public CsTest cascateDync(Integer id) {
    return csTestMapper.cascateDync(id);
  }
}
