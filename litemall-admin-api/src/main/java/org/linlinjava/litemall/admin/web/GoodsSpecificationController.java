package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;
import org.linlinjava.litemall.db.service.LitemallGoodsSpecificationService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods-specification")
public class GoodsSpecificationController {
    private final Log logger = LogFactory.getLog(GoodsSpecificationController.class);

    @Autowired
    private LitemallGoodsSpecificationService goodsSpecificationService;

    @GetMapping("/list")
    public Object list(
                       Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallGoodsSpecification> goodsSpecificationList = goodsSpecificationService.querySelective(goodsId, page, limit, sort, "add_time desc");
        int total = goodsSpecificationService.countSelective(goodsId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsSpecificationList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallGoodsSpecification goodsSpecification){

        goodsSpecificationService.add(goodsSpecification);
        return ResponseUtil.ok(goodsSpecification);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallGoodsSpecification goodsSpecification = goodsSpecificationService.findById(id);
        return ResponseUtil.ok(goodsSpecification);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallGoodsSpecification goodsSpecification){

        goodsSpecificationService.updateById(goodsSpecification);
        return ResponseUtil.ok(goodsSpecification);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallGoodsSpecification goodsSpecification){

        goodsSpecificationService.deleteById(goodsSpecification.getId());
        return ResponseUtil.ok();
    }

    @GetMapping("/volist")
    public Object volist( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        Object goodsSpecificationVoList = goodsSpecificationService.getSpecificationVoList(id);
        return ResponseUtil.ok(goodsSpecificationVoList);
    }

}
