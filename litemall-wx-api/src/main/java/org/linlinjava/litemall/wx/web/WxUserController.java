package org.linlinjava.litemall.wx.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    @Value("${access_token.url}")
    private String token_url;
    @Value("${create_codeA.url}")
    private String create_codeA_url;
    @Value("${create_codeB.url}")
    private String create_codeB_url;
    @Value("${create_codeC.url}")
    private String create_codeC_url;

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

    //获取access_token
    @GetMapping("/token")
    public String getAcessToken(){
        String requestUrl=token_url.replace("APPID",appid).replace("APPSECRET",secret);
        JSONObject re = HttpClientUtil.doGet(requestUrl);
        return re.getString("access_token");
    }

    //获取access_token
    @PostMapping("/createCode")
    public Object createCode(@RequestBody WxCode code){
        String token=getAcessToken();
        JSONObject object=new JSONObject();
        object.put("path",code.getPath());
        object.put("width",code.getWidth());
        String requestUrl=create_codeC_url.replace("ACCESS_TOKEN",token);
         InputStream i=HttpClientUtil.doPostInstream(requestUrl,object);
        byte[] data = new byte[1024];
        int len = -1;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:\\test1.jpg");
            while ((len = i.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            if (i != null) {
                try {
                    i.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return ResponseUtil.ok();
    }
}