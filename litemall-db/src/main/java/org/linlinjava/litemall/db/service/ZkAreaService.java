package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ZkAreaMapper;
import org.linlinjava.litemall.db.domain.ZkArea;
import org.linlinjava.litemall.db.domain.ZkAreaExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZkAreaService {
    @Resource
    private ZkAreaMapper zkAreaMapper;

    public ZkArea findById(Integer id) {
        return zkAreaMapper.selectByPrimaryKey(id);
    }

    public void add(ZkArea zkArea) {
        zkAreaMapper.insert(zkArea);
    }

    public void update(ZkArea zkArea) {
        zkAreaMapper.updateByPrimaryKeySelective(zkArea);
    }

    public List<ZkArea> querySelective(String areaName, Integer page, Integer size, String sort, String order) {
        ZkAreaExample example = new ZkAreaExample();
        ZkAreaExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(areaName)){
            criteria.andAreaNameLike("%" + areaName + "%");
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return zkAreaMapper.selectByExample(example);
    }

    public int countSeletive(String areaName,Integer page, Integer size, String sort, String order) {
        ZkAreaExample example = new ZkAreaExample();
        ZkAreaExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(areaName)){
            criteria.andAreaNameLike("%" + areaName + "%");
        }

        return (int) zkAreaMapper.countByExample(example);
    }

    public List<ZkArea> queryAll() {
        ZkAreaExample example=new ZkAreaExample();
        example.or().andDeletedEqualTo(0);
        example.setOrderByClause("status");
        return zkAreaMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        zkAreaMapper.deleteByPrimaryKey(id);
    }
}
