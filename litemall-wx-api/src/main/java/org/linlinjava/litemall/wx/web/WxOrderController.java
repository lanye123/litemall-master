package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/*
 * 订单设计
 *
 * 订单状态：
 * 101 订单生成，未支付；102，订单生产，但是未支付就取消；
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，但是退款取消；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货，订单结束； 402 用户没有确认收货，但是快递反馈已收获后，超过一定时间，系统自动确认收货，订单结束。
 *
 * 当101用户未付款时，此时用户可以进行的操作是取消订单，或者付款操作
 * 当201支付完成而商家未发货时，此时用户可以取消订单并申请退款
 * 当301商家已发货时，此时用户可以有确认收货的操作
 * 当401用户确认收货以后，此时用户可以进行的操作是删除订单，评价商品，或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除订单，评价商品，或者再次购买
 *
 * 目前不支持订单退货和售后服务
 *
 */
@RestController
@RequestMapping("/wx/order")
public class WxOrderController {
    private final Log logger = LogFactory.getLog(WxOrderController.class);

    @Autowired
    private PlatformTransactionManager txManager;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private LitemallAddressService addressService;
    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallRegionService regionService;
    @Autowired
    private LitemallProductService productService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private CollageDetailService collageDetailService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private IntegretionDetailService integretionDetailService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;

    public WxOrderController() {
    }

    private String detailedAddress(LitemallAddress litemallAddress) {
        Integer provinceId = litemallAddress.getProvinceId();
        Integer cityId = litemallAddress.getCityId();
        Integer areaId = litemallAddress.getAreaId();
        String provinceName = regionService.findById(provinceId).getName();
        String cityName = regionService.findById(cityId).getName();
        String areaName = regionService.findById(areaId).getName();
        String fullRegion = provinceName + " " + cityName + " " + areaName;
        return fullRegion + " " + litemallAddress.getAddress();
    }

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息
     *                 0， 全部订单
     *                 1，待付款
     *                 2，待发货
     *                 3，待收货
     *                 4，待评价
     * @param page     分页页数
     * @param size     分页大小
     * @return 订单操作结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * data: xxx ,
     * count: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @RequestMapping("list")
    public Object list(Integer userId, Integer showType,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (userId == null) {
            return ResponseUtil.fail401();
        }
        if (showType == null) {
            showType = 0;
        }

        List<Short> orderStatus = OrderUtil.orderStatus(showType);
        List<LitemallOrder> orderList = orderService.queryByOrderStatus(userId, orderStatus);
        int count = orderService.countByOrderStatus(userId, orderStatus);

        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
        for (LitemallOrder order : orderList) {
            Map<String, Object> orderVo = new HashMap<>();
            orderVo.put("id", order.getId());
            orderVo.put("orderSn", order.getOrderSn());
            orderVo.put("actualPrice", order.getActualPrice());
            orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
            //orderVo.put("handleOption", OrderUtil.build(order));

            List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>();
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
                orderGoodsVo.put("number", orderGoods.getNumber());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                orderGoodsVo.put("goodsSpecificationValues", orderGoods.getGoodsSpecificationValues());
                //orderGoodsVo.put("retailPrice", orderGoods.getRetailPrice());
                LitemallGoods goods = goodsService.findById(orderGoods.getGoodsId());
                if(goods == null){
                    continue;
                }
                orderGoodsVo.put("goodsBrief", goods.getGoodsBrief());
                orderGoodsVo.put("integretion", goods.getIntegretion());
                orderGoodsVo.put("price",goods.getCounterPrice());
                orderGoodsVoList.add(orderGoodsVo);
            }
            orderVo.put("goodsList", orderGoodsVoList);

            orderVoList.add(orderVo);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("data", orderVoList);

        return ResponseUtil.ok(result);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单信息
     * @return 订单操作结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * orderInfo: xxx ,
     * orderGoods: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("detail")
    public Object detail(Integer userId, Integer orderId) {
        if (userId == null) {
            return ResponseUtil.fail401();
        }
        if (orderId == null) {
            return ResponseUtil.fail402();
        }

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> orderVo = new HashMap<String, Object>();
        //查出该笔订单的拼团信息
        List<CollageDetail> collageDetailList = collageDetailService.queryBySelective(orderId,null,null,null,null,"","create_date");
        List<CollageDetail> collageDetailList2 = collageDetailService.queryById(orderId);

        List<Map<String, Object>> userVoList = new ArrayList<>(collageDetailList.size());
        Map<String, Object> userVo;
        LitemallUser user;
        Integer goodsId = null;
        for (CollageDetail collageDetail : collageDetailList) {
            if(userId == collageDetail.getUserId()){
                orderVo.put("sno",collageDetail.getSno());
                if(collageDetail.getCreateDate().contains(".0")){
                    collageDetail.setCreateDate(collageDetail.getCreateDate().substring(0,collageDetail.getCreateDate().length()-2));
                }
                orderVo.put("groupTime", collageDetail.getCreateDate());
            }
            userVo = new HashMap<>();
            user = litemallUserService.findById(collageDetail.getUserId());
            if(user == null){
                continue;
            }
            userVo.put("id", user.getId());
            userVo.put("nickName", user.getNickname());
            userVo.put("avatar", user.getAvatar());
            if(collageDetail.getPid()==0){
                userVo.put("master", 0);
                goodsId = collageDetail.getGoodsId();
            }else{
                userVo.put("master", 1);
            }
            userVoList.add(userVo);
        }
        for (CollageDetail collageDetail : collageDetailList2) {
            if(userId == collageDetail.getUserId()){
                orderVo.put("sno",collageDetail.getSno());
                if(collageDetail.getCreateDate().contains(".0")){
                    collageDetail.setCreateDate(collageDetail.getCreateDate().substring(0,collageDetail.getCreateDate().length()-2));
                }
                orderVo.put("groupTime", collageDetail.getCreateDate());
            }
            userVo = new HashMap<>();
            user = litemallUserService.findById(collageDetail.getUserId());
            if(user == null){
                continue;
            }
            userVo.put("id", user.getId());
            userVo.put("avatar", user.getAvatar());
            userVo.put("nickName", user.getNickname());
            if(collageDetail.getPid()==0){
                userVo.put("master", 0);
                goodsId = collageDetail.getGoodsId();
            }else{
                userVo.put("master", 1);
            }
            userVoList.add(userVo);
        }

        LitemallGoods goods2 = goodsService.findById(goodsId);
        // 订单信息
        LitemallOrder order = orderService.findById(orderId);
        if (null == order) {
            return ResponseUtil.fail(403, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.fail(403, "不是当前用户的订单");
        }
        if(order.getAddTime().contains(".0")){
            order.setAddTime(order.getAddTime().substring(0,order.getAddTime().length()-2));
        }
        orderVo.put("createTime", order.getAddTime());

        orderVo.put("id", order.getId());
        orderVo.put("orderSn", order.getOrderSn());
        orderVo.put("addTime", LocalDate.now());
        orderVo.put("consignee", order.getConsignee());
        orderVo.put("mobile", order.getMobile());
        orderVo.put("address", order.getAddress());
        //orderVo.put("goodsPrice", order.getGoodsPrice());
        //orderVo.put("freightPrice", order.getFreightPrice());
        orderVo.put("actualPrice", order.getActualPrice());
        orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
        //orderVo.put("handleOption", OrderUtil.build(order));

        List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
        List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
        for (LitemallOrderGoods orderGoods : orderGoodsList) {
            Map<String, Object> orderGoodsVo = new HashMap<>();
            orderGoodsVo.put("id", orderGoods.getId());
            orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
            orderGoodsVo.put("number", orderGoods.getNumber());
            orderGoodsVo.put("goodsSpecificationValues", orderGoods.getGoodsSpecificationValues());
            orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
            //orderGoodsVo.put("retailPrice", orderGoods.getRetailPrice());
            LitemallGoods goods = goodsService.findById(orderGoods.getGoodsId());
            if(goods == null){
                continue;
            }
            orderGoodsVo.put("goodsBrief", goods.getGoodsBrief());
            orderGoodsVo.put("integretion", goods.getIntegretion());
            orderGoodsVo.put("price",goods.getCounterPrice());
            orderGoodsVo.put("person",goods.getPersonNum());
            orderGoodsVoList.add(orderGoodsVo);
        }

        if(goods2!=null){
            result.put("count", goods2.getPersonNum());
        }
        result.put("userVoList", userVoList);
        result.put("orderGoods", orderGoodsVoList);
        result.put("orderInfo", orderVo);
//        result.put("collageDetailList", collageDetailList);
        return ResponseUtil.ok(result);

    }

    /**
     * 提交订单
     * 1. 根据购物车ID、地址ID、优惠券ID，创建订单表项
     * 2. 购物车清空
     * 3. TODO 优惠券设置已用
     * 4. 商品货品数量减少
     *
     * @param userId 用户ID
     * @param body   订单信息，{ cartId：xxx, addressId: xxx, couponId: xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功', data: { orderId: xxx } }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("submit")
    public Object submit(Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        if (cartId == null || addressId == null) {
            return ResponseUtil.badArgument();
        }

        // 收货地址
        LitemallAddress checkedAddress = addressService.findById(addressId);

        // 获取可用的优惠券信息
        // 使用优惠券减免的金额
        BigDecimal couponPrice = new BigDecimal(0.00);

        // 货品价格
        List<LitemallCart> checkedGoodsList = null;
        if (cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        if (checkedGoodsList.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (LitemallCart checkGoods : checkedGoodsList) {
            checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getRetailPrice().multiply(new BigDecimal(checkGoods.getNumber())));
        }

        // 根据订单商品总价计算运费，满88则免运费，否则8元；
        BigDecimal freightPrice = new BigDecimal(0.00);
        if (checkedGoodsPrice.compareTo(new BigDecimal(88.00)) < 0) {
            freightPrice = new BigDecimal(8.00);
        }

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);


        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice);
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        // 订单
        LitemallOrder order = new LitemallOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setAddTime(DateUtils.formatTimestamp.format(new Date()));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee(checkedAddress.getName());
        order.setMobile(checkedAddress.getMobile());
        String detailedAddress = detailedAddress(checkedAddress);
        order.setAddress(detailedAddress);
        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(freightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);
        order.setOrder_type(0);//0积分单

        // 订单商品
        List<LitemallOrderGoods> orderGoodsList = new ArrayList<>(checkedGoodsList.size());
        for (LitemallCart cartGoods : checkedGoodsList) {
            LitemallOrderGoods orderGoods = new LitemallOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(cartGoods.getGoodsId());
            orderGoods.setGoodsSn(cartGoods.getGoodsSn());
            orderGoods.setProductId(cartGoods.getProductId());
            orderGoods.setGoodsName(cartGoods.getGoodsName());
            orderGoods.setPicUrl(cartGoods.getPicUrl());
            orderGoods.setRetailPrice(cartGoods.getRetailPrice());
            orderGoods.setNumber(cartGoods.getNumber());
            orderGoods.setGoodsSpecificationIds(cartGoods.getGoodsSpecificationIds());
            orderGoods.setGoodsSpecificationValues(cartGoods.getGoodsSpecificationValues());
            orderGoodsList.add(orderGoods);
        }

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // 添加订单表项
            orderService.add(order);

            // 添加订单商品表项
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                orderGoodsService.add(orderGoods);
            }

            // 删除购物车里面的商品信息
            cartService.clearGoods(userId);

            // 商品货品数量减少
            for (LitemallCart checkGoods : checkedGoodsList) {
                Integer productId = checkGoods.getProductId();
                LitemallProduct product = productService.findById(productId);

                Integer remainNumber = product.getGoodsNumber() - checkGoods.getNumber();
                if (remainNumber < 0) {
                    throw new RuntimeException("下单的商品货品数量大于库存量");
                }
                product.setGoodsNumber(remainNumber);
                productService.updateById(product);
            }
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
            return ResponseUtil.fail(403, "下单失败");
        }
        txManager.commit(status);

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.getId());
        return ResponseUtil.ok(data);
    }

    /**
     * 取消订单
     * 1. 检测当前订单是否能够取消
     * 2. 设置订单取消状态
     * 3. 商品货品数量增加
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isCancel()) {
            return ResponseUtil.fail(403, "订单不能取消");
        }

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // 设置订单已取消状态
            order.setOrderStatus(OrderUtil.STATUS_CANCEL);
            orderService.update(order);

            // 商品货品数量增加
            List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                Integer productId = orderGoods.getProductId();
                LitemallProduct product = productService.findById(productId);
                Integer number = product.getGoodsNumber() + orderGoods.getNumber();
                product.setGoodsNumber(number);
                productService.updateById(product);
            }
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
            return ResponseUtil.fail(403, "订单取消失败");
        }
        txManager.commit(status);

        return ResponseUtil.ok();
    }

    /**
     * 付款
     * 1. 检测当前订单是否能够付款
     * 2. 设置订单付款状态
     * 3. TODO 微信后台申请支付，同时设置付款状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("pay")
    public Object pay(Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否能够付款
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(403, "订单不能付款");
        }

        // 微信后台申请微信支付订单号
        String payId = "";
        // 微信支付订单号生产未支付
        Short payStatus = 1;

        order.setOrderStatus(OrderUtil.STATUS_PAY);
        order.setPayId(payId);
        order.setPayStatus(payStatus);
        orderService.update(order);

        return ResponseUtil.ok();
    }

    /**
     * 付款成功回调接口
     * 1. 检测当前订单是否是付款状态
     * 2. 设置订单付款成功状态相关信息
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx, payId: xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     * <p>
     * 注意，这里pay_notify是示例地址，开发者应该设立一个隐蔽的回调地址
     * TODO 这里需要根据微信支付文档设计
     */
    @PostMapping("pay_notify")
    public Object pay_notify(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        Integer payId = JacksonUtil.parseInteger(body, "payId");
        if (orderId == null || payId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测是否是付款状态
        if (!order.getOrderStatus().equals(OrderUtil.STATUS_PAY)) {
            logger.error("系统内部错误");
        }
        if (!order.getPayId().equals(payId)) {
            logger.error("系统内部错误");
        }

        Short payStatus = (short) 2;
        order.setPayStatus(payStatus);
        order.setPayTime(LocalDateTime.now());
        orderService.update(order);

        return ResponseUtil.ok();
    }


    /**
     * 退款取消订单
     * 1. 检测当前订单是否能够退款取消
     * 2. 设置订单退款取消状态
     * 3. TODO 退款
     * 4. 商品货品数量增加
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("refund")
    public Object refund(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isRefund()) {
            return ResponseUtil.fail(403, "订单不能取消");
        }

        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // 设置订单取消状态
            order.setOrderStatus(OrderUtil.STATUS_REFUND);
            orderService.update(order);

            // 退款操作

            // 商品货品数量增加
            List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                Integer productId = orderGoods.getProductId();
                LitemallProduct product = productService.findById(productId);
                Integer number = product.getGoodsNumber() + orderGoods.getNumber();
                product.setGoodsNumber(number);
                productService.updateById(product);
            }
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
            return ResponseUtil.fail(403, "订单退款失败");
        }
        txManager.commit(status);

        return ResponseUtil.ok();
    }

    /**
     * 发货
     * 1. 检测当前订单是否能够发货
     * 2. 设置订单发货状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("ship")
    public Object ship(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        String shipSn = JacksonUtil.parseString(body, "shipSn");
        String shipChannel = JacksonUtil.parseString(body, "shipChannel");
        if (orderId == null || shipSn == null || shipChannel == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 如果订单不是已付款状态，则不能发货
        if (!order.getOrderStatus().equals(OrderUtil.STATUS_PAY)) {
            return ResponseUtil.fail(403, "订单不能确认收货");
        }

        order.setOrderStatus(OrderUtil.STATUS_SHIP);
        order.setShipSn(shipSn);
        order.setShipChannel(shipChannel);
        order.setShipStartTime(LocalDateTime.now());
        orderService.update(order);

        return ResponseUtil.ok();
    }

    /**
     * 确认收货
     * 1. 检测当前订单是否能够确认订单
     * 2. 设置订单确认状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("confirm")
    public Object confirm(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isConfirm()) {
            return ResponseUtil.fail(403, "订单不能确认收货");
        }

        order.setOrderStatus(OrderUtil.STATUS_CONFIRM);
        order.setConfirmTime(LocalDateTime.now());
        orderService.update(order);

        return ResponseUtil.ok();
    }


    /**
     * 自动确认收货
     * 1. 检测当前订单是否能够自动确认订单
     * 2. 设置订单自动确认状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("autoconfirm")
    public Object autoconfirm(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isConfirm()) {
            return ResponseUtil.fail(403, "订单不能确认收货");
        }

        order.setOrderStatus(OrderUtil.STATUS_AUTO_CONFIRM);
        order.setConfirmTime(LocalDateTime.now());
        orderService.update(order);

        return ResponseUtil.ok();
    }

    /**
     * 删除订单
     * 1. 检测当前订单是否删除
     * 2. 设置订单删除状态
     *
     * @param userId 用户ID
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgument();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isDelete()) {
            return ResponseUtil.fail(403, "订单不能删除");
        }

        // 订单order_status没有字段用于标识删除
        // 而是存在专门的delete字段表示是否删除
        orderService.deleteById(orderId);

        return ResponseUtil.ok();
    }

    /**
     * 可以评价的订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 订单操作结果
     * 成功则 { errno: 0, errmsg: '成功', data: xxx }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("comment")
    public Object comment(@LoginUser Integer userId, Integer orderId, Integer goodsId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        List<LitemallOrderGoods> orderGoodsList = orderGoodsService.findByOidAndGid(orderId, goodsId);
        int size = orderGoodsList.size();

        Assert.state(size < 2, "存在多个符合条件的订单商品");

        if (size == 0) {
            return ResponseUtil.badArgumentValue();
        }

        LitemallOrderGoods orderGoods = orderGoodsList.get(0);
        return ResponseUtil.ok(orderGoods);
    }


    @PostMapping("collageSubmit")
    public Object collageSubmit(@RequestBody String body) {
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer cartId = JacksonUtil.parseInteger(body, "cartId");
        Integer addressId = JacksonUtil.parseInteger(body, "addressId");
        Integer pid=JacksonUtil.parseInteger(body, "pid");//团长为空团员为pid
        Integer userId = JacksonUtil.parseInteger(body, "userId");

        if (cartId == null || addressId == null) {
            return ResponseUtil.badArgument();
        }

        // 收货地址
        LitemallAddress checkedAddress = addressService.findById(addressId);
        //用户信息
        LitemallUser user=litemallUserService.findById(userId);

        // 获取可用的优惠券信息
        // 使用优惠券减免的金额
        //BigDecimal couponPrice = new BigDecimal(0.00);

        // 货品价格
        BigDecimal actualPrice=null;
        List<LitemallCart> checkedGoodsList = null;
        if (cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
            actualPrice=cart.getRetailPrice();
        }
        if (checkedGoodsList.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        /*BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (LitemallCart checkGoods : checkedGoodsList) {
            checkedGoodsPrice = checkedGoodsPrice.add(checkGoods.getRetailPrice().multiply(new BigDecimal(checkGoods.getNumber())));
        }

        // 根据订单商品总价计算运费，满88则免运费，否则8元；
        BigDecimal freightPrice = new BigDecimal(0.00);
        if (checkedGoodsPrice.compareTo(new BigDecimal(88.00)) < 0) {
            freightPrice = new BigDecimal(8.00);
        }

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);


        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice);
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);*/

        // 订单
        LitemallOrder order = new LitemallOrder();
        order.setUserId(userId);
        order.setOrderSn(orderService.generateOrderSn(userId));
        order.setAddTime(DateUtils.formatTimestamp.format(new Date()));
        order.setOrderStatus(OrderUtil.WAIT_SHARE);
        if(checkedAddress!=null){
            order.setConsignee(checkedAddress.getName());
            order.setMobile(checkedAddress.getMobile());
            //String detailedAddress = detailedAddress(checkedAddress);
            String detailedAddress =checkedAddress.getAddress();
            order.setAddress(detailedAddress);
        }
        /*order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(freightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);*/
        order.setActualPrice(actualPrice);
        order.setOrder_type(1);//拼团单
        //拼团流水
        CollageDetail detail=new CollageDetail();
        // 订单商品
        List<LitemallOrderGoods> orderGoodsList = new ArrayList<>(checkedGoodsList.size());
        for (LitemallCart cartGoods : checkedGoodsList) {
            LitemallOrderGoods orderGoods = new LitemallOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(cartGoods.getGoodsId());
            orderGoods.setGoodsSn(cartGoods.getGoodsSn());
            orderGoods.setProductId(cartGoods.getProductId());
            orderGoods.setGoodsName(cartGoods.getGoodsName());
            orderGoods.setPicUrl(cartGoods.getPicUrl());
            orderGoods.setRetailPrice(cartGoods.getRetailPrice());
            orderGoods.setNumber(cartGoods.getNumber());
            orderGoods.setGoodsSpecificationIds(cartGoods.getGoodsSpecificationIds());
            orderGoods.setGoodsSpecificationValues(cartGoods.getGoodsSpecificationValues());
            orderGoodsList.add(orderGoods);

            detail.setGoodsId(cartGoods.getGoodsId());
            detail.setUserId(userId);
            if(user!=null)
                detail.setAvatar(user.getAvatar());
            detail.setStatus(0);
            detail.setJoinDate(new Date());
            if(!StringUtils.isEmpty(pid)){
                detail.setPid(pid);
                detail.setFlag(0);//拼团团员标识
            }else
                detail.setFlag(1);//拼团团长标识
        }

        //订单提交积分扣除
        IntegretionDetail integretion=new IntegretionDetail();
        integretion.setUserId(String.valueOf(userId));
        integretion.setAmount(-actualPrice.intValue());
        integretion.setStatus((byte) 0);
        integretion.setType((byte) 20);//20代表扣除否则都为添加积分


        // 开启事务管理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // 添加订单表项
            orderService.add(order);

            // 添加订单商品表项
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                orderGoods.setOrderId(order.getId());
                orderGoodsService.add(orderGoods);
            }

            // 删除购物车里面的商品信息
            cartService.clearGoods(userId);

            // 商品货品数量减少
            for (LitemallCart checkGoods : checkedGoodsList) {
                Integer specificationIds = checkGoods.getGoodsSpecificationIds();
                //LitemallProduct product = productService.findById(productId);
                LitemallGoodsSpecification specification=specificationService.findById(specificationIds);
                Integer remainNumber = specification.getGoodsNumber() - checkGoods.getNumber();
                if (remainNumber < 0) {
                    throw new RuntimeException("下单的商品货品数量大于库存量");
                }
                specification.setGoodsNumber(remainNumber);
                specificationService.updateById(specification);
            }

            //保存拼团流水表
            detail.setOrderId(order.getId());
            collageDetailService.add(detail);

            //保存积分拼团扣除记录
            integretionDetailService.add(integretion);
        } catch (Exception ex) {
            txManager.rollback(status);
            logger.error("系统内部错误", ex);
            return ResponseUtil.fail(403, "下单失败");
        }
        txManager.commit(status);

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.getId());
        return ResponseUtil.ok(data);
    }
}