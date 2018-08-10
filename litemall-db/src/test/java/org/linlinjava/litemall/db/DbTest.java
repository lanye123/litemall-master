package org.linlinjava.litemall.db;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.service.WxMessService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DbTest {
    @Resource
    private WxMessService wxMessService;
    @Test
    public void test() {
       JSONObject res= wxMessService.articleCheck("pages/card/main","早上8点早客户CASIO办公室接2个客人到厂里做质检","2018-02-15 12:25:30","2017-05-04 12:30:30","123123",68);
        System.out.println(res);
    }

    @Test
    public void jxfTest() {
    }

}
