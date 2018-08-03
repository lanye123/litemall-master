package org.linlinjava.litemall.admin.timingTask;

import com.alibaba.druid.util.StringUtils;
import net.sf.json.JSONObject;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.domain.WxTempleteSend;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.service.WxTempleteSendService;
import org.linlinjava.litemall.db.service.WxTempleteService;
import org.linlinjava.litemall.db.util.HttpClientUtil;
import org.linlinjava.litemall.db.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName templeteSendTiming
 * Description TODO 公众号模板消息群发定时推送任务
 * Author leiqiang
 * Date 2018/8/2 15:45
 * Version 1.0
 **/
@Configuration
@EnableScheduling
public class templeteSendTiming {
    @Resource
    private WxTempleteSendService wxTempleteSendService;
    @Resource
    private WxTempleteService wxTempleteService;
    @Resource
    private WxConfigService wxConfigService;
    @Value("${miniprogram.appid}")
    private String miniprogram_appid;//小程序appid作跳转用
    @Value("${task.switch}")
    private String taskSwitch;
    /**
     * @Author leiqiang
     * @Description //TODO 定时器，每隔固定时间去数据库中查询待发送的模板消息数据并发送
     * @Date   2018/8/2 15:49
     * @Param  []
     * @return void
     **/
    @Scheduled(fixedRate = 20000)//通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间20秒执行一次
    public void send(){
        if(taskSwitch.equals("close")){
            return;
        }else{
            WxConfig config=wxConfigService.getGzhToken();
            List<WxTempleteSend> list=wxTempleteSendService.waitSend();
            for (WxTempleteSend temp:list){
                String body=temp.getContent();
                String templete_id = JacksonUtil.parseString(body, "templete_id");
                String touser=JacksonUtil.parseString(body, "touser");
                String first_str = JacksonUtil.parseString(body, "first");
                String keyword1_str = JacksonUtil.parseString(body, "keyword1");
                String keyword2_str = JacksonUtil.parseString(body, "keyword2");
                String keyword3_str = JacksonUtil.parseString(body, "keyword3");
                String keyword4_str = JacksonUtil.parseString(body, "keyword4");
                String keyword5_str = JacksonUtil.parseString(body, "keyword5");
                String remark_str = JacksonUtil.parseString(body, "remark");

                String url=JacksonUtil.parseString(body, "url");//跳转链接
                String pagepath=JacksonUtil.parseString(body, "pagepath");//小程序链接
                net.sf.json.JSONObject data=new net.sf.json.JSONObject();
                net.sf.json.JSONObject object=new net.sf.json.JSONObject();
                object.put("touser",touser);
                object.put("template_id",templete_id);
                if(!StringUtils.isEmpty(url))
                    object.put("url",url);
                if(!StringUtils.isEmpty(pagepath)){
                    //小程序跳转
                    net.sf.json.JSONObject miniprogram=new net.sf.json.JSONObject();
                    miniprogram.put("appid",miniprogram_appid);
                    miniprogram.put("pagepath",pagepath);
                    object.put("miniprogram",miniprogram);
                }
                net.sf.json.JSONObject first=new net.sf.json.JSONObject();
                first.put("value",first_str);
                object.put("first",first);
                net.sf.json.JSONObject keyword1=new net.sf.json.JSONObject();
                keyword1.put("value",keyword1_str);
                data.put("keyword1",keyword1);
                net.sf.json.JSONObject keyword2=new net.sf.json.JSONObject();
                keyword2.put("value",keyword2_str);
                data.put("keyword2",keyword2);
                net.sf.json.JSONObject keyword3=new net.sf.json.JSONObject();
                keyword3.put("value",keyword3_str);
                data.put("keyword3",keyword3);
                net.sf.json.JSONObject keyword4=new net.sf.json.JSONObject();
                keyword4.put("value",keyword4_str);
                data.put("keyword4",keyword4);
                net.sf.json.JSONObject keyword5=new net.sf.json.JSONObject();
                keyword5.put("value",keyword5_str);
                data.put("keyword5",keyword5);
                net.sf.json.JSONObject remark=new net.sf.json.JSONObject();
                keyword5.put("value",remark_str);
                data.put("remark",remark);
                object.put("data",data);
                net.sf.json.JSONObject result=wxTempleteService.sendMess(config.getAccessToken(),object);
                System.out.println("###########模板消息发送状态:"+result.get("errcode")+","+result.get("errmsg"));
                //发送完成后更新status为1
                if(Integer.valueOf(result.getString("errcode"))==0){
                    temp.setStatus(1);
                    wxTempleteSendService.update(temp);
                }
            }
        }

    }
}
