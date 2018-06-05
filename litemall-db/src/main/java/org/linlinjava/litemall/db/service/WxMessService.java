package org.linlinjava.litemall.db.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.dao.WxMessMapper;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.domain.WxMess;
import org.linlinjava.litemall.db.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.logging.Logger;

@Service
public class WxMessService {
    private final Log logger = LogFactory.getLog(WxMessService.class);
    @Resource
    private WxMessMapper wxMessMapper;
    @Resource
    private LitemallUserService litemallUserService;
    @Resource
    private WxConfigService wxConfigService;

    @Value("${mubaninfo.url}")
    private String muban_url;
    //图文发布审核通过提醒
    public JSONObject articleCheck(String url,String keyword1_str,String keyword2_str,String keyword3_str,String formId,Integer user_id){
        LitemallUser user=litemallUserService.findById(user_id);
        JSONObject result=null;
        if(user!=null&&!StringUtils.isEmpty(user.getWeixinOpenid())){
            WxConfig config= wxConfigService.getToken();
            String request_url=muban_url.replace("ACCESS_TOKEN",config.getAccessToken());
            JSONObject data=new JSONObject();
            JSONObject object=new JSONObject();
            JSONObject object2=new JSONObject();
            object.put("touser",user.getWeixinOpenid());
            object.put("template_id","rppEJdn0BtBTl2G0SAuISnh05CYfLFiAs4Yg1EpQZkE");
            if(!StringUtils.isEmpty(url))
            object.put("page",url);
            if(!StringUtils.isEmpty(formId))
            object.put("form_id",formId);
            JSONObject keyword1 = new JSONObject();
                keyword1.put("value",keyword1_str);
                data.put("keyword1",keyword1);
            JSONObject keyword2 = new JSONObject();
                keyword2.put("value",keyword2_str);
                data.put("keyword2",keyword2);
            JSONObject keyword3 = new JSONObject();
                keyword3.put("value",keyword3_str);
                data.put("keyword3",keyword3);
            object.put("data",data);
            result = HttpClientUtil.doPost(request_url, object);
            logger.info(result);
            if(Integer.parseInt(result.getString("data.errcode"))==0){
                WxMess m = new WxMess();
                m.setUserId(user_id);
                m.setReceptOpenId(user.getWeixinOpenid());
                m.setContent(keyword1_str+","+keyword2_str+","+keyword3_str);
                wxMessMapper.insertSelective(m);
            }
        }
        return result;
    }

    //图文发布审核不通过提醒
    public void articleCheckFail(String url,String keyword1,String keyword2,String keyword3,Integer user_id){

    }
}
