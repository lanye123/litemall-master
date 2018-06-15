package org.linlinjava.litemall.admin.timingTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.WxFormid;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.service.WxFormidService;
import org.springframework.beans.factory.annotation.Autowired;
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

        log.info("定时清理过期formid开始......"+new Date());
        List<WxFormid> formidList=wxFormidService.queryByStatus(1);
        for(WxFormid formid:formidList){
            wxFormidService.delete(formid.getId());
        }
        log.info("定时清理过期formid结束"+new Date());
    }

    public static void main(String[] args){
    }
}