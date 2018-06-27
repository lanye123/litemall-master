package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SysCorpsMapper;
import org.linlinjava.litemall.db.domain.SysCorps;
import org.linlinjava.litemall.db.domain.SysCorpsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCorpsService {
    @Resource
    private SysCorpsMapper sysCorpsMapper;

    public SysCorps findById(Integer id) {
        return sysCorpsMapper.selectByPrimaryKey(id);
    }

    public SysCorps queryByBuId(Integer buId) {
        SysCorpsExample example = new SysCorpsExample();
        example.or().andBuIdEqualTo(buId);
        return sysCorpsMapper.selectOneByExample(example);
    }

    public void add(SysCorps sysCorps) {
        sysCorpsMapper.insert(sysCorps);
    }

    public void update(SysCorps sysCorps) {
        sysCorpsMapper.updateByPrimaryKeySelective(sysCorps);
    }

    public List<SysCorps> querySelective(String name, Integer userId, Integer buId, Integer page, Integer size, String sort, String order) {
        SysCorpsExample example = new SysCorpsExample();
        SysCorpsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(buId)){
            criteria.andBuIdEqualTo(buId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return sysCorpsMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer userId, Integer buId, Integer page, Integer size, String sort, String order) {
        SysCorpsExample example = new SysCorpsExample();
        SysCorpsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(buId)){
            criteria.andBuIdEqualTo(buId);
        }

        return (int) sysCorpsMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        sysCorpsMapper.deleteByPrimaryKey(id);
    }

}
