package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallGoodsAttribute;
import org.linlinjava.litemall.db.service.LitemallGoodsAttributeService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods-attribute")
public class GoodsAttributeController {
    private final Log logger = LogFactory.getLog(GoodsAttributeController.class);

    @Autowired
    private LitemallGoodsAttributeService goodsAttributeService;

    @GetMapping("/list")
    public Object list(
                       Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallGoodsAttribute> goodsAttributeList = goodsAttributeService.querySelective(goodsId, page, limit, sort, order);
        int total = goodsAttributeService.countSelective(goodsId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsAttributeList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallGoodsAttribute goodsAttribute){

        goodsAttributeService.add(goodsAttribute);
        return ResponseUtil.ok(goodsAttribute);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallGoodsAttribute goodsAttribute = goodsAttributeService.findById(id);
        return ResponseUtil.ok(goodsAttribute);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallGoodsAttribute goodsAttribute){

        goodsAttributeService.updateById(goodsAttribute);
        return ResponseUtil.ok(goodsAttribute);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallGoodsAttribute goodsAttribute){

        goodsAttributeService.deleteById(goodsAttribute.getId());
        return ResponseUtil.ok();
    }

}
