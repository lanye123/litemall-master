package org.linlinjava.litemall.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.service.IntegretionDetailService;
import org.linlinjava.litemall.db.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DbTest {
    @Resource
    private IntegretionDetailService integretionDetailService;
    @Test
    public void test() {
        Map<String,Object> data = new HashMap<>();
        List<IntegretionDetail> integretionDetailList=integretionDetailService.querySelective("1");
        IntegretionDetail a=new IntegretionDetail();
        for (IntegretionDetail detail:integretionDetailList)
        {
            System.out.println(DateUtils.localToDate(detail.getCreateDate())+"  ###"+DateUtils.compareDate(DateUtils.localToDate(detail.getCreateDate()),DateUtils.dateFormat(new Date()),5));
            //integretionDetailService.days(integretionDetail);//
        }
    }

}
