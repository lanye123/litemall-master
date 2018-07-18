package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CsOptionMapper;
import org.linlinjava.litemall.db.domain.CsOption;
import org.linlinjava.litemall.db.domain.CsOptionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CsOptionService {
  @Resource
  private CsOptionMapper csOptionMapper;

  public List<CsOption> querySelective(String optionName,Integer testId,Integer titleId,Integer page, Integer limit, String sort, String order) {
    CsOptionExample example = new CsOptionExample();
    CsOptionExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(optionName)){
      criteria.andOptionNameLike("%" + optionName + "%");
    }
    if(!StringUtils.isEmpty(testId)){
      criteria.andTestIdEqualTo(testId);
    }
    if(!StringUtils.isEmpty(titleId)){
      criteria.andTitleIdEqualTo(titleId);
    }
    if(!StringUtils.isEmpty(order)){
      example.setOrderByClause(order);
    }

    if(page!=null && limit!=null){
      PageHelper.startPage(page, limit);
    }
    return csOptionMapper.selectByExample(example);
  }

  public int countSelective(String optionName,Integer testId,Integer titleId) {
    CsOptionExample example = new CsOptionExample();
    CsOptionExample.Criteria criteria = example.createCriteria();

    if(!StringUtils.isEmpty(optionName)){
      criteria.andOptionNameLike("%" + optionName + "%");
    }
    if(!StringUtils.isEmpty(testId)){
      criteria.andTestIdEqualTo(testId);
    }
    if(!StringUtils.isEmpty(titleId)){
      criteria.andTitleIdEqualTo(titleId);
    }

    return (int) csOptionMapper.countByExample(example);
  }

  public void deleteById(Integer id) {
    if(StringUtils.isEmpty(id)){
      return;
    }
    csOptionMapper.deleteByPrimaryKey(id);
  }

    public void add(CsOption csOption) {
        csOptionMapper.insert(csOption);
    }

    public CsOption findById(Integer id) {
        return csOptionMapper.selectByPrimaryKey(id);
    }

    public void update(CsOption csOption) {
        csOptionMapper.updateByPrimaryKeySelective(csOption);
    }
}
