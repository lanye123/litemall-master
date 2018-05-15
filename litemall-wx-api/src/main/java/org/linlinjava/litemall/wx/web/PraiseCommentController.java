package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.PraiseComment;
import org.linlinjava.litemall.db.service.PraiseCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/praiseComment")
public class PraiseCommentController {
    @Autowired
    private PraiseCommentService praiseCommentService;
    /**
     * 点赞接口
     * @author leiqiang
     * @param comment_id
     * @param reply_id
     * 2018年5月11日 16:50:57
     * @return
     */
    @PostMapping("create")
    public Object create(Integer comment_id,Integer reply_id){
        //首页评论内容点赞
        //有点赞记录则点赞数量累加1

        if(comment_id!=null){
            PraiseComment comment= praiseCommentService.querySelective(comment_id);
            if (comment!=null){
                PraiseComment praiseComment=new PraiseComment();
                Integer praiseCount=comment.getAmount()+1;
                praiseComment.setId(comment.getId());
                praiseComment.setAmount(praiseCount);
                praiseCommentService.update(praiseComment);

            }else{
                PraiseComment comment1=new PraiseComment();
                comment1.setCommentId(comment_id);
                comment1.setAmount(1);
                praiseCommentService.add(comment1);

            }
        }
        //回复列表点赞
        if(reply_id!=null){
            PraiseComment comment2= praiseCommentService.querySelective(reply_id);
            if (comment2!=null){
                PraiseComment praiseComment2=new PraiseComment();
                Integer praiseCount2=comment2.getAmount()+1;
                praiseComment2.setId(comment2.getId());
                praiseComment2.setAmount(praiseCount2);
                praiseCommentService.update(praiseComment2);

            }else{
                PraiseComment comment3=new PraiseComment();
                comment3.setReplyId(reply_id);
                comment3.setAmount(1);
                praiseCommentService.add(comment3);

            }
        }
        return ResponseUtil.ok();
    }


}
