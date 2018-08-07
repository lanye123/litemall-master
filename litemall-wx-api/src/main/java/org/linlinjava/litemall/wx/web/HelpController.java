package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.HelpDetailService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/help")
public class HelpController {
    private final Log logger = LogFactory.getLog(HelpController.class);

    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private HelpDetailService helpDetailService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/list")
    public Object list(Integer userId) {
        Map<String, Object> data = new HashMap<>();
        List<LitemallGoods> goodsList = goodsService.helpList(1036005, userId);
        if(goodsList==null || goodsList.size()==0){
            return ResponseUtil.ok(data);
        }
        List<Map<String, Object>> goodsVoList = new ArrayList<>(goodsList.size());
        for (LitemallGoods goods : goodsList) {
            Map<String,Object> goodsVo = new HashMap<>();
            goodsVo.put("id",goods.getId());
            goodsVo.put("actPicUrl",goods.getActPicUrl());
            goodsVo.put("memo",goods.getMemo());
            goodsVo.put("name",goods.getName());
            goodsVo.put("orderStatus",goods.getOrderStatus());
            goodsVo.put("count",helpDetailService.countByGoodsId(goods.getId()));
            goodsVoList.add(goodsVo);
        }
        data.put("goodsVoList", goodsVoList);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/detail")
    public Object detail(Integer userId,Integer id) {
        Map<String, Object> data = new HashMap<>();
        LitemallGoods goods = goodsService.findById(id);
        data.put("count",helpDetailService.countByGoodsId(id));
        data.put("goods", goods);
        return ResponseUtil.ok(data);
    }

}