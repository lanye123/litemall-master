package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.WxGzhUserMapper;
import org.linlinjava.litemall.db.domain.WxGzhUser;
import org.linlinjava.litemall.db.domain.WxGzhUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName WxGzhUserService
 * Description TODO 微信公众号用户信息列表
 * Author leiqiang
 * Date 2018/8/2 10:23
 * Version 1.0
 **/
@Service
public class WxGzhUserService {
    @Resource
    private WxGzhUserMapper wxGzhUserMapper;

    //获取用户列表最后一个openid,id倒序排列取第一个
    public List<WxGzhUser> querySelective(String openid, String nickname, Integer page, Integer size, String sort, String order) {
        WxGzhUserExample example = new WxGzhUserExample();
        WxGzhUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(nickname)){
            criteria.andNicknameLike("%" + nickname + "%");
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenidEqualTo(openid);
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return wxGzhUserMapper.selectByExample(example);
    }

    public int countSeletive(String openid, String nickname) {
        WxGzhUserExample example = new WxGzhUserExample();
        WxGzhUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(nickname)){
            criteria.andNicknameLike("%" + nickname + "%");
        }
        if(!StringUtils.isEmpty(openid)){
            criteria.andOpenidEqualTo(openid);
        }

        return (int) wxGzhUserMapper.countByExample(example);
    }

    public void add(WxGzhUser user) {
        wxGzhUserMapper.insertSelective(user);
    }

    public void update(WxGzhUser user) {
        wxGzhUserMapper.updateByPrimaryKeySelective(user);
    }

    public WxGzhUser findById(Integer id) {
        if(id==null){
            return null;
        }
        return wxGzhUserMapper.selectByPrimaryKey(id);
    }

    //获取已关注用户列表
}
