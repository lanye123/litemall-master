package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ZkTikuMapper;
import org.linlinjava.litemall.db.domain.ZkTiku;
import org.linlinjava.litemall.db.domain.ZkTikuExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZkTikuService {
    @Resource
    private ZkTikuMapper zkTikuMapper;

    public ZkTiku findById(Integer id) {
        return zkTikuMapper.selectByPrimaryKey(id);
    }

    public List<ZkTiku> queryByZyId(String zyId,Integer userId) {
        ZkTikuExample example = new ZkTikuExample();
        if(!StringUtils.isEmpty(userId)){
            example.createCriteria().andUserIdEqualTo(userId);
        }
        if(StringUtils.isEmpty(zyId)){
            return zkTikuMapper.selectByExample2(example);
        }
        String[] ids = zyId.split(",");
        List<Integer> integerList = new ArrayList<>();
        for(String id:ids){
            integerList.add(Integer.valueOf(id));
        }
        if(integerList.size()>0){
            example.createCriteria().andZyIdIn(integerList);
        }
        return zkTikuMapper.selectByExample2(example);
    }

    public void add(ZkTiku zkTiku) {
        zkTikuMapper.insert(zkTiku);
    }

    public void update(ZkTiku zkTiku) {
        zkTikuMapper.updateByPrimaryKeySelective(zkTiku);
    }

    public List<ZkTiku> querySelective(String title, String subTitle,Integer zyId ,Integer page, Integer size, String sort, String order) {
        ZkTikuExample example = new ZkTikuExample();
        ZkTikuExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subTitle)){
            criteria.andSubTitleLike("%" + subTitle + "%");
        }
        if(!StringUtils.isEmpty(zyId)){
            criteria.andZyIdEqualTo(zyId);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return zkTikuMapper.selectByExample(example);
    }

    public int countSeletive(String title, String subTitle,Integer zyId ,Integer page, Integer size, String sort, String order) {
        ZkTikuExample example = new ZkTikuExample();
        ZkTikuExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if(!StringUtils.isEmpty(subTitle)){
            criteria.andSubTitleLike("%" + subTitle + "%");
        }
        if(!StringUtils.isEmpty(zyId)){
            criteria.andZyIdEqualTo(zyId);
        }

        return (int) zkTikuMapper.countByExample(example);
    }

    public List<ZkTiku> queryAll() {
        ZkTikuExample example=new ZkTikuExample();
        return zkTikuMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        if(!StringUtils.isEmpty(id)){
            return;
        }
        zkTikuMapper.deleteByPrimaryKey(id);
    }
}
