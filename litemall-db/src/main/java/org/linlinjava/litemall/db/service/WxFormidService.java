package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxFormidMapper;
import org.linlinjava.litemall.db.domain.WxFormid;
import org.linlinjava.litemall.db.domain.WxFormidExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WxFormidService {
    @Resource
    private WxFormidMapper wxFormidMapper;

    public void add(WxFormid formid){
        wxFormidMapper.insertSelective(formid);
    }

    public void delete(Integer id){
        wxFormidMapper.deleteByPrimaryKey(id);
    }

    public List<WxFormid> queryByStatus(int i) {
        WxFormidExample example=new WxFormidExample();
        example.or().andStatusEqualTo(i);
        return wxFormidMapper.selectByExampleSelective(example);
    }


    public int count() {
        WxFormidExample example = new WxFormidExample();
        example.or().andStatusEqualTo(0);

        return (int)wxFormidMapper.countByExample(example);
    }
}
