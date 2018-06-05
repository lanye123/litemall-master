package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleReply;
import org.linlinjava.litemall.db.domain.PraiseComment;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.service.PraiseCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/praiseComment")
public class PraiseCommentController {
    @Autowired
    private PraiseCommentService praiseCommentService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleReplyService articleReplyService;
    /**
     * 点赞接口
     * @author leiqiang
     * 2018年5月11日 16:50:57
     * @return
     */
    @PostMapping("create")
    public Object create(@RequestBody PraiseComment comment){
        //首页评论内容点赞
        //有点赞记录则点赞数量累加1
        if(comment.getCommentId()!=null){
            List<PraiseComment> comments= praiseCommentService.querySelective(null,comment.getCommentId(),comment.getUserId());
            if (comments!=null && comments.size()>0){
                /*PraiseComment praiseComment=new PraiseComment();
                Integer praiseCount=comments.get(0).getAmount()+1;
                praiseComment.setId(comments.get(0).getId());
                praiseComment.setAmount(praiseCount);
                praiseCommentService.update(praiseComment);*/

            }else{
                PraiseComment comment1=new PraiseComment();
                comment1.setCommentId(comment.getCommentId());
                comment1.setAmount(1);
                comment1.setUserId(comment.getUserId());
                ArticleComment articleComment = articleCommentService.queryById(comment.getCommentId());
                if(articleComment==null){
                    return ResponseUtil.fail(999,"该评论不存在");
                }
                comment1.setFromUserId(articleComment.getFromUserid());
                praiseCommentService.add(comment1);

            }
        }
        //回复列表点赞
        if(comment.getReplyId()!=null){
            List<PraiseComment> comment2= praiseCommentService.querySelective(comment.getReplyId(),null,comment.getUserId());
            if (comment2!=null && comment2.size()>0){
                /*PraiseComment praiseComment2=new PraiseComment();

                praiseComment2.setId(comment2.get(0).getId());
                praiseComment2.setReplyId(comment2.get(0).getReplyId());
                praiseComment2.setAmount(comment2.get(0).getAmount());
                praiseComment2.setCreateDate(comment2.get(0).getCreateDate());
                praiseComment2.setUserId(comment2.get(0).getUserId());
                praiseComment2.setStatus(comment2.get(0).getStatus());*/
//                if(comment.getStatus()!=null){
//                    praiseComment2.setStatus(comment.getStatus());
//                }else{
//                    praiseComment2.setStatus(comment2.get(0).getStatus());
//                }

                //praiseCommentService.update(praiseComment2);

            }else{
                PraiseComment comment3=new PraiseComment();
                comment3.setReplyId(comment.getReplyId());
                comment3.setAmount(1);
                //comment3.setStatus((byte)1);
                comment3.setUserId(comment.getUserId());
                ArticleReply articleReply = articleReplyService.queryById(comment.getReplyId());
                if(articleReply==null){
                    return ResponseUtil.fail(999,"该回复不存在");
                }
                comment3.setFromUserId(articleReply.getFromUserid());
                praiseCommentService.add(comment3);

            }
        }
        return ResponseUtil.ok();
    }


}
