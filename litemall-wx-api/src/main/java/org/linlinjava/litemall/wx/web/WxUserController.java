package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;

    @Value("${miniprogram.url}")
    private String url;
    @Value("${miniprogram.appid}")
    private String appid;
    @Value("${miniprogram.secret}")
    private String secret;

    @PostMapping("/create")
    public Object create(@RequestBody LitemallUser user){
        logger.debug(user);

        if(litemallUserService.countSeletive("","",user.getWeixinOpenid(),null,null,"","")>0){
            return ResponseUtil.ok(litemallUserService.querySelective("","",user.getWeixinOpenid(),null,null,"","").get(0));
        }

        litemallUserService.add(user);
        return ResponseUtil.ok(user);
    }

    //获取微信用户信息
    @GetMapping("/code")
    public Object getUserInfo(String code){
        String requestUrl=url.replace("APPID",appid).replace("CODE",code).replace("SECRET",secret);
        JSONObject re = HttpClientUtil.doGet(requestUrl);
        return ResponseUtil.ok(re);
    }

}