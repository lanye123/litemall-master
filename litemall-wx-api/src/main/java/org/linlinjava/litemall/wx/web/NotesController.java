package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/notice")
/**
  * @author lanye
  * @Description TODO
  * @Date 2018/5/29 17:25
  * @Param
  * @return
  **/
public class NotesController {
    @Autowired
    private ArticleReplyService articleReplyService;
    @Autowired
    private NotesTempService notesTempService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private LitemallUserService litemallUserService;

    /**
      * @author lanye
      * @Description 评论回复通知列表
      * @Date 2018/5/29 17:36
      * @Param [userId]
      * @return java.lang.Object
      **/
    @GetMapping("replyList")
    public Object replyList(Integer userId){
        if (userId==null){
            return ResponseUtil.badArgument();
        }
        List<Notes> notesList = notesService.querySelective(null,0,null,userId,null,"0",null,null,"","");
        if(notesList==null || notesList.size()<=0){
            return ResponseUtil.ok();
        }
        //最终返回data
        Map<String,Object> data = new HashMap<>();
        //封装的对象
        List<Map<String,Object>> returnList = new ArrayList<>();
        //行数据
        Map<String,Object> dataItem;
        //给你发送通知的用户
        LitemallUser user;
        for(Notes notes:notesList){
            dataItem = new HashMap<>();
            user = litemallUserService.findById(notes.getUserId());
            if(notes.getCreateDate().contains(".0")){
                notes.setCreateDate(notes.getCreateDate().substring(0,notes.getCreateDate().length()-2));
            }
            dataItem.put("createDate",notes.getCreateDate());
            //回复人昵称
            dataItem.put("nickName",user.getNickname());
            //回复人头像
            dataItem.put("avatar",user.getAvatar());
            //评论内容
            dataItem.put("commentContent",articleCommentService.queryById(notes.getInfoid()).getContent());
            //回复内容
            dataItem.put("replyContent",notes.getContent());
            //通知模板内容
            dataItem.put("replyContent",notesTempService.findById(notes.getTempId()).getContent());
            returnList.add(dataItem);
        }
        data.put("returnList",returnList);
        data.put("count",returnList.size());
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 系统通知列表
      * @Date 2018/5/29 17:36
      * @Param [userId]
      * @return java.lang.Object
      **/
    @GetMapping("systemList")
    public Object systemList(Integer userId){
        if (userId==null){
            return ResponseUtil.badArgument();
        }
        List<Notes> notesList = notesService.querySelective(null,1,null,userId,null,"0",null,null,"","");
        if(notesList == null || notesList.size()<=0){
            return ResponseUtil.ok();
        }
        //最终返回data
        Map<String,Object> data = new HashMap<>();
        //封装的对象
        List<Map<String,Object>> returnList = new ArrayList<>();
        //行数据
        Map<String,Object> dataItem;
        //给你发送通知的用户
        LitemallUser user;
        for(Notes notes:notesList){
            dataItem = new HashMap<>();
            user = litemallUserService.findById(notes.getFromUserid());
            if(notes.getCreateDate().contains(".0")){
                notes.setCreateDate(notes.getCreateDate().substring(0,notes.getCreateDate().length()-2));
            }
            dataItem.put("createDate",notes.getCreateDate());
            //回复人昵称
            dataItem.put("nickName",user.getNickname());
            //回复人头像
            dataItem.put("avatar",user.getAvatar());
            //内容
            dataItem.put("content",notes.getContent());
            returnList.add(dataItem);
        }
        data.put("returnList",returnList);
        data.put("count",returnList.size());
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 更新状态
      * @Date 2018/5/29 17:35
      * @Param [id]
      * @return java.lang.Object
      **/
    @PostMapping("update")
    public Object update(Integer id){
        if (id==null){
            return ResponseUtil.badArgument();
        }
        Notes notes = notesService.findById(id);
        if (notes==null){
            return ResponseUtil.ok();
        }
        if("1".equals(notes.getStatus())){
            return ResponseUtil.ok();
        }
        notes.setStatus("1");
        notesService.update(notes);
        return ResponseUtil.ok();
    }
}
