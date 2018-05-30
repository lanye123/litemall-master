package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesTemp;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
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
                praiseCommentService.countComment(null,userId,null));
        //增加返回该用户未读通知数 2018-5-30 15:00
        data.put("notesCount",notesService.countSeletive(null,null,null,userId,null,"0",null,null,"",""));
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
        Medal before = medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(medalDetails.getUserId(),null,null));
        medalDetailsService.add(medalDetails);
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
            notes.setContent(notesTemp.getContent());
            notes.setNo(notesTemp.getNo());
//            notes.setInfoid(reply.getCommentId());
            notesService.add(notes);
            return ResponseUtil.ok(notes);
        }
        return ResponseUtil.ok(medalDetails);
    }

}
