package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/articleReply")
public class ArticleReplyController {
    private final Log logger = LogFactory.getLog(ArticleReplyController.class);

    @Autowired
    private ArticleReplyService articleReplyService;

    @GetMapping("/list")
    public Object list(Integer commentId, Integer replyId, String replyType, String content, Integer fromUserid,Integer toUserid,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleReply> articleNotesList = articleReplyService.querySelective(commentId, replyId,replyType,content,fromUserid, toUserid,page, limit, sort, order);
        int total = articleReplyService.countSelective(commentId, replyId,replyType,content,fromUserid, toUserid, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleNotesList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleReply articleReply){
        logger.debug(articleReply);

        articleReplyService.add(articleReply);
        return ResponseUtil.ok(articleReply);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleReply articleReply){
        logger.debug(articleReply);

        articleReplyService.update(articleReply);
        return ResponseUtil.ok(articleReply);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ArticleReply articleReply){

        articleReplyService.deleteById(articleReply.getId());
        return ResponseUtil.ok(articleReply);
    }
}
