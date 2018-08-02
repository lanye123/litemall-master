package org.linlinjava.litemall.db.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
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

    public void createBatch(JSONObject data,String radomid) {
        WxTempleteSend wxTempleteSend=new WxTempleteSend();
        wxTempleteSend.setContent(JSONObject.toJSONString(data));
        wxTempleteSend.setFlag(radomid);
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
}
