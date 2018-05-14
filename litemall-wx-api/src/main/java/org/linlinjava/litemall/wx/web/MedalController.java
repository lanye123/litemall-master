package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wx/medal")
public class MedalController {
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private MedalService medalService;
    /**
     *@Author:lanye
     *@Description:获取用户勋章等级接口
     *@Date:16:44 2018/5/7
     */
    @GetMapping("medal")
    public Object getMedal(@RequestParam Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }

        Map<String,Object> data = new HashMap<>();
        List<Medal> medals = medalService.getMedal(null);
        Medal medalDb = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(userId,null,null));
        for(Medal medal:medals){
            if(medal.getId().equals(medalDb.getId())){
                medal.setStatus((byte)9);
                medal.setImgUrl(medal.getImgUrl2());
            }
            //该用户所属勋章等级之前的勋章也需点亮
            if(medal.getMin()<=medalDetailsService.getScoreByUserId(userId,null,null)){
                //前端要求只传一个有效图片地址
                medal.setStatus((byte)9);
                medal.setImgUrl(medal.getImgUrl2());

            }
        }
        data.put("medals",medals);
        data.put("score",medalDetailsService.getScoreByUserId(userId,null,null));
        data.put("comment",medalDb.getComment());
        data.put("img_url3",medalDb.getImgUrl3());
        data.put("img_url2",medalDb.getImgUrl2());
        data.put("name",medalDb.getName());
        data.put("imgName",medalDb.getImgName());

        return ResponseUtil.ok(data);
    }

    /**
     *@Author:lanye
     *@Description:记录用户成长值接口
     *@Date:16:20 2018/5/7
     */
    @PostMapping("add")
    public Object add(@RequestParam Integer userId, @RequestBody MedalDetails medalDetails){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        medalDetailsService.add(medalDetails);

        return ResponseUtil.ok(medalDetails);
    }

}
