package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/articleReply")
public class ArticleReplyController {
    @Autowired
    private ArticleReplyService articleReplyService;

    @PostMapping("create")
    public Object create(@RequestBody ArticleReply reply,Integer from_userid,Integer to_userid){
        if (from_userid!=null)
            reply.setFromUserid(from_userid);
        if(to_userid!=null)
            reply.setToUserid(to_userid);
        articleReplyService.add(reply);
        return ResponseUtil.ok(reply);
    }
}
