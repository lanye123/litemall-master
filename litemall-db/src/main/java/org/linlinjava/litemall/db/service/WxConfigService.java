package org.linlinjava.litemall.db.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.dao.WxConfigMapper;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class WxConfigService {
    @Resource
    private WxConfigMapper wxConfigMapper;

    @Value("${access_token.url}")
    private String token_url;

    //token更新
    public WxConfig getToken(){
        WxConfig config=wxConfigMapper.selectByPrimaryKey(1);
        // 判断当前时间是否大于到期时间 如果大于则重新获取
        System.out.println(config.getEndDate()+"#######"+DateUtils.getCurrentDate());
        System.out.println(config.getEndDate().compareTo(DateUtils.getCurrentDate())<0);
        if(StringUtils.isEmpty(config.getAccessToken())||config.getEndDate().compareTo(DateUtils.getCurrentDate())<0){
            String requestUrl=token_url.replace("APPID",config.getAppid()).replace("APPSECRET",config.getSecret());
            JSONObject re = HttpClientUtil.doGet(requestUrl);
            config.setStartDate(DateUtils.getCurrentDate());
            config.setEndDate(DateUtils.addSecond(DateUtils.getCurrentDate(),config.getInterval()));
            config.setAccessToken(re.getString("access_token"));
            wxConfigMapper.updateByPrimaryKey(config);
        }
        return config;
    }
}
