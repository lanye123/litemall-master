package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CsTitleMapper;
import org.linlinjava.litemall.db.domain.CsTitle;
import org.linlinjava.litemall.db.domain.CsTitleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CsTitleService {
  @Resource
  private CsTitleMapper csTitleMapper;

  public List<CsTitle> querySelective(String title, Integer testId, Integer page, Integer size, String sort, String order) {
    CsTitleExample example = new CsTitleExample();
    CsTitleExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(title)){
      criteria.andTitleLike("%" + title + "%");
    }
    if(!StringUtils.isEmpty(testId)){
      criteria.andTestIdEqualTo(testId);
    }
    if(!StringUtils.isEmpty(order)){
      example.setOrderByClause(order);
    }

    if(page!=null && size!=null){
      PageHelper.startPage(page, size);
    }
    return csTitleMapper.selectByExample(example);
  }

  public int countSelective(String title, Integer testId) {
    CsTitleExample example = new CsTitleExample();
    CsTitleExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(title)){
      criteria.andTitleLike("%" + title + "%");
    }
    if(!StringUtils.isEmpty(testId)){
      criteria.andTestIdEqualTo(testId);
    }

    return (int) csTitleMapper.countByExample(example);
  }

  public CsTitle option(Integer pid) {
    return csTitleMapper.selectByPid(pid);
  }

  public void add(CsTitle csTitle) {
    csTitleMapper.insert(csTitle);
  }
}
