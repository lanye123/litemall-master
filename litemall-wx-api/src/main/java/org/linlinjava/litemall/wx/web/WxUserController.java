package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx/user")
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallUserService litemallUserService;

    @PostMapping("/create")
    public Object create(@LoginUser Integer userId, @RequestBody LitemallUser user){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        logger.debug(user);

        litemallUserService.add(user);
        return ResponseUtil.ok(user);
    }

}