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
        goodsService.updateById(goods);
        return ResponseUtil.ok(goods);
    }

    public static void main(String[] args){
        LitemallGoods goods = new LitemallGoods();
        String aa = "#[\"images/goods/1530001841517.jpg\"]#[\"images/goods/1530001841718.jpg\"]";
        String bb = "[\"https://sunlands.ministudy.com/images/goods/iphonexdetail1.jpg\",\"https://sunlands.ministudy.com/images/goods/iphonexdetail2.jpg\",\"https://sunlands.ministudy.com/images/goods/iphonexdetail3.jpg\",\"https://sunlands.ministudy.com/images/goods/iphonexdetail4.jpg\"]#[\"images/upload/1529999891852.jpg\"]#[\"images/upload/1529999892008.jpg\"]#[\"images/upload/1529999892120.jpg\"]#";
        goods.setGallery(aa);
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
        System.out.println(galleryArray.toJSONString());
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallGoods goods){
        goodsService.deleteById(goods.getId());
        return ResponseUtil.ok();
    }

}
