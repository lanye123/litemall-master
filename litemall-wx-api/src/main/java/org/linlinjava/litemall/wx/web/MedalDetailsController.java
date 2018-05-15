package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public Object getTotalList(@RequestParam Integer userId){
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
        sort(medalDetailsList);
        Integer userIdDb = null;
        for(MedalDetails medalDetails:medalDetailsList){
            if(userIdDb == medalDetails.getUserId()){
                continue;
            }
            userIdDb = medalDetails.getUserId();
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

    /**
     *@Author:lanye
     *@Description:获取周榜接口
     *@Date:10:30 2018/5/9
     */
    @GetMapping("weekList")
    public Object getWeekList(@RequestParam Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        //拼装时间参数
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = DateUtils.getCurrentMondayDate();
        String time1 = dateFormat.format(date);
        date = DateUtils.getPreviousSundayDate();
        String time2 = dateFormat.format(date);

        Map<String,Object> data = new HashMap<>();
        Map<String,Object> dataItem;
        List<Map<String,Object>> returnTotalList = new ArrayList<>();
        Medal medal;
        List<MedalDetails> medalDetailsList = medalDetailsService.selectList(null,null,null,null,null,null);
        for(MedalDetails medalDetails:medalDetailsList){
            medalDetails.setAmount(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),time1,time2));
        }
        //对用户成长值进行排序
        sort(medalDetailsList);
        Integer userIdDb = null;
        for(MedalDetails medalDetails:medalDetailsList){
            //剔除重复用户
            if(userIdDb == medalDetails.getUserId()){
                continue;
            }
            userIdDb = medalDetails.getUserId();
            dataItem = new HashMap<>();
            medal = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),time1,time2));

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

    /**
      * @author lanye
      * @Description 对用户成长值进行排序
      * @Date 2018/5/9 11:42
      * @Param [medalDetailsList]
      * @return java.util.List<org.linlinjava.litemall.db.domain.MedalDetails>
      **/
    private List<MedalDetails> sort(List<MedalDetails> medalDetailsList){
        Collections.sort(medalDetailsList, (s1, s2) ->{
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            return s2.getAmount()-s1.getAmount();
        });
        return medalDetailsList;
    }

}



