package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.domain.WxGzhUser;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.service.WxGzhUserService;
import org.linlinjava.litemall.db.service.WxTempleteSendService;
import org.linlinjava.litemall.db.service.WxTempleteService;
import org.linlinjava.litemall.db.util.JacksonUtil;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Value("${templete.url}")
    private String templeteurl;

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

    @PostMapping("sendMess")
    public JSONObject sendMess(@RequestBody String body){
        String templete_id = JacksonUtil.parseString(body, "templete_id");

        String first = JacksonUtil.parseString(body, "first");
        String keyword1 = JacksonUtil.parseString(body, "keyword1");
        String keyword2 = JacksonUtil.parseString(body, "keyword2");
        String keyword3 = JacksonUtil.parseString(body, "keyword3");
        String keyword4 = JacksonUtil.parseString(body, "keyword4");
        String keyword5 = JacksonUtil.parseString(body, "keyword5");
        String remark = JacksonUtil.parseString(body, "remark");
        JSONObject data=null;
        //List<WxGzhUser> userList=wxGzhUserService.list();
        //return wxTempleteSendService.createBatch(data);
        return null;
    }


}
