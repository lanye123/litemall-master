package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wx/medal")
public class MedalController {
    @Autowired
    private MedalDetailsService medalDetailsService;
    /**
     *@Author:lanye
     *@Description:获取用户勋章等级接口
     *@Date:16:44 2018/5/7
     */
    @GetMapping("medal")
    public Object getMedal(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        return ResponseUtil.ok(medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(userId,null,null)));
    }

    /**
     *@Author:lanye
     *@Description:记录用户成长值接口
     *@Date:16:20 2018/5/7
     */
    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody MedalDetails medalDetails){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        medalDetailsService.add(medalDetails);

        return ResponseUtil.ok(medalDetails);
    }

}
