package org.linlinjava.litemall.admin.web;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.domain.WxGzhUser;
import org.linlinjava.litemall.db.domain.WxTempleteSend;
import org.linlinjava.litemall.db.domain.WxTempleteSendStatus;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.JacksonUtil;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName WxGzhController
 * Description TODO 公众号管理模块功能开发（模板列表管理、粉丝管理、模板消息管理）
 * Author leiqiang
 * Date 2018/8/2 11:33
 * Version 1.0
 **/
@RestController
@RequestMapping("/admin/gzh")
public class WxGzhController {
    @Autowired
    private WxTempleteService wxTempleteService;
    @Autowired
    private WxTempleteSendService wxTempleteSendService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private WxGzhUserService wxGzhUserService;
    @Autowired
    private WxTempleteSendStatusService wxTempleteSendStatusService;
    @Value("${templete.url}")
    private String templeteurl;
    @Value("${miniprogram.appid}")
    private String miniprogram_appid;//小程序appid作跳转用

    @ModelAttribute
    public WxConfig getConfig(){
        WxConfig config=wxConfigService.getGzhToken();
        return config;
    }
    /**
     * @Author leiqiang
     * @Description //TODO 获取微信公众号模板消息列表保存到本地wx_templete库中
     * @Date   2018/8/2 14:11
     * @Param  []
     * @return java.lang.Object
     **/
    @GetMapping("getTempleteList")
    public Object getTempleteList(){
        wxTempleteService.getTempleteList(getConfig().getAccessToken());
        return ResponseUtil.ok();
    }

    @GetMapping("templeteList")
    public Object templeteList(){
        List<JSONObject> list=wxTempleteService.queryList();
        return ResponseUtil.ok(list);

    }
    /**
     * @Author leiqiang
     * @Description //TODO 选择模板填写模板内容后提交保存至后台
     * @Date   2018/8/2 15:38
     * @Param  [body]
     * @return java.lang.Object
     **/
    @PostMapping("sendMess")
    public Object sendMess(@RequestBody String body){
        String templete_id = JacksonUtil.parseString(body, "templete_id");
        String first_str = JacksonUtil.parseString(body, "first");
        String keyword1_str = JacksonUtil.parseString(body, "keyword1");
        String keyword2_str = JacksonUtil.parseString(body, "keyword2");
        String keyword3_str = JacksonUtil.parseString(body, "keyword3");
        String keyword4_str = JacksonUtil.parseString(body, "keyword4");
        String keyword5_str = JacksonUtil.parseString(body, "keyword5");
        String remark_str = JacksonUtil.parseString(body, "remark");

        String url=JacksonUtil.parseString(body, "url");//跳转链接
        String pagepath=JacksonUtil.parseString(body, "pagepath");//小程序链接
        WxTempleteSendStatus sendStatus=new WxTempleteSendStatus();
        sendStatus.setTempleteId(templete_id);
        sendStatus.setTitle(first_str);
        wxTempleteSendStatusService.create(sendStatus);
        List<WxGzhUser> userList=wxGzhUserService.querySelective("","",null,null,null,"id asc");
        if(userList!=null&&userList.size()>0){
            for (WxGzhUser user:userList){
                JSONObject data=new JSONObject();
                data.put("touser",user.getOpenid());
                data.put("templete_id",templete_id);
                if(!StringUtils.isEmpty(url))
                    data.put("url",url);
                if(!StringUtils.isEmpty(pagepath))
                data.put("pagepath",pagepath);
                data.put("first",first_str);
                data.put("keyword1",keyword1_str);
                data.put("keyword2",keyword2_str);
                data.put("keyword3",keyword3_str);
                data.put("keyword4",keyword4_str);
                data.put("keyword5",keyword5_str);
                data.put("remark",remark_str);
                wxTempleteSendService.createBatch(data,sendStatus.getId());
          }
        }
        return ResponseUtil.ok();
    }
    //模板消息发送测试接口，未使用
    @PostMapping("send")
    public void send(){
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

    @PostMapping("fansSend")
    public Object fansSend(@RequestBody LinkedHashMap obBody){
        String openIds = (String) obBody.get("openIds");
        if (openIds==null || openIds.length()==0){
            return ResponseUtil.ok();
        }
        String[] ids = openIds.split(",");
        LinkedHashMap body = (LinkedHashMap) obBody.get("body");
        String templete_id = (String) body.get("templeteId");
        String first_str = (String) body.get("first");
        String keyword1_str = (String) body.get("keyword1");
        String keyword2_str = (String) body.get("keyword2");
        String keyword3_str = (String) body.get("keyword3");
        String keyword4_str = (String) body.get("keyword4");
        String keyword5_str = (String) body.get("keyword5");
        String remark_str = (String) body.get("remark");
        String url= (String) body.get("url");//跳转链接
        String pagepath= (String) body.get("pagepath");//小程序链接
        WxTempleteSendStatus sendStatus=new WxTempleteSendStatus();
        sendStatus.setTempleteId(templete_id);
        sendStatus.setTitle(first_str);
        wxTempleteSendStatusService.create(sendStatus);
        for (String id:ids){
            JSONObject data=new JSONObject();
            data.put("touser",id);
            data.put("templete_id",templete_id);
            if(!StringUtils.isEmpty(url)) {
                data.put("url",url);
            }
            if(!StringUtils.isEmpty(pagepath)) {
                data.put("pagepath",pagepath);
            }
            data.put("first",first_str);
            data.put("keyword1",keyword1_str);
            data.put("keyword2",keyword2_str);
            data.put("keyword3",keyword3_str);
            data.put("keyword4",keyword4_str);
            data.put("keyword5",keyword5_str);
            data.put("remark",remark_str);
            wxTempleteSendService.createBatch(data,sendStatus.getId());
        }
        return ResponseUtil.ok();
    }

    //粉丝管理列表接口
    @GetMapping("/list")
    public Object list(
            String openid, String nickname,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            String sort, String order){

        List<WxGzhUser> userList = wxGzhUserService.querySelective(openid, nickname, page,limit, sort, order);
        int total = wxGzhUserService.countSeletive(openid, nickname);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    //用户管理用户详情接口
    @GetMapping("/view")
    public Object list(Integer id){

        WxGzhUser user = wxGzhUserService.findById(id);

        return ResponseUtil.ok(user);
    }
    //模板消息发送状态列表
    @GetMapping("/listTempleteStatus")
    public Object listTempleteStatus(
            String templete_id, String title,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            String sort, String order){

        List<WxTempleteSendStatus> userList = wxTempleteSendStatusService.querySelective(templete_id, title, page,limit, sort, "create_date desc");
        int total = wxTempleteSendStatusService.countSeletive(templete_id, title);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);
        return ResponseUtil.ok(data);
    }

    //模板消息发送状态详情
    @GetMapping("/readTempleteStatus")
    public JSONObject readTempleteStatus(@RequestParam Integer id){
        JSONObject data=null;
        List<WxTempleteSend> sendList=wxTempleteSendService.queryByFlag(String.valueOf(id));
        if(sendList.size()>0){
            WxTempleteSend send=sendList.get(0);
            data=JSONObject.parseObject(send.getContent());
        }
        return data;
    }

    //模板消息发送百分比占比统计图接口
    @GetMapping("rate")
    public Object rate(@RequestParam Integer id){
        Map data=new HashMap();
        Integer sumAmount=0;//总数
        Integer finishAmount=0;//已完成发送数量
        Integer waitAmount=0;//待发送数量
        Integer failAmount=0;//发送失败数量

        sumAmount=wxTempleteSendService.countByFlag(String.valueOf(id),null);
        finishAmount=wxTempleteSendService.countByFlag(String.valueOf(id),1);
        waitAmount=wxTempleteSendService.countByFlag(String.valueOf(id),0);
        data.put("sumAmount",sumAmount);
        data.put("finishAmount",finishAmount);
        data.put("waitAmount",waitAmount);
        data.put("failAmount",failAmount);
        return data;
    }

    //粉丝管理界面勾选粉丝后发送模板消息接口
    @PostMapping("sendTempleteMess")
    public Object sendTempleteMess(@RequestBody String body){
        String templete_id = JacksonUtil.parseString(body, "templete_id");
        String first_str = JacksonUtil.parseString(body, "first");
        String keyword1_str = JacksonUtil.parseString(body, "keyword1");
        String keyword2_str = JacksonUtil.parseString(body, "keyword2");
        String keyword3_str = JacksonUtil.parseString(body, "keyword3");
        String keyword4_str = JacksonUtil.parseString(body, "keyword4");
        String keyword5_str = JacksonUtil.parseString(body, "keyword5");
        String remark_str = JacksonUtil.parseString(body, "remark");
        String url=JacksonUtil.parseString(body, "url");//跳转链接
        String pagepath=JacksonUtil.parseString(body, "pagepath");//小程序链接
        String userlist=JacksonUtil.parseString(body, "userIds");
        WxTempleteSendStatus sendStatus=new WxTempleteSendStatus();
        sendStatus.setTempleteId(templete_id);
        sendStatus.setTitle(first_str);
        wxTempleteSendStatusService.create(sendStatus);
        if(!StringUtils.isEmpty(userlist)){
            JSONArray userlist2=JSONArray.parseArray(userlist);
            for (int i = 0; i <userlist2.size() ; i++) {
                JSONObject data=new JSONObject();
                data.put("touser",userlist2.get(i));
                data.put("templete_id",templete_id);
                if(!StringUtils.isEmpty(url))
                    data.put("url",url);
                if(!StringUtils.isEmpty(pagepath))
                    data.put("pagepath",pagepath);
                data.put("first",first_str);
                data.put("keyword1",keyword1_str);
                data.put("keyword2",keyword2_str);
                data.put("keyword3",keyword3_str);
                data.put("keyword4",keyword4_str);
                data.put("keyword5",keyword5_str);
                data.put("remark",remark_str);
                wxTempleteSendService.createBatch(data,sendStatus.getId());
            }
        }
        return ResponseUtil.ok();
    }
}
