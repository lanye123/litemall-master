package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CsResultMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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

    public List<CsResult> querySelective(String title, Integer testId, Integer page, Integer size, String sort, String order) {
        CsResultExample example = new CsResultExample();
        CsResultExample.Criteria criteria = example.createCriteria();

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
        return csResultMapper.selectByExample(example);
    }

    public int countSelective(String title, Integer testId) {
        CsResultExample example = new CsResultExample();
        CsResultExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(testId)){
            criteria.andTestIdEqualTo(testId);
        }

        return (int) csResultMapper.countByExample(example);
    }

    public void add(CsResult csResult) {
        csResultMapper.insert(csResult);
    }

    public CsResult findById(Integer id) {
        return csResultMapper.selectByPrimaryKey(id);
    }

    public void update(CsResult csResult) {
        csResultMapper.updateByPrimaryKeySelective(csResult);
    }

    public void deleteById(Integer id) {
        if(StringUtils.isEmpty(id)){
            return;
        }
        csResultMapper.deleteByPrimaryKey(id);
    }
}
