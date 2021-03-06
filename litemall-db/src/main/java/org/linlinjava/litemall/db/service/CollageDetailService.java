package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.CollageDetailMapper;
import org.linlinjava.litemall.db.domain.CollageDetail;
import org.linlinjava.litemall.db.domain.CollageDetailExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
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

    public List<CollageDetail> queryBySelective(Integer userId, Integer orderId, Integer goodsId, Integer status, Date startDate, Date endDate, Integer page, Integer limit, String sort, String order) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderId))
            criteria.andOrderIdEqualTo(orderId);
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(status))
            criteria.andStatusEqualTo(status);
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        if(!StringUtils.isEmpty(startDate))
            criteria.andCreateDateGreaterThanOrEqualTo(startDate);
        if(!StringUtils.isEmpty(endDate))
            criteria.andCreateDateLessThanOrEqualTo(endDate);
        if(page!=null && limit!=null){
            PageHelper.startPage(page,limit);
        }
        return collageDetailMapper.selectByExample(example);
    }

    public List<CollageDetail> queryBySelective3(Integer userId, String orderSn, Integer goodsId, Integer status, Date startDate, Date endDate,Integer type,String nickname,Integer type2, Integer page, Integer limit, String sort, String order) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andCUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderSn))
            criteria.andOOrderSnLike("%"+orderSn+"%");
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(status))
            criteria.andCStatusEqualTo(status);
        if(!StringUtils.isEmpty(type))
            criteria.andTypeEqualTo(type);
        if(!StringUtils.isEmpty(nickname))
            criteria.andNickNameLike("%"+nickname+"%");
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        if(!StringUtils.isEmpty(startDate))
            criteria.andCCreateDateGreaterThanOrEqualTo(startDate);
        if(!StringUtils.isEmpty(endDate))
            criteria.andCCreateDateLessThanOrEqualTo(endDate);
        if(!StringUtils.isEmpty(type2)){
            if(type2==0){
                criteria.andAccountIsNotNull();
            }else if(type2 == 1){
                criteria.andAccountIsNull();
            }
        }

        if(page!=null && limit!=null){
            PageHelper.startPage(page,limit);
        }
        return collageDetailMapper.selectByExample2(example);
    }

    public List<CollageDetail>  queryBySelective2(Integer pid,Integer userId,Integer goodsId,Integer status, Integer page, Integer limit, String sort, String order) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(pid!=null)
            criteria.andPidEqualTo(pid);
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(status))
            criteria.andStatusEqualTo(status);
        if(!StringUtils.isEmpty(order))
            criteria.example().setOrderByClause(order);
        if(page!=null && limit!=null){
            PageHelper.startPage(page,limit);
        }
        return collageDetailMapper.selectByExample(example);
    }

    public int count(Integer userId,Integer orderId,Integer goodsId,Integer status,Date startDate, Date endDate) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderId))
            criteria.andOrderIdEqualTo(orderId);
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(status))
            criteria.andStatusEqualTo(status);
        if(!StringUtils.isEmpty(startDate))
            criteria.andCreateDateGreaterThanOrEqualTo(startDate);
        if(!StringUtils.isEmpty(endDate))
            criteria.andCreateDateLessThanOrEqualTo(endDate);
        return (int)collageDetailMapper.countByExample(example);
    }

    public int count2(Integer userId,String orderSn,Integer goodsId,Integer status,Date startDate, Date endDate,Integer type,String nickname,Integer type2) {
        CollageDetailExample example=new CollageDetailExample();
        CollageDetailExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(userId))
            criteria.andCUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderSn))
            criteria.andOOrderSnLike("%"+orderSn+"%");
        if(!StringUtils.isEmpty(goodsId))
            criteria.andGoodsIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(status))
            criteria.andCStatusEqualTo(status);
        if(!StringUtils.isEmpty(type))
            criteria.andTypeEqualTo(type);
        if(!StringUtils.isEmpty(nickname))
            criteria.andNickNameLike("%"+nickname+"%");
        if(!StringUtils.isEmpty(startDate))
            criteria.andCCreateDateGreaterThanOrEqualTo(startDate);
        if(!StringUtils.isEmpty(endDate))
            criteria.andCCreateDateLessThanOrEqualTo(endDate);
        if(!StringUtils.isEmpty(type2)){
            if(type2 == 1){
                criteria.andAccountIsNull();
            }else if(type2 == 0){
                criteria.andAccountIsNotNull();
            }
        }
        return (int)collageDetailMapper.countByExample2(example);
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
