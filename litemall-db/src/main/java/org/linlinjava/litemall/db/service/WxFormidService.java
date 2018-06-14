package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxFormidMapper;
import org.linlinjava.litemall.db.domain.WxFormid;
import org.linlinjava.litemall.db.domain.WxFormidExample;
import org.mybatis.generator.codegen.ibatis2.sqlmap.elements.InsertElementGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WxFormidService {
    @Resource
    private WxFormidMapper wxFormidMapper;

    public void add(String form_id){
        WxFormid formid=new WxFormid();
        formid.setFormId(form_id);
        formid.setStatus(0);
        wxFormidMapper.insertSelective(formid);
    }

    public void delete(Integer id){
        wxFormidMapper.deleteByPrimaryKey(id);
    }

    public List<WxFormid> queryByStatus(int i) {
        WxFormidExample example=new WxFormidExample();
        example.or().andStatusEqualTo(1);
        return wxFormidMapper.selectByExampleSelective(example);
    }
}
