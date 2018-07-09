package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ZkTikuDetailMapper;
import org.linlinjava.litemall.db.domain.ZkTikuDetail;
import org.linlinjava.litemall.db.domain.ZkTikuDetailExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZkTikuDetailService {
    @Resource
    private ZkTikuDetailMapper zkTikuDetailMapper;

    public ZkTikuDetail findById(Integer id) {
        return zkTikuDetailMapper.selectByPrimaryKey(id);
    }

    public List<ZkTikuDetail> queryByPid(Integer pid,Integer tkId) {
        ZkTikuDetailExample example = new ZkTikuDetailExample();
        ZkTikuDetailExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(pid)){
            criteria.andPidEqualTo(pid);
        }else{
            return new ArrayList<>();
        }
        if(!StringUtils.isEmpty(tkId)){
            criteria.andTkIdEqualTo(tkId);
        }

        return zkTikuDetailMapper.selectByExample(example);
    }

    public void add(ZkTikuDetail zkTiku) {
        zkTikuDetailMapper.insert(zkTiku);
    }

    public void update(ZkTikuDetail zkTiku) {
        zkTikuDetailMapper.updateByPrimaryKeySelective(zkTiku);
    }

    public List<ZkTikuDetail> querySelective(Integer tkId, Integer userId,Integer pid ,Integer flag ,Integer status,Integer page, Integer size, String sort, String order) {
        ZkTikuDetailExample example = new ZkTikuDetailExample();
        ZkTikuDetailExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(tkId)){
            criteria.andTkIdEqualTo(tkId);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(pid)){
            criteria.andPidEqualTo(pid);
        }
        if(!StringUtils.isEmpty(flag)){
            criteria.andFlagEqualTo(flag);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return zkTikuDetailMapper.selectByExample(example);
    }

    public int countSeletive(Integer tkId, Integer userId,Integer pid ,Integer flag ,Integer status,Integer page, Integer size, String sort, String order) {
        ZkTikuDetailExample example = new ZkTikuDetailExample();
        ZkTikuDetailExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(tkId)){
            criteria.andTkIdEqualTo(tkId);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(pid)){
            criteria.andPidEqualTo(pid);
        }
        if(!StringUtils.isEmpty(flag)){
            criteria.andFlagEqualTo(flag);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        return (int) zkTikuDetailMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        zkTikuDetailMapper.deleteByPrimaryKey(id);
    }
}
