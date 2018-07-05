package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ZkZhuanyeMapper;
import org.linlinjava.litemall.db.domain.ZkZhuanye;
import org.linlinjava.litemall.db.domain.ZkZhuanyeExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZkZhuanyeService {
    @Resource
    private ZkZhuanyeMapper zkZhuanyeMapper;

    public ZkZhuanye findById(Integer id) {
        return zkZhuanyeMapper.selectByPrimaryKey(id);
    }

    public void add(ZkZhuanye zkZhuanye) {
        zkZhuanyeMapper.insert(zkZhuanye);
    }

    public void update(ZkZhuanye zkZhuanye) {
        zkZhuanyeMapper.updateByPrimaryKeySelective(zkZhuanye);
    }

    public List<ZkZhuanye> querySelective(String name, Integer page, Integer size, String sort, String order) {
        ZkZhuanyeExample example = new ZkZhuanyeExample();
        ZkZhuanyeExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo("%" + name + "%");
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return zkZhuanyeMapper.selectByExample(example);
    }

    public int countSeletive(String name,Integer page, Integer size, String sort, String order) {
        ZkZhuanyeExample example = new ZkZhuanyeExample();
        ZkZhuanyeExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo("%" + name + "%");
        }

        return (int) zkZhuanyeMapper.countByExample(example);
    }

    public List<ZkZhuanye> queryAll() {
        ZkZhuanyeExample example=new ZkZhuanyeExample();
        return zkZhuanyeMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        zkZhuanyeMapper.deleteByPrimaryKey(id);
    }
}
