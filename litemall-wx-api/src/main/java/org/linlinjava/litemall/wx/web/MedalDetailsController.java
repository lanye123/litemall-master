package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/wx/medalDetails")
public class MedalDetailsController {

    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private LitemallUserService litemallUserService;
    /**
     *@Author:lanye
     *@Description:获取总榜接口
     *@Date:16:44 2018/5/8
     */
    @GetMapping("totalList")
    public Object getTotalList(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        Map<String,Object> data = new HashMap<>();
        Map<String,Object> dataItem;
        List<Map<String,Object>> returnTotalList = new ArrayList<>();
        Medal medal;
        List<MedalDetails> medalDetailsList = medalDetailsService.selectList(null,null,null,null,null,null);
        for(MedalDetails medalDetails:medalDetailsList){
            medalDetails.setAmount(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));
        }
        //对用户成长值进行排序
        Collections.sort(medalDetailsList, (s1, s2) ->{// 省略参数表的类型
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            return s2.getAmount()-s1.getAmount();
        });
        for(MedalDetails medalDetails:medalDetailsList){
            dataItem = new HashMap<>();
            medal = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));

            dataItem.put("score",medalDetails.getAmount());
            dataItem.put("userName",litemallUserService.findById(medalDetails.getUserId()).getUsername());
            dataItem.put("medalName",medal.getName());
            dataItem.put("imgUrl",medal.getImgUrl());
            dataItem.put("comment",medal.getComment());
            dataItem.put("max",medal.getMax());
            dataItem.put("min",medal.getMin());

            returnTotalList.add(dataItem);
        }

        data.put("returnTotalList",returnTotalList);
        return ResponseUtil.ok(data);
    }

}



