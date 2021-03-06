package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallCart;
import org.linlinjava.litemall.db.service.LitemallCartService;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.service.LitemallProductService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/cart")
public class CartController {
    private final Log logger = LogFactory.getLog(CartController.class);

    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallProductService productService;

    @GetMapping("/list")
    public Object list(
                       Integer userId, Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        List<LitemallCart> cartList = cartService.querySelective(userId, goodsId, page, limit, sort, order);
        int total = cartService.countSelective(userId, goodsId, page, limit, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", cartList);

        return ResponseUtil.ok(data);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/create")
    public Object create( @RequestBody LitemallCart cart){

        return ResponseUtil.fail501();
    }

    @GetMapping("/read")
    public Object read( Integer id){

        LitemallCart cart = cartService.findById(id);
        return ResponseUtil.ok(cart);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/update")
    public Object update( @RequestBody LitemallCart cart){
        return ResponseUtil.fail501();
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallCart cart){
        cartService.deleteById(cart.getId());
        return ResponseUtil.ok();
    }

}
