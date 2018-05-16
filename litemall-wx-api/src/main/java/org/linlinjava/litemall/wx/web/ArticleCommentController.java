package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.PraiseCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/wx/articleComment")
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleReplyService articleReplyService;
    @Autowired
    private PraiseCommentService praiseCommentService;
    @Autowired
    private LitemallUserService litemallUserService;
    /**
     * leiqiang
     * 评论列表
     * @param article_id
     * 2018-5-7 15:51:46
     * @return
     */
    @GetMapping("list")
    public Object list(Integer article_id,String flag){
        //文章评论列表
        List<ArticleComment> articleCommentList=articleCommentService.querySelective(article_id,flag);
        //文章评论数
        Long comentCount=articleCommentService.countSelective(article_id);
        List<Map<String, Object>> articleCommentVoList = new ArrayList<>(articleCommentList.size());
        for (ArticleComment comment:articleCommentList){
            //统计文章回复数量
            Integer countReply=articleReplyService.countReply(comment.getId());
            //统计点赞数量
            Integer countPraise=praiseCommentService.count(comment.getId());
            Map<String, Object> articleCommentVo = new HashMap<>();
            articleCommentVo.put("id",comment.getId());
            articleCommentVo.put("articleId",comment.getArticleId());
            articleCommentVo.put("comment",comment.getContent());
            articleCommentVo.put("fromUserid",comment.getFromUserid());
            articleCommentVo.put("status",comment.getStatus());
            articleCommentVo.put("createDate",comment.getCreateDate());
            articleCommentVo.put("countReply",countReply);
            LitemallUser user=litemallUserService.queryById(comment.getFromUserid());
            articleCommentVo.put("nickname",user.getNickname());
            articleCommentVo.put("avatar",user.getAvatar());
            articleCommentVo.put("countPraise",countPraise);
            articleCommentVoList.add(articleCommentVo);
        }
        if("1".equals(flag)){
            Collections.sort(articleCommentVoList, (s1, s2) ->{
                if(s1 == null)
                    return -1;
                if(s2 == null)
                    return 1;
                return (int)s2.get("countPraise") - (int)s1.get("countPraise");
            });
        }
        return ResponseUtil.ok(articleCommentVoList);
    }

    @PostMapping("create")
    public Object create(@RequestBody ArticleComment comment,Integer from_userid){
        if(from_userid!=null)
            comment.setFromUserid(from_userid);
        articleCommentService.add(comment);
        return ResponseUtil.ok();
    }

    /**
     *
     * @param id
     * @return
     */

    @GetMapping("detail")
    public Object detail(Integer id,Integer status){
        if(id == null){
            return ResponseUtil.badArgument();
        }
        ArticleComment comment=articleCommentService.queryById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("comment",comment);
        //统计文章回复数量
        //Integer countReply=articleReplyService.countReply(comment.getId());
        //回复详情列表
        //List<ArticleReply> replyList=articleReplyService.queryByCommentId(id);
        ArticleReply reply=new ArticleReply();
        reply.setCommentId(id);
        //获取评论详情信息、回复信息、回复人，回复人图像，回复点赞数
        if(status == 0){
            reply.setContent("create_date desc");
        }else if(status == 1){
            reply.setContent("amount desc");
        }
        List<ArticleReply> replyList=articleReplyService.queryByList(reply);
        data.put("replyList",replyList);
        return ResponseUtil.ok(data);
    }
}
