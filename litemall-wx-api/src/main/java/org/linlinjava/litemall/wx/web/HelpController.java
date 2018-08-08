package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/wx/help")
public class HelpController {
    private final Log logger = LogFactory.getLog(HelpController.class);

    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private HelpDetailService helpDetailService;
    @Autowired
    private HelpOrderService helpOrderService;
    @Autowired
    private LitemallGoodsSpecificationService specificationService;
    @Autowired
    private LitemallUserService litemallUserService;
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

    /**
     * @Author leiqiang
     * @Description //TODO 商品详情订单创建接口
     * @Date   2018/8/7 11:21
     * @Param  [order]
     * @return java.lang.Object
     **/
    @PostMapping("addOrder")
    public Object addOrder(@RequestBody HelpOrder order){
        Map data=new HashMap();
        Integer userId=order.getUserId();
        Integer goodsId=order.getGoodsId();
        Integer orderId=null;
        LitemallUser user=litemallUserService.findById(userId);
        data.put("nickname",user.getNickname());
        data.put("avatar",user.getAvatar());
        HelpOrder ho=helpOrderService.queryById(userId,goodsId);
        if(ho==null){
            //创建订单
            order.setSno(this.radomOrderSno());
            helpOrderService.addOrder(order);
            data.put("orderSno",order.getSno());
            data.put("orderId",order.getId());
            orderId=order.getId();
            //创建从表记录
            HelpDetail detail=new HelpDetail();
            detail.setUserId(userId);
            detail.setGoodsId(goodsId);
            detail.setOrderSno(order.getSno());
            detail.setOrderId(order.getId());
            detail.setAvatar(user.getAvatar());
            detail.setPid(order.getId());
            detail.setPid(1);//团长
            helpDetailService.create(detail);
        }else{
            data.put("orderSno",ho.getSno());
            data.put("orderId",ho.getId());
            orderId=ho.getId();
        }

        //返回商品信息
        LitemallGoods goods=goodsService.findById(goodsId);
        if(goods!=null){
            data.put("picUrl",goods.getActPicUrl());
            data.put("goodsName",goods.getName());
            data.put("memo",goods.getMemo());
            data.put("endDate",goods.getEndDate());
            data.put("personNum",goods.getPersonNum());
            data.put("price",goods.getCounterPrice());
        }
        LitemallGoodsSpecification specification=specificationService.findByGoodsId(goodsId);
        if(specification!=null){
            data.put("fakeNumber",specification.getFakeNumber());
            data.put("goodsNumber",specification.getGoodsNumber());
        }
        List<HelpDetail> detailList=helpDetailService.list(orderId);
        data.put("requiredNum",goods.getPersonNum()-detailList.size());
        ArrayList array=new ArrayList();
        for (HelpDetail hd2:detailList){
            array.add(hd2.getAvatar());
        }
        data.put("list",array);
        //返回detail详情
        return ResponseUtil.ok(data);
    }

    /**
     * @Author leiqiang
     * @Description //TODO 用户通过二维码或者分享链接进入小程序后确定授权助力成功创建接口
     * @Date   2018/8/7 11:23
     * @Param  [detail]
     * @return java.lang.Object
     **/
    @PostMapping("addDetail")
    public Object addDetail(@RequestBody HelpDetail helpDetail){
        Map data=new HashMap();
        Integer userId=helpDetail.getUserId();
        Integer orderId=helpDetail.getOrderId();
        HelpOrder order=helpOrderService.load(orderId);
        LitemallGoods goods=goodsService.findById(order.getGoodsId());
        LitemallUser user=litemallUserService.findById(userId);
        //创建从表记录
        HelpDetail hd=helpDetailService.validate(userId,orderId);
        if(hd==null){
            HelpDetail detail=new HelpDetail();
            detail.setUserId(userId);
            detail.setGoodsId(order.getGoodsId());
            detail.setOrderSno(order.getSno());
            detail.setOrderId(orderId);
            detail.setAvatar(user.getAvatar());
            detail.setPid(orderId);
            detail.setPid(0);//助力团员
            helpDetailService.create(detail);
        }

        data.put("orderSno",order.getSno());
        data.put("orderId",orderId);
        if(goods!=null){
            data.put("picUrl",goods.getActPicUrl());
            data.put("goodsName",goods.getName());
            data.put("memo",goods.getMemo());
            data.put("endDate",goods.getEndDate());
            data.put("personNum",goods.getPersonNum());
            data.put("price",goods.getCounterPrice());
        }
        LitemallGoodsSpecification specification=specificationService.findByGoodsId(order.getGoodsId());
        if(specification!=null){
            data.put("fakeNumber",specification.getFakeNumber());
            data.put("goodsNumber",specification.getGoodsNumber());
        }
        List<HelpDetail> detailList=helpDetailService.list(orderId);
        data.put("requiredNum",goods.getPersonNum()-detailList.size());
        ArrayList array=new ArrayList();
        for (HelpDetail hd2:detailList){
            array.add(hd2.getAvatar());
        }
        data.put("list",array);
        return ResponseUtil.ok(data);
    }

    @GetMapping("helpList")
    public Object helpList(@RequestParam Integer orderId){
        Map data=new HashMap();
        HelpOrder order=helpOrderService.load(orderId);
        LitemallGoods goods=goodsService.findById(order.getGoodsId());
        data.put("orderSno",order.getSno());
        data.put("orderId",orderId);
        if(goods!=null){
            data.put("picUrl",goods.getActPicUrl());
            data.put("goodsName",goods.getName());
            data.put("memo",goods.getMemo());
            data.put("endDate",goods.getEndDate());
            data.put("personNum",goods.getPersonNum());
            data.put("price",goods.getCounterPrice());
        }
        LitemallGoodsSpecification specification=specificationService.findByGoodsId(order.getGoodsId());
        if(specification!=null){
            data.put("fakeNumber",specification.getFakeNumber());
            data.put("goodsNumber",specification.getGoodsNumber());
        }
        List<HelpDetail> detailList=helpDetailService.list(orderId);
        data.put("requiredNum",goods.getPersonNum()-detailList.size());
        ArrayList array=new ArrayList();
        for (HelpDetail hd2:detailList){
            array.add(hd2.getAvatar());
        }
        data.put("list",array);
        return ResponseUtil.ok(data);
    }

    /**
     * 日期/字符串/长整型数字间的互转
     */
    //将长整型数字转换为日期格式的字符串
    public String parseString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    public String radomOrderSno(){
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(this.parseString(System.currentTimeMillis(), "yyyyMMdd"));
        strbuf.append((int)((Math.random()*9+1)*100000));
        return strbuf.toString();
    }

}