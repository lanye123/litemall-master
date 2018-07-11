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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/admin/collage")
public class CollageController {
    private final Log logger = LogFactory.getLog(CollageController.class);

    @Autowired
    private CollageDetailService collageDetailService;
    @Autowired
    private LitemallOrderService litemallOrderService;

    @GetMapping("/list")
    public Object list(Integer userId, Integer orderId, Integer goodsId, Integer status, String startDate, String endDate,Integer type,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order) throws ParseException {

        order = "create_date desc";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        if(!StringUtils.isEmpty(startDate)){
            start = simpleDateFormat.parse(startDate);
        }
        if(!StringUtils.isEmpty(endDate)){
            end = simpleDateFormat.parse(endDate);
        }
        List<CollageDetail> collageDetailList = collageDetailService.queryBySelective3(userId, orderId, goodsId,status,start,end,type,page, limit, sort, order);
        int total = collageDetailService.count2(userId, orderId,goodsId,status,start,end,type);

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
        //拼团成功才更新该团所有成员的memo
        if(collageDetail.getStatus()==1){
            List<CollageDetail> collageDetailList = collageDetailService.queryById(collageDetail.getPid());
            for(CollageDetail collageDetailDb:collageDetailList){
                collageDetailDb.setMemo(collageDetail.getMemo());
                collageDetailService.update(collageDetailDb);
            }
        }
        return ResponseUtil.ok(collageDetail);
    }

    @PostMapping("/custom")
    public Object custom(@RequestBody Map<String,Object> ob){
        List<CollageDetail> collageDetailList = (List<CollageDetail>) ob.get("coll");
        if(collageDetailList == null || collageDetailList.size()==0){
            return ResponseUtil.ok();
        }
        String memo = (String) ob.get("memo");
        if(StringUtils.isEmpty(memo)){
            return ResponseUtil.ok();
        }
        Map<String,Object> map;
        CollageDetail collageDetailDb;
        for(Object collageDetail:collageDetailList){
            map = (LinkedHashMap<String, Object>) collageDetail;
            collageDetailDb = collageDetailService.findById((Integer) map.get("id"));
            if(collageDetailDb==null){
                continue;
            }
            collageDetailDb.setMemo(memo);
            collageDetailService.update(collageDetailDb);
        }
        return ResponseUtil.ok(ob);
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
            if(collageDetailDb.getId().intValue()==collageDetailV.getId().intValue()){
                collageDetailDb.setStatus(4);
            }
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

    @PostMapping("/second")
    public Object second(@RequestBody List<CollageDetail> collageDetailList){
        for(CollageDetail collageDetail:collageDetailList){
            collageDetail.setStatus(6);
            collageDetailService.update(collageDetail);
        }
        return ResponseUtil.ok(collageDetailList);
    }

}
