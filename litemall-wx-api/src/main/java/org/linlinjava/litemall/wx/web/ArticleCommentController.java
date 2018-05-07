package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/articleComment")
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;

    /**
     * leiqiang
     * 评论列表
     * @param article_id
     * 2018-5-7 15:51:46
     * @return
     */
    @GetMapping("list")
    public Object list(Integer article_id){
        //文章评论列表
        List<ArticleComment> articleCommentList=articleCommentService.querySelective(article_id);
        //文章评论数
        //Long comentCount=articleCommentService.countSelective(article_id);
        List<Map<String, Object>> articleCommentVoList = new ArrayList<>(articleCommentList.size());
        for (ArticleComment comment:articleCommentList){
            Map<String, Object> articleCommentVo = new HashMap<>();
            articleCommentVo.put("id",comment.getId());
            articleCommentVo.put("articleId",comment.getArticleId());
            articleCommentVo.put("comment",comment.getContent());
            articleCommentVo.put("fromUserid",comment.getFromUserid());
            articleCommentVo.put("status",comment.getStatus());
            articleCommentVoList.add(articleCommentVo);
        }
        return ResponseUtil.ok(articleCommentVoList);
    }

    @PostMapping("create")
    public Object create(@RequestBody ArticleComment comment){
        articleCommentService.add(comment);
        return ResponseUtil.ok();
    }
}
