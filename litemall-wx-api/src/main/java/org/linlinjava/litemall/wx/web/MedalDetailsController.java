package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallUser;
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
    private Log log = LogFactory.getLog(MedalDetailsController.class);
    /**
     *@Author:lanye
     *@Description:获取总榜接口
     *@Date:16:44 2018/5/8
     */
    @GetMapping("totalList")
    public Object getTotalList(Integer userId,@RequestParam(value = "page", defaultValue = "1")Integer page,@RequestParam(value = "limit", defaultValue = "20")Integer limit){
        if(userId == null){
            return ResponseUtil.badArgument();
        }
        LitemallUser user = litemallUserService.findById(userId);
        if(user == null){
            return ResponseUtil.badArgumentValue();
        }
        Map<String,Object> data = new HashMap<>();
        data.put("userId",userId);
        //排行榜所有人
        List<MedalDetails> medalDetailsList = medalDetailsService.list(null,"","",page,limit);
        List<Map<String,Object>> returnTotalList = this.returnList(medalDetailsList);
        //自己成长值情况
        this.getMedal(data);
        data.put("nickName",user.getNickname());
        data.put("avatar",user.getAvatar());
        data.put("returnTotalList",returnTotalList);
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 去除重复用户
      * @Date 2018/5/31 11:07
      * @Param [medalDetailsList]
      * @return java.util.ArrayList<org.linlinjava.litemall.db.domain.MedalDetails>
      **/
    private static ArrayList<MedalDetails> removeDuplicateUser(List<MedalDetails> medalDetailsList) {
        Set<MedalDetails> set = new TreeSet<MedalDetails>(new Comparator<MedalDetails>() {
            @Override
            public int compare(MedalDetails o1, MedalDetails o2) {
                //字符串,则按照asicc码升序排列
                if(o1!=null && o2!=null){
                    return o1.getUserId().compareTo(o2.getUserId());
                }
                return 0;
            }
        });
        set.addAll(medalDetailsList);
        return new ArrayList<MedalDetails>(set);
    }

    /**
     *@Author:lanye
     *@Description:获取周榜接口
     *@Date:10:30 2018/5/9
     */
    @GetMapping("weekList")
    public Object getWeekList(Integer userId,@RequestParam(value = "page", defaultValue = "1")Integer page,@RequestParam(value = "limit", defaultValue = "20")Integer limit){
        if(userId == null){
            return ResponseUtil.badArgument();
        }
        LitemallUser user = litemallUserService.findById(userId);
        if(user == null){
            return ResponseUtil.badArgumentValue();
        }
        Map<String,Object> data = new HashMap<>();
        data.put("userId",userId);
        //拼装周榜时间参数
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = DateUtils.getCurrentMondayDate();
        String time1 = dateFormat.format(date);
        date = DateUtils.getPreviousSundayDate();
        String time2 = dateFormat.format(date);
        //排行榜所有人
        List<MedalDetails> medalDetailsList = medalDetailsService.list(null,time1,time2,page,limit);
        List<Map<String,Object>> returnTotalList = this.returnList(medalDetailsList);
        //用户总共成长值情况
        this.getMedal(data);
        data.put("nickName",user.getNickname());
        data.put("avatar",user.getAvatar());
        data.put("returnTotalList",returnTotalList);
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 封装排行榜数据
      * @Date 2018/6/23 17:42
      * @Param [returnTotalList, medalDetailsList]
      * @return void
      **/
    private List<Map<String,Object>> returnList(List<MedalDetails> medalDetailsList) {
        if(medalDetailsList==null || medalDetailsList.size()<=0){
            return null;
        }
        List<Map<String,Object>> returnTotalList = new ArrayList<>();
        Medal medal;
        Map<String, Object> dataItem;
        for(MedalDetails medalDetails:medalDetailsList){
            medal = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));
            dataItem = new HashMap<>();
            dataItem.put("score",medalDetails.getAmount());
            dataItem.put("rank",medalDetails.getRank());
            dataItem.put("userId",medalDetails.getUserId());
            dataItem.put("nickName",medalDetails.getNickName());
            dataItem.put("avatar",medalDetails.getAvatar());
            dataItem.put("medalName",medal.getName());
            dataItem.put("imgUrl",medal.getImgUrl());
            dataItem.put("comment",medal.getComment());
            dataItem.put("max",medal.getMax());
            dataItem.put("min",medal.getMin());
            returnTotalList.add(dataItem);
        }
        return returnTotalList;
    }

    /**
      * @author lanye
      * @Description 获取当前用户得成长值
      * @Date 2018/6/23 17:35
      * @Param [userId]
      * @return java.util.Map<java.lang.String,java.lang.Object>
      **/
    private void getMedal(Map<String,Object> data){
        List<MedalDetails> medalDetailsList2 = medalDetailsService.list(null,"","",null,null);
        Medal medal;
        for(MedalDetails medalDetails:medalDetailsList2){
            if(medalDetails.getUserId()==data.get("userId")){
                medal = medalDetailsService.getMedalByScore(medalDetails.getAmount());
                data.put("rank",medalDetails.getRank());
                data.put("score",medalDetails.getAmount());
                data.put("medalName",medal.getName());
            }
        }
        if(data.get("rank")==null){
            medal = medalDetailsService.getMedalByScore(0);
            data.put("rank",0);
            data.put("score",0);
            data.put("medalName",medal.getName());
        }
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



