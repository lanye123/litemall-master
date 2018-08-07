package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/help")
public class HelpController {
    private final Log logger = LogFactory.getLog(HelpController.class);

    @Autowired
    private LitemallGoodsService goodsService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/list")
    public Object list(String userId) {
        Map<String, Object> data = new HashMap<>();
        //只查询书籍的商品列表
        List<LitemallGoods> goodsList = goodsService.queryByCategory(1036005, 3,0,20);
        data.put("goodsList", goodsList);

        return ResponseUtil.ok(data);
    }

}