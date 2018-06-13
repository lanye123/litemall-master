package org.linlinjava.litemall.wx.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private WxConfigService wxConfigService;

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
        user.setNickname(filterEmoji(user.getNickname()));
        litemallUserService.add(user);
        return ResponseUtil.ok(user);
    }

    public static String filterEmoji(String source) {
        if (source == null) {
            return source;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("*");
            return source;
        }
        return source;
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
    public Object getAcessToken(){
        WxConfig config=wxConfigService.getToken();
       return ResponseUtil.ok(config);
    }

    /**
      * @author lanye
      * @Description 获取用户信息
      * @Date 2018/6/5 10:54
      * @Param []
      * @return java.lang.Object
      **/
    @GetMapping("/user")
    public Object getUser(Integer userId,String openId){
        List<LitemallUser> userList = litemallUserService.querySelective("","",openId,null,null,"","");
        if(userList==null || userList.size()<=0){
            return  ResponseUtil.fail(500,"该openId不存在");
        }else {
            return  ResponseUtil.ok(userList);
        }
    }

    /**
      * @author lanye
      * @Description 更新用户信息
      * @Date 2018/6/5 10:56
      * @Param [user]
      * @return java.lang.Object
      **/
    @PostMapping("/update")
    public Object getUser(@RequestBody LitemallUser user){
        if(user==null){
            return ResponseUtil.badArgument();
        }
        logger.debug(user);

        litemallUserService.update(user);
        return ResponseUtil.ok(user);
    }
}