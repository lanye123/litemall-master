package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    public List<MedalDetails> sort(List<MedalDetails> medalDetailsList){
        Collections.sort(medalDetailsList, (s1, s2) ->{
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            return s2.getAmount()-s1.getAmount();
        });
        return medalDetailsList;
    }

    /**
     * @author leiqiang
     * 文章点赞接口
     */

    @PostMapping("create")
    public Object create(@RequestBody MedalDetails details){
       //统计文章点亮的数量
        Integer mds1=medalDetailsService.countSeletive(0,details.getArticleId(),details.getUserId(),null,null,null,null,"","");
        Integer mds2=medalDetailsService.countSeletive(details.getNotesId(),details.getArticleId(),details.getUserId(),null,null,null,null,"","");
        //加成长值
            MedalDetails medalDetails = new MedalDetails();
            medalDetails.setUserId(details.getUserId());
            medalDetails.setArticleId(details.getArticleId());
            medalDetails.setAmount(10);
            //当章节ID为空的时候则点亮文章
            if(details.getNotesId()==0){
                medalDetails.setNotesId(0);
            }else{
                //当章节ID不为空的时候则点亮章节
                medalDetails.setNotesId(details.getNotesId());
            }
            if(mds1==0||mds2==0)
            medalDetailsService.add(medalDetails);
        return ResponseUtil.ok();
    }

}



