package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CollageDetailMapper;
import org.linlinjava.litemall.db.domain.CollageDetail;
import org.linlinjava.litemall.db.domain.CollageDetailExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        example.setOrderByClause("create_date");
      return collageDetailMapper.selectByExample(example);
    }

    public List<CollageDetail>  queryBySelective(Integer orderId,Integer userId,Integer goodsId, Integer page, Integer limit, String sort, String order) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderId))
            criteria.andOrderIdEqualTo(orderId);
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        if(page!=null && limit!=null){
            PageHelper.startPage(page,limit);
        }
        return collageDetailMapper.selectByExample(example);
    }

    public int count(Integer orderId,Integer userId,Integer goodsId) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderId))
            criteria.andOrderIdEqualTo(orderId);
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        return (int)collageDetailMapper.countByExample(example);
    }

    public CollageDetail queryByPid(Integer orderId, Integer userId) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderId))
            criteria.andPidEqualTo(orderId);
        //criteria.andFlagEqualTo(1);
        return collageDetailMapper.selectOneByExample(example);
    }

    public Integer countByPid(Integer pid) {
        return collageDetailMapper.countByPid(pid);
    }

    public List<CollageDetail> queryByPids(Integer pid) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(pid!=null)
            criteria.andPidEqualTo(pid);
        criteria.andStatusEqualTo(0);//拼团成功待开奖记录
        return collageDetailMapper.selectByExample(example);
    }

    public CollageDetail findByOrderId(Integer orderId,Integer userId) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(orderId!=null)
            criteria.andOrderIdEqualTo(orderId);
        if(userId!=null)
            criteria.andUserIdEqualTo(userId);
        return collageDetailMapper.selectOneByExample(example);
    }

    public CollageDetail selectOneByExample(Integer userId, Integer pid) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(pid!=null)
            criteria.andPidEqualTo(pid);
        if(userId!=null)
            criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo(0);//参团中
        return collageDetailMapper.selectOneByExample(example);

    }


    public CollageDetail findById(Integer id) {
        return collageDetailMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        collageDetailMapper.deleteByPrimaryKey(id);
    }
}
