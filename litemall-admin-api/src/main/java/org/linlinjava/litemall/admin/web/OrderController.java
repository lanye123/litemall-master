package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private final Log logger = LogFactory.getLog(OrderController.class);

    @Autowired
    private LitemallOrderService orderService;

    @GetMapping("/list")
    public Object list(
                       Integer userId, String orderSn,Integer orderType,Short orderStatus,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        List<LitemallOrder> orderList = orderService.querySelective(userId, orderSn,orderType,orderStatus, page, limit, sort, order);
        int total = orderService.countSelective(userId, orderSn, orderType,orderStatus, page, limit, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", orderList);

        return ResponseUtil.ok(data);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/create")
    public Object create( @RequestBody LitemallOrder order){

        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read( Integer id){
        LitemallOrder order = orderService.findById(id);
        return ResponseUtil.ok(order);
    }

    /*
     * 目前仅仅支持管理员设置发货相关的信息
     */
    @PostMapping("/update")
    public Object update( @RequestBody LitemallOrder order){


        Integer orderId = order.getId();
        if(orderId == null){
            return ResponseUtil.badArgument();
        }

        LitemallOrder litemallOrder = orderService.findById(orderId);
        if(litemallOrder == null){
            return ResponseUtil.badArgumentValue();
        }

        if(OrderUtil.isPayStatus(litemallOrder) || OrderUtil.isShipStatus(litemallOrder)){
            LitemallOrder newOrder = new LitemallOrder();
            newOrder.setId(orderId);
            newOrder.setShipChannel(order.getShipChannel());
            newOrder.setShipSn(order.getOrderSn());
            newOrder.setShipStartTime(order.getShipStartTime());
            newOrder.setShipEndTime(order.getShipEndTime());
            newOrder.setOrderStatus(OrderUtil.STATUS_SHIP);
            orderService.update(newOrder);
        }
        else {
            return ResponseUtil.badArgumentValue();
        }

        litemallOrder = orderService.findById(orderId);
        return ResponseUtil.ok(litemallOrder);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallOrder order){

        return ResponseUtil.unsupport();
    }

}
