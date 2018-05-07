package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/medal")
public class MedalController {
    @Autowired
    private MedalService medalService;
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
        int score = 0;
        List<MedalDetails> medalDetailsList = medalDetailsService.selectList(userId,null,null,null);
        for(MedalDetails medalDetails:medalDetailsList){
            score+=medalDetails.getAmount();
        }
        List<Medal> medals = medalService.getMedal(null);
        int min;
        for(int i = medals.size();i>0;i--){
            if(medals.get(i-1).getMin() == null){
                min = 0;
            }else{
                min = medals.get(i-1).getMin();
            }
            if(score>=min){
                return ResponseUtil.ok(medals.get(i-1));
            }
        }

        return ResponseUtil.ok(medals.get(0));
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
