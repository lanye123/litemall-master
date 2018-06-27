package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SysDeptMapper;
import org.linlinjava.litemall.db.domain.SysDept;
import org.linlinjava.litemall.db.domain.SysDeptExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    public SysDept findById(Integer id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    public void add(SysDept sysDept) {
        sysDeptMapper.insert(sysDept);
    }

    public void update(SysDept sysDept) {
        sysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    public List<SysDept> querySelective(String name, Integer userId,Integer page, Integer size, String sort, String order) {
        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return sysDeptMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer userId,Integer page, Integer size, String sort, String order) {
        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }

        return (int) sysDeptMapper.countByExample(example);
    }

    public List<SysDept> queryAll() {
        SysDeptExample example=new SysDeptExample();
        return sysDeptMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        sysDeptMapper.deleteByPrimaryKey(id);
    }

}
