package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SysBuMapper;
import org.linlinjava.litemall.db.domain.SysBu;
import org.linlinjava.litemall.db.domain.SysBuExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysBuService {
    @Resource
    private SysBuMapper sysBuMapper;

    public SysBu findById(Integer id) {
        return sysBuMapper.selectByPrimaryKey(id);
    }

    public List<SysBu> queryByDeptId(Integer deptId) {
        SysBuExample example = new SysBuExample();
        example.or().andDeptIdEqualTo(deptId);
        return sysBuMapper.selectByExample(example);
    }

    public void add(SysBu sysBu) {
        sysBuMapper.insert(sysBu);
    }

    public void update(SysBu sysBu) {
        sysBuMapper.updateByPrimaryKeySelective(sysBu);
    }

    public List<SysBu> querySelective(String name, Integer userId, Integer deptId, Integer page, Integer size, String sort, String order) {
        SysBuExample example = new SysBuExample();
        SysBuExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(deptId)){
            criteria.andDeptIdEqualTo(deptId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return sysBuMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer userId, Integer deptId, Integer page, Integer size, String sort, String order) {
        SysBuExample example = new SysBuExample();
        SysBuExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(deptId)){
            criteria.andDeptIdEqualTo(deptId);
        }

        return (int) sysBuMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        sysBuMapper.deleteByPrimaryKey(id);
    }

}
