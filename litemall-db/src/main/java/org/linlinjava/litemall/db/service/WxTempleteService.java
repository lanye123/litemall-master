package org.linlinjava.litemall.db.service;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.linlinjava.litemall.db.dao.WxTempleteMapper;
import org.linlinjava.litemall.db.domain.WxTemplete;
import org.linlinjava.litemall.db.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;
import javax.annotation.Resource;
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
        String request_url=templeteurl.replace("ACCESS_TOKEN",accessToken);
        JSONObject result=HttpClientUtil.doGet(request_url);
        List<WxTemplete> template_list=result.getJSONArray("template_list");
        for(WxTemplete temp:template_list){
            wxTempleteMapper.insertSelective(temp);
        }
    }
    //发送模板消息列表
    public JSONObject sendMess(String accessToken,JSONObject data) {
        String request_url=sendtemleteurl.replace("ACCESS_TOKEN",accessToken);
        return HttpClientUtil.doPost(request_url,data);
    }
}
