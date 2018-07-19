package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CsTestMapper;
import org.linlinjava.litemall.db.domain.CsResult;
import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTestExample;
import org.linlinjava.litemall.db.domain.CsTitle;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CsTestService {
  @Resource
  private CsTestMapper csTestMapper;
  @Resource
  private CsTitleService csTitleService;
  @Resource
  private CsResultService csResultService;

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
    return csTestMapper.selectByExample2(example);
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

  public CsTest findById(Integer id) {
    return csTestMapper.selectByPrimaryKey(id);
  }

  public void update(CsTest csTest) {
    csTestMapper.updateByPrimaryKeySelective(csTest);
  }

  public void deleteById(Integer id) {
    if(StringUtils.isEmpty(id)){
      return;
    }
    List<CsTitle> csTitleList = csTitleService.querySelective("",id,null,null,"","");
    for(CsTitle csTitle:csTitleList){
      csTitleService.deleteById(csTitle.getId());
    }
    List<CsResult> csResultList = csResultService.querySelective("",id,null,null,"","");
    for(CsResult csResult:csResultList){
      csResultService.deleteById(csResult.getId());
    }
    csTestMapper.deleteByPrimaryKey(id);
  }
}
