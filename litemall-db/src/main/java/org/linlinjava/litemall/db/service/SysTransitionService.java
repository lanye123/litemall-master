package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SysTransitionMapper;
import org.linlinjava.litemall.db.domain.SysTransition;
import org.linlinjava.litemall.db.domain.SysTransitionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysTransitionService {
    @Resource
    private SysTransitionMapper sysTransitionMapper;

    public SysTransition findById(Integer id) {
        return sysTransitionMapper.selectByPrimaryKey(id);
    }

    public List<SysTransition> queryByCorpsId(Integer corpsId) {
        SysTransitionExample example = new SysTransitionExample();
        if(!StringUtils.isEmpty(corpsId)){
            example.or().andCorpsIdEqualTo(corpsId);
        }
        return sysTransitionMapper.selectByExample(example);
    }

    public void add(SysTransition sysTransition) {
        sysTransitionMapper.insert(sysTransition);
    }

    public void update(SysTransition sysTransition) {
        sysTransitionMapper.updateByPrimaryKeySelective(sysTransition);
    }

    public List<SysTransition> querySelective(String name, Integer userId, Integer corpsId, Integer page, Integer size, String sort, String order) {
        SysTransitionExample example = new SysTransitionExample();
        SysTransitionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(corpsId)){
            criteria.andCorpsIdEqualTo(corpsId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return sysTransitionMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer userId, Integer corpsId, Integer page, Integer size, String sort, String order) {
        SysTransitionExample example = new SysTransitionExample();
        SysTransitionExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(corpsId)){
            criteria.andCorpsIdEqualTo(corpsId);
        }

        return (int) sysTransitionMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        sysTransitionMapper.deleteByPrimaryKey(id);
    }

}
