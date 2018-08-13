package org.linlinjava.litemall.db.service;

import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.dao.HbPhotoHisMapper;
import org.linlinjava.litemall.db.domain.HbPhotoHis;
import org.linlinjava.litemall.db.domain.HbPhotoHisExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName HbPhotoHisService
 * Description TODO
 * Author leiqiang
 * Date 2018/8/13 10:04
 * Version 1.0
 **/
@Service
public class HbPhotoHisService {
    @Resource
    private HbPhotoHisMapper hbPhotoHisMapper;

    public String load(Integer userId,Integer orderId){
        String img_url="";
        HbPhotoHisExample example=new HbPhotoHisExample();
        HbPhotoHisExample.Criteria criteria=example.createCriteria();
        if(userId!=null)
            criteria.andUserIdEqualTo(userId);
        if(orderId!=null)
            criteria.andOrderIdEqualTo(orderId);
        HbPhotoHis his=hbPhotoHisMapper.selectOneByExample(example);
        if(his!=null&&StringUtils.isNotEmpty(his.getImgUrl()))
            img_url=his.getImgUrl();
        return img_url;
    }

    public void create(HbPhotoHis his){
        hbPhotoHisMapper.insertSelective(his);
    }
}
