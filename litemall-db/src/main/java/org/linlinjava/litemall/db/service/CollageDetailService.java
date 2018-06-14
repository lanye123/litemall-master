package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CollageDetailMapper;
import org.linlinjava.litemall.db.domain.CollageDetail;
import org.linlinjava.litemall.db.domain.CollageDetailExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollageDetailService {
    @Resource
    private CollageDetailMapper collageDetailMapper;

    public void add(CollageDetail collageDetail){
        collageDetailMapper.insertSelective(collageDetail);
    }

    public void update(CollageDetail collageDetail){
        CollageDetailExample example=new   CollageDetailExample();
        example.or().andIdEqualTo(collageDetail.getId());
        collageDetailMapper.updateByExample(collageDetail,example);
    }
    //根据团长订单id查询拼团成员信息
    public List<CollageDetail> queryById(Integer pid){
        CollageDetailExample example=new CollageDetailExample();
        example.or().andPidEqualTo(pid);
      return collageDetailMapper.selectByExample(example);
    }
}
