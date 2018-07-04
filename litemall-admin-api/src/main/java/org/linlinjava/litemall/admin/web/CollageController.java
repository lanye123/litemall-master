package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.CollageDetail;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.CollageDetailService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/collage")
public class CollageController {
    private final Log logger = LogFactory.getLog(CollageController.class);

    @Autowired
    private CollageDetailService collageDetailService;
    @Autowired
    private LitemallOrderService litemallOrderService;

    @GetMapping("/list")
    public Object list(Integer userId, Integer orderId,Integer goodsId,Integer status,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        order = "create_date desc";
        List<CollageDetail> collageDetailList = collageDetailService.queryBySelective(userId, orderId, goodsId,status,page, limit, sort, order);
        int total = collageDetailService.count(userId, orderId,goodsId,status);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collageDetailList);

        return ResponseUtil.ok(data);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/create")
    public Object create(@RequestBody CollageDetail collageDetail){
        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read(Integer id){

        CollageDetail collageDetail = collageDetailService.findById(id);
        return ResponseUtil.ok(collageDetail);
    }

    @PostMapping("/update")
    public Object update(@RequestBody CollageDetail collageDetail){
        collageDetailService.update(collageDetail);
        return ResponseUtil.ok(collageDetail);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody CollageDetail collageDetail){
        if(collageDetail==null){
            return ResponseUtil.fail();
        }
        collageDetailService.deleteById(collageDetail.getId());
        return ResponseUtil.unsupport();
    }

    @PostMapping("/wincode")
    public Object wincode(@RequestBody CollageDetail collageDetailV){
        if(collageDetailV==null){
            return ResponseUtil.fail();
        }
        CollageDetail collageDetail = collageDetailService.findById(collageDetailV.getId());
        if(collageDetail==null){
            return ResponseUtil.fail(110,"拼团单不存在呀");
        }
        String sno = collageDetail.getSno();
        if(StringUtils.isEmpty(sno)){
            return ResponseUtil.fail(102,"sno是空的");
        }
        List<CollageDetail> collageDetailList = collageDetailService.queryBySelective2(collageDetailV.getPid(), null, collageDetail.getGoodsId(),1,null, null, null, null);
        for(CollageDetail collageDetailDb:collageDetailList){
            collageDetailDb.setWincode(sno);
            collageDetailService.update(collageDetailDb);
            LitemallOrder order=litemallOrderService.findById(collageDetailDb.getOrderId());
            if(order!=null) {
                order.setOrderStatus((short) OrderUtil.win);
                litemallOrderService.update(order);
            }

        }
        return ResponseUtil.ok();
    }

}
