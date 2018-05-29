package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesTemp;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.service.NotesService;
import org.linlinjava.litemall.db.service.NotesTempService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/articleReply")
public class ArticleReplyController {
    @Autowired
    private ArticleReplyService articleReplyService;
    @Autowired
    private NotesTempService notesTempService;
    @Autowired
    private NotesService notesService;

    @PostMapping("create")
    public Object create(@RequestBody ArticleReply reply,Integer from_userid,Integer to_userid){
        if (from_userid!=null)
            reply.setFromUserid(from_userid);
        if(to_userid!=null)
            reply.setToUserid(to_userid);
        articleReplyService.add(reply);
        List<NotesTemp> notesTemps = notesTempService.querySelective("reply","",null,"",null,null,"","");
        if(notesTemps==null || notesTemps.size()==0){
            return ResponseUtil.ok(reply);
        }
        NotesTemp notesTemp = notesTemps.get(0);
        Notes notes = new Notes();
        notes.setUserId(reply.getToUserid());
        notes.setFromUserid(reply.getFromUserid());
        notes.setTempId(notesTemp.getId());
        notes.setType(notesTemp.getType());
        notes.setContent(reply.getContent());
        notes.setNo(notesTemp.getNo());
        notes.setInfoid(reply.getCommentId());
        notesService.add(notes);
        return ResponseUtil.ok(reply);
    }
}
