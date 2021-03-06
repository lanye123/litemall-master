package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wx/medal")
public class MedalController {
    private final Log logger = LogFactory.getLog(MedalController.class);
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private MedalService medalService;
    @Autowired
    private NotesTempService notesTempService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private PraiseService praiseService;
    @Autowired
    private PraiseCommentService praiseCommentService;
    @Autowired
    private IntegretionDetailService integretionDetailService;
    @Autowired
    private ArticleCollectionService articleCollectionService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WxMenuService wxMenuService;

    /**
     *@Author:lanye
     *@Description:获取用户勋章等级接口
     *@Date:16:44 2018/5/7
     */
    @GetMapping("medal")
    public Object getMedal(@RequestParam Integer userId) throws ParseException {
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
            medal.setIsView((byte)0);
            if(integretionDetailService.countSeletive(userId+"",medal.getId())>0){
                //isView为9表示用户已经获得过该勋章积分
                medal.setIsView((byte)9);
            }
        }
        data.put("medals",medals);
        data.put("score",medalDetailsService.getScoreByUserId(userId,null,null));
        data.put("comment",medalDb.getComment());
        data.put("img_url3",medalDb.getImgUrl3());
        data.put("img_url2",medalDb.getImgUrl2());
        data.put("name",medalDb.getName());
        data.put("imgName",medalDb.getImgName());

        //增加返回该用户点亮文章数 2018-5-28 14:30
        data.put("shineCount",medalDetailsService.countSeletive(null,null,userId,null,null,null,null,"",""));
        //增加返回该用户获得赞数 2018-5-30 10:34
        data.put("praiseCount",praiseService.countSeletive(null,null,userId,null,null,null,"","")+
                praiseCommentService.countComment(null,null,userId,null));
        //增加返回该用户未读通知数 2018-5-30 15:00
        data.put("notesCount",notesService.countSeletive(null,null,null,userId,null,"0",null,null,"",""));
        //增加返回该用户收藏数 2018-6-6 15:45
        data.put("collectionCount",articleCollectionService.countSeletive(null,userId,1,null,null,"",""));
        //增加返回该用户制作图文数 2018-6-6 15:45
        data.put("createCount",articleService.querySelective3(null,null,null,userId,"").size());

        //是否打卡状态
        List<IntegretionDetail> detailList=integretionDetailService.queryByLimit(String.valueOf(userId));
        if(detailList.size()>0){
            IntegretionDetail detail=integretionDetailService.queryByLimit(String.valueOf(userId)).get(0);
            if(DateUtils.compareDate(DateUtils.localToDate(detail.getCreateDate()),DateUtils.getCurrentDate(),5)==0)
                data.put("dkStatus",1);
            else
                data.put("dkStatus",0);
        }else
            data.put("dkStatus",0);

        //获取"我的"模块中菜单信息by leiq 2018-8-10 10:45:54
        List<WxMenu> menuList=wxMenuService.list();
        if(menuList.size()>0)
        data.put("menuList",menuList);

        return ResponseUtil.ok(data);
    }

    /**
     *@Author:lanye
     *@Description:记录用户成长值接口
     *@Date:16:20 2018/5/7
     */
    @PostMapping("add")
    public Object add(@RequestBody MedalDetails medalDetails){
        if(medalDetails == null){
            return ResponseUtil.badArgument();
        }
        if(medalDetails.getUserId() == null){
            return ResponseUtil.unlogin();
        }
        if(medalDetails.getArticleId() == null){
            return ResponseUtil.badArgument();
        }
        if(medalDetails.getAmount() == null){
            return ResponseUtil.badArgument();
        }
        Map<String,Object> data = new HashMap<>();
        Medal before = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));
        String remsg = medalDetailsService.add(medalDetails);
        data.put("msg",remsg);
        Medal after = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));
        if(!before.getId().equals(after.getId())){
            //发送通知
            List<NotesTemp> notesTemps = notesTempService.querySelective("upgrade","",null,"",null,null,"","");
            if(notesTemps==null || notesTemps.size()==0){
                return ResponseUtil.ok(medalDetails);
            }
            NotesTemp notesTemp = notesTemps.get(0);
            Notes notes = new Notes();
            //notes.setUserId(reply.getToUserid());
            notes.setFromUserid(medalDetails.getUserId());
            notes.setTempId(notesTemp.getId());
            notes.setType(notesTemp.getType());
            notes.setContent(notesTemp.getContent().replace("SNAME",before.getName()).replace("ENAME",after.getName()));
            notes.setNo(notesTemp.getNo());
            notes.setInfoid(before.getId());
            if(notesService.countSeletive(notes.getTempId(),notes.getType(),null,notes.getFromUserid(),notes.getInfoid(),"",null,null,"","")>0){
                ResponseUtil.ok(data);
            }
            notesService.add(notes);
            return ResponseUtil.ok(data);
        }
        return ResponseUtil.ok(data);
    }

}
