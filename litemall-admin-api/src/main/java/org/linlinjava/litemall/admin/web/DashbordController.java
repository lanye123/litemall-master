package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class DashbordController {
    private final Log logger = LogFactory.getLog(DashbordController.class);

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallProductService productService;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WxFormidService wxFormidService;

    @GetMapping("")
    public Object info(@LoginAdmin Integer adminId){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        int userTotal = userService.count();
        int articleTotal = articleService.countSelective("","",null,null,"","",1,null,null,"","");
        int customArticleTotal = articleService.countSelective("","",null,1,"","",1,null,null,"","");
        int formIdTotal = wxFormidService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("articleTotal", articleTotal);
        data.put("customArticleTotal", customArticleTotal);
        data.put("formIdTotal", formIdTotal);

        return ResponseUtil.ok(data);
    }

}
