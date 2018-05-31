package org.linlinjava.litemall.wx.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wx/loginInfo")
public class WxLoginInfoController {
    private final Log logger = LogFactory.getLog(WxLoginInfoController.class);
    /**
     * 获取微信小程序 session_key 和 openid
     *
     * @param code 调用微信登陆返回的Code
     * @return
     *
     */
    @Value("${miniprogram.appid}")
    private String appid;
    @Value("${miniprogram.secret}")
    private String secret;
    @Value("${miniprogram.url}")
    private String url;

@GetMapping("getOpenid")
    public Object getOropenid(String code) {
        //微信端登录code值
        String wxCode = code;
        String requestUrl=url.replace("APPID",appid).replace("SECRET",secret).replace("CODE",code);
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        JSONObject re = HttpClientUtil.doGet(url);
        return ResponseUtil.ok(re);
    }

    public static void main(String[] args){
        //微信端登录code值
        String wxCode = "0232xqXG1I7gp701HwXG1ghhXG12xqXl";
        String url="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String requestUrl=url.replace("APPID","wx97aa39b40d7412cb").replace("SECRET","25f2dfd586910c80c2be4c3bcab06373").replace("JCODE","033BnRgg1SmMay0Kmkeg1DbAgg1BnRgW");
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        JSONObject re = HttpClientUtil.doGet(url);
        System.out.println(re.get("nickName"));
    }
}
