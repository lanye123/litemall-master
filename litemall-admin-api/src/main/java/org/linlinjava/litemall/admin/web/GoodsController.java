package org.linlinjava.litemall.admin.web;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods")
public class GoodsController {
    private final Log logger = LogFactory.getLog(GoodsController.class);

    @Autowired
    private LitemallGoodsService goodsService;

    private final static String PREFIX = "https://sunlands.ministudy.com/";

    @GetMapping("/list")
    public Object list(String goodsSn, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit2", defaultValue = "10") Integer limit2,
                       String sort, String order){

        List<LitemallGoods> goodsList = goodsService.querySelective(goodsSn, name, page, limit2, sort, "create_date desc");
        int total = goodsService.countSelective(goodsSn, name, page, limit2, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody LitemallGoods goods){
        logger.info("before goods："+goods);
        Long date = new Date().getTime();
        if(goods.getStartDate()!=null && goods.getEndDate()!=null) {
            if (date <= goods.getEndDate().getTime() && date >= goods.getStartDate().getTime()) {
                goods.setStatus(0);
            } else {
                goods.setStatus(1);
            }
        }
        if(goods.getGallery().contains("#")){
            String[] gallery = goods.getGallery().split("#");
            JSONArray galleryArray = new JSONArray();
            JSONArray jj;
            for(int i=0;i<gallery.length;i++){
                if(StringUtils.isEmpty(gallery[i])){
                    continue;
                }
                jj = JSONArray.parseArray(gallery[i]);
                galleryArray.add(PREFIX+jj.get(0));
            }
            goods.setGallery(galleryArray.toJSONString());
            logger.info("after goods："+goods);
        }
        if(goods.getListPicUrl().contains("#")){
            String[] listPicUrl = goods.getListPicUrl().split("#");
            JSONArray listPicUrlArray = new JSONArray();
            JSONArray jj;
            for(int i=0;i<listPicUrl.length;i++){
                if(StringUtils.isEmpty(listPicUrl[i])){
                    continue;
                }
                jj = JSONArray.parseArray(listPicUrl[i]);
                listPicUrlArray.add(PREFIX+jj.get(0));
            }
            goods.setListPicUrl(listPicUrlArray.toJSONString());
            logger.info("after goods："+goods);
        }
        if(!goods.getPrimaryPicUrl().contains(PREFIX)){
            goods.setPrimaryPicUrl(PREFIX+goods.getPrimaryPicUrl());
        }
        if(!goods.getActPicUrl().contains(PREFIX)){
            goods.setActPicUrl(PREFIX+goods.getActPicUrl());
        }
        if(!goods.getPosterPicUrl().contains(PREFIX)){
            goods.setPosterPicUrl(PREFIX+goods.getPosterPicUrl());
        }
        goodsService.add(goods);
        return ResponseUtil.ok(goods);
    }

    @GetMapping("/read")
    public Object read(Integer id){

        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallGoods goods = goodsService.findById(id);
        return ResponseUtil.ok(goods);
    }

    @PostMapping("/update")
    public Object update(@RequestBody LitemallGoods goods){
        logger.info("before  goods："+goods);
        Long date = new Date().getTime();
        if(goods.getStartDate()!=null && goods.getEndDate()!=null){
            if(date<=goods.getEndDate().getTime() && date>=goods.getStartDate().getTime()){
                goods.setStatus(0);
            }else{
                goods.setStatus(1);
            }
        }
        if(goods.getGallery().contains("#")){
            String[] gallery = goods.getGallery().split("#");
            JSONArray galleryArray = new JSONArray();
            JSONArray jj;
            for(int i=1;i<gallery.length;i++){
                jj = JSONArray.parseArray(gallery[i]);
                galleryArray.add(PREFIX+jj.get(0));
            }
            goods.setGallery(galleryArray.toJSONString());
            logger.info("after  goods："+goods);
        }
        if(goods.getListPicUrl().contains("#")){
            String[] listPicUrl = goods.getListPicUrl().split("#");
            JSONArray listPicUrlArray = new JSONArray();
            JSONArray jj;
            for(int i=1;i<listPicUrl.length;i++){
                jj = JSONArray.parseArray(listPicUrl[i]);
                listPicUrlArray.add(PREFIX+jj.get(0));
            }
            goods.setListPicUrl(listPicUrlArray.toJSONString());
            logger.info("after  goods："+goods);
        }
        if(!goods.getPrimaryPicUrl().contains(PREFIX)){
            goods.setPrimaryPicUrl(PREFIX+goods.getPrimaryPicUrl());
        }
        if(!goods.getActPicUrl().contains(PREFIX)){
            goods.setActPicUrl(PREFIX+goods.getActPicUrl());
        }
        if(!goods.getPosterPicUrl().contains(PREFIX)){
            goods.setPosterPicUrl(PREFIX+goods.getPosterPicUrl());
        }
        goodsService.updateById(goods);
        return ResponseUtil.ok(goods);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallGoods goods){
        goodsService.deleteById(goods.getId());
        return ResponseUtil.ok();
    }

    @PostMapping("/online")
    public Object online(@RequestBody LitemallGoods goods){
        if(goods == null){
            return ResponseUtil.badArgument();
        }

        LitemallGoods goodsDb = goodsService.findById(goods.getId());
        if(goodsDb==null){
            return ResponseUtil.ok();
        }
        if(goodsDb.getState()==0){
            goodsDb.setState(1);
            goodsService.updateById(goodsDb);
        }else if(goodsDb.getState()==1){
            goodsDb.setState(0);
            goodsService.updateById(goodsDb);
        }
        return ResponseUtil.ok(goodsDb);
    }

}
