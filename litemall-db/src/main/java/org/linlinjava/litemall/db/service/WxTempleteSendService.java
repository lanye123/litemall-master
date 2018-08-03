package org.linlinjava.litemall.db.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.dao.WxTempleteSendMapper;
import org.linlinjava.litemall.db.domain.WxTempleteSend;
import org.linlinjava.litemall.db.domain.WxTempleteSendExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName WxTempleteSendService
 * Description TODO 模板消息待发送消息列表
 * Author leiqiang
 * Date 2018/8/2 11:31
 * Version 1.0
 **/
@Service
public class WxTempleteSendService {
    @Resource
    private WxTempleteSendMapper wxTempleteSendMapper;

    public void createBatch(JSONObject data,Integer radomid) {
        WxTempleteSend wxTempleteSend=new WxTempleteSend();
        wxTempleteSend.setContent(JSONObject.toJSONString(data));
        wxTempleteSend.setFlag(String.valueOf(radomid));
        wxTempleteSend.setStatus(0);
        wxTempleteSendMapper.insertSelective(wxTempleteSend);
    }

    public List<WxTempleteSend> waitSend(){
        WxTempleteSendExample example=new WxTempleteSendExample();
        WxTempleteSendExample.Criteria criteria=example.createCriteria();
        criteria.andStatusEqualTo(0);
        criteria.example().setOrderByClause("flag,id asc");
        PageHelper.startPage(1,10);
       return wxTempleteSendMapper.selectByExample(example);
    }

    public void update(WxTempleteSend temp) {
        wxTempleteSendMapper.updateByPrimaryKey(temp);
    }

    public List<WxTempleteSend> queryByFlag(String s) {
        WxTempleteSendExample example=new WxTempleteSendExample();
        WxTempleteSendExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(s))
        criteria.andFlagEqualTo(s);
        return wxTempleteSendMapper.selectByExample(example);
    }

    public int countByFlag(String s,Integer status) {
        WxTempleteSendExample example=new WxTempleteSendExample();
        WxTempleteSendExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(s))
            criteria.andFlagEqualTo(s);
        if(status!=null)
            criteria.andStatusEqualTo(status);
        return (int) wxTempleteSendMapper.countByExample(example);
    }
}
