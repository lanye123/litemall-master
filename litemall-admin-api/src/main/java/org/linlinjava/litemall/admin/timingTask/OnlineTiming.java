package org.linlinjava.litemall.admin.timingTask;

import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.domain.WxGzhUser;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.service.WxFormidService;
import org.linlinjava.litemall.db.service.WxGzhUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description 定时上线图文
 * @Author lanye
 * @Date 2018/6/12 10:22
 **/
@Component
public class OnlineTiming {

    private final Log log = LogFactory.getLog(OnlineTiming.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private WxFormidService wxFormidService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private WxGzhUserService wxGzhUserService;
    @Value("${getUserList}")
    private String getUserUrl;
    @Value("${getUserInfo}")
    private String getUserInfoUrl;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 30 0 * * ?")
    public void online(){
        long begin = System.currentTimeMillis();
        log.debug("每天零点三十执行定时任务");
        log.info("图文上线开始-------"+new Date());
        List<Article> articleList =  articleService.queryBySelective("","",null,1,"",simpleDateFormat.format(new Date()), null,null, null, "", "");
        for(Article article:articleList){
            if(article.getStatus()==1){
                continue;
            }
            article.setStatus(1);
            article.setCreateDate(simpleDateFormat2.format(new Date()));
            articleService.updateById(article);
        }
        long end = System.currentTimeMillis();
        log.info("图文上线结束-------耗时："+(end-begin)+"ms");
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void getUserList1(){
        getUserList();
    }

    @Scheduled(cron = "0 0 20 * * ?")
    public void getUserList2(){
        getUserList();
    }

    public void getUserList(){
        long begin = System.currentTimeMillis();
        log.debug("执行定时同步用户信息");
        log.info("定时同步用户信息开始-------"+new Date());
        try {
            WxConfig config = wxConfigService.getGzhToken();
            String url = getUserUrl.replace("ACCESS_TOKEN",config.getAccessToken());
            String infoUrl = getUserInfoUrl.replace("ACCESS_TOKEN",config.getAccessToken());
            List<WxGzhUser> userList = wxGzhUserService.querySelective("","",null,null,"","id desc");
            String next_openid = "";
            if(userList!=null && userList.size()>0){
                next_openid = userList.get(0).getOpenid();
            }
            url = url.replace("NEXT_OPENID",next_openid);
            JSONObject jsonObject = HttpClientUtil.doGet(url);
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray jsonArray =  JSONArray.parseArray(jsonObject.get("openid").toString());
            String openid;
            //用户对象DB存储
            WxGzhUser user = new WxGzhUser();
            int i = 1;
            for (Object ob : jsonArray) {
                openid = (String) ob;
                infoUrl = infoUrl.replace("OPENID",openid);
                jsonObject = HttpClientUtil.doGet(infoUrl);
                infoUrl = infoUrl.replace(openid,"OPENID");
                log.info("获取第"+i+"条用户信息结果-------"+jsonObject);
                user = new WxGzhUser();
                user.setSubscribe(((Integer) jsonObject.get("subscribe")).byteValue());
                user.setOpenid((String) jsonObject.get("openid"));
                user.setNickname((String) jsonObject.get("nickname"));
                user.setSex((Integer) jsonObject.get("sex"));
                user.setLanguage((String) jsonObject.get("language"));
                user.setCity((String) jsonObject.get("city"));
                user.setProvince((String) jsonObject.get("province"));
                user.setCountry((String) jsonObject.get("country"));
                user.setHeadimgurl((String) jsonObject.get("headimgurl"));
                user.setSubscribeTime(Long.valueOf((Integer)jsonObject.get("subscribe_time")));
                user.setUnionid((String) jsonObject.get("unionid"));
                user.setRemark((String) jsonObject.get("remark"));
                user.setGroupid((Integer) jsonObject.get("groupid"));
                user.setTagidList(((net.sf.json.JSONArray) jsonObject.get("tagid_list")).toString());
                user.setSubscribeScene((String) jsonObject.get("subscribe_scene"));
                user.setQrScene((Integer) jsonObject.get("qr_scene"));
                user.setQrSceneStr((String) jsonObject.get("qr_scene_str"));
                wxGzhUserService.add(user);
                log.info("插入第"+i+"条记录成功，user信息-------"+user.toString());
                i++;
            }
        } catch (Exception e) {
            log.error("定时同步用户信息失败"+e);
            e.printStackTrace();
            return;
        }
        long end = System.currentTimeMillis();
        log.info("定时同步用户信息结束-------耗时："+(end-begin)+"ms");
    }

    public static void main(String[] args){
//        WxConfig config =
//        //create_codeB_url.replace("ACCESS_TOKEN",config.getAccessToken());
//        //String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
//        JSONObject jsonObject = HttpClientUtil.doGet(url);
//        System.out.println(jsonObject);
    }
}