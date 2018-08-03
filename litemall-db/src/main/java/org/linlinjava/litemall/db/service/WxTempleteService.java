package org.linlinjava.litemall.db.service;

import com.sun.scenario.effect.impl.prism.PrImage;
import net.sf.json.JSONArray;
import org.linlinjava.litemall.db.dao.WxTempleteMapper;
import org.linlinjava.litemall.db.domain.WxTemplete;
import org.linlinjava.litemall.db.domain.WxTempleteExample;
import org.linlinjava.litemall.db.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName WxTempleteService
 * Description TODO 从微信服务器获取模板消息列表存储到本地
 * Author leiqiang
 * Date 2018/8/2 11:29
 * Version 1.0
 **/
@Service
public class WxTempleteService {
    @Resource
    private WxTempleteMapper wxTempleteMapper;
    @Value("${templete.url}")
    private String templeteurl;
    @Value("${sendtemplete.url}")
    private String sendtemleteurl;

    //获取模板消息列表
    public void getTempleteList(String accessToken) {
        //插入前先清空模板表
        wxTempleteMapper.deleteAll();
        String request_url=templeteurl.replace("ACCESS_TOKEN",accessToken);
        JSONObject result=HttpClientUtil.doGet(request_url);
        JSONArray json=result.getJSONArray("template_list");
        if(json.size()>0) {
            for (int i = 0; i < json.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject job = json.getJSONObject(i);
                WxTemplete templete = new WxTemplete();
                templete.setTemplateId(job.getString("template_id"));
                templete.setContent(job.getString("content"));
                templete.setDeputyIndustry(job.getString("deputy_industry"));
                templete.setTitle(job.getString("title"));
                templete.setPrimaryIndustry("primary_industry");
                templete.setExample(job.getString("example"));
                wxTempleteMapper.insertSelective(templete);
            }
        }
    }

    //发送模板消息列表
    public JSONObject sendMess(String accessToken,JSONObject data) {
        String request_url=sendtemleteurl.replace("ACCESS_TOKEN",accessToken);
        return HttpClientUtil.doPost(request_url,data);
    }

    public List<com.alibaba.fastjson.JSONObject> queryList() {
        return wxTempleteMapper.queryList();
    }
}
