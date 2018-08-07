package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallUserService {
    @Resource
    private LitemallUserMapper userMapper;
    @Resource
    private LitemallUserService litemallUserService;
    @Resource
    private IntegretionDetailService integretionDetailService;

    public LitemallUser findById(Integer userId) {
        if(userId==null){
            return null;
        }
        return userMapper.selectByPrimaryKey(userId);
    }

    public LitemallUser queryByOid(String openId) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(LitemallUser user) {
        if(litemallUserService.countSeletive("","",user.getWeixinOpenid(),"",null,null,null,"","")>0){
            return;
        }
        userMapper.insertSelective(user);
        //新用户赠送5积分
        IntegretionDetail integretionDetail = new IntegretionDetail();
        integretionDetail.setAmount(5);
        integretionDetail.setUserId(user.getId()+"");
        integretionDetail.setType((byte)20);
        integretionDetailService.add(integretionDetail);
    }

    public void update(LitemallUser user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<LitemallUser> querySelective(String username, String mobile, String weixinOpenid, String registerIp,Integer type, Integer page, Integer size, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        if(!StringUtils.isEmpty(weixinOpenid)){
            criteria.andWeixinOpenidEqualTo(weixinOpenid);
        }
        if(!StringUtils.isEmpty(registerIp)){
            criteria.andRegisterIpEqualTo(registerIp);
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }
        if(!StringUtils.isEmpty(type)){
            if(type==0){
                criteria.andAccountIsNotNull();
            }else if(type == 1){
                criteria.andAccountIsNull();
            }
        }

        criteria.andDeletedEqualTo(false);

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return userMapper.selectByExample(example);
    }

    public int countSeletive(String username, String mobile, String weixinOpenid, String registerIp, Integer type ,Integer page, Integer size, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        if(!StringUtils.isEmpty(weixinOpenid)){
            criteria.andWeixinOpenidEqualTo(weixinOpenid);
        }
        if(!StringUtils.isEmpty(registerIp)){
            criteria.andRegisterIpEqualTo(registerIp);
        }
        if(!StringUtils.isEmpty(type)){
            if(type==0){
                criteria.andAccountIsNotNull();
            }else if(type == 1){
                criteria.andAccountIsNull();
            }
        }
        criteria.andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public int count() {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andDeletedEqualTo(false);

        return (int)userMapper.countByExample(example);
    }

    public List<LitemallUser> queryByUsername(String username) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<LitemallUser> queryByMobile(String mobile) {
        LitemallUserExample example = new LitemallUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        LitemallUser user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return;
        }
        user.setDeleted(true);
        userMapper.updateByPrimaryKey(user);
    }

    public LitemallUser queryById(Integer fromUserid) {
        return userMapper.selectByPrimaryKey(fromUserid);
    }

    public List<LitemallUser> queryAll() {
        LitemallUserExample example=new LitemallUserExample();
        return userMapper.selectByExample(example);
    }

    public List<LitemallUser> queryPlannerSelective(String pid, Integer dept_id, Integer bu_id, Integer corps_id, Integer transition_id, String start_date, String end_date, String mobile, Integer page, Integer limit, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(pid)){
            criteria.andPidEqualTo(pid);
        }
        if(dept_id!=null){
            criteria.andDeptIdEqualTo(dept_id);
        }
        if(bu_id!=null){
            criteria.andBuIdEqualTo(bu_id);
        }
        if(corps_id!=null){
            criteria.andCorpsIdEqualTo(corps_id);
        }
        if(transition_id!=null){
            criteria.andTransitionIdEqualTo(transition_id);
        }
        if(!StringUtils.isEmpty(start_date)){
            criteria.andStartDateEqualTo(start_date);
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileLike("%" + mobile + "%");
        }
        if(!StringUtils.isEmpty(end_date)){
            criteria.andEndDateEqualTo(end_date);
        }
        if(page!=null && limit!=null){
            PageHelper.startPage(page, limit);
        }
        return userMapper.listByPlanner(example);
    }

    public int countPlannerSeletive(String pid, Integer dept_id, Integer bu_id, Integer corps_id, Integer transition_id, String start_date, String end_date, String mobile, Integer page, Integer limit, String sort, String order) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(pid)){
            criteria.andPidEqualTo(pid);
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileLike("%" + mobile + "%");
        }
        if(dept_id!=null){
            criteria.andDeptIdEqualTo(dept_id);
        }
        if(bu_id!=null){
            criteria.andBuIdEqualTo(bu_id);
        }
        if(corps_id!=null){
            criteria.andCorpsIdEqualTo(corps_id);
        }
        if(transition_id!=null){
            criteria.andTransitionIdEqualTo(transition_id);
        }
        if(!StringUtils.isEmpty(start_date)){
            criteria.andStartDateEqualTo(start_date);
        }
        if(!StringUtils.isEmpty(end_date)){
            criteria.andEndDateEqualTo(end_date);
        }
        return userMapper.countByPlanner(example);
    }

    public List<LitemallUser> tjCorpsPie(){
        return userMapper.tjByCorps();
    }

    public List<LitemallUser> queryByAccount(String account) {
        LitemallUserExample example = new LitemallUserExample();
        LitemallUserExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(account)){
            criteria.andAccountEqualTo(account);
        }
       return userMapper.selectByExample(example);
    }
}
