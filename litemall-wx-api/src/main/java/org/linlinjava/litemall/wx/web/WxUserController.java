package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;

    @PostMapping("/create")
    public Object create(@RequestBody LitemallUser user){
        logger.debug(user);

        if(litemallUserService.countSeletive("","",user.getWeixinOpenid(),null,null,"","")>0){
            return ResponseUtil.ok(litemallUserService.querySelective("","",user.getWeixinOpenid(),null,null,"","").get(0));
        }

        litemallUserService.add(user);
        return ResponseUtil.ok(user);
    }

}