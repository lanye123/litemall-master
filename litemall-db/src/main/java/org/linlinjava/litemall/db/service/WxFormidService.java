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

    public List<WxFormid> queryByStatus(String openid) {
        return wxFormidMapper.selectByStatus(openid);
    }


    public int count() {
        WxFormidExample example = new WxFormidExample();
        example.or().andStatusEqualTo(0);

        return (int)wxFormidMapper.countByExample(example);
    }

    /*public void update(Integer id) {
        WxFormid formid=new WxFormid();
        formid.setStatus(1);
        formid.setId(id);
        wxFormidMapper.updateByPrimaryKey(formid);
    }*/

    public void update(WxFormid formid) {
        wxFormidMapper.updateByPrimaryKey(formid);
    }
}
