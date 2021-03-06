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
import org.springframework.util.StringUtils;
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
    public Object list(Integer article_id,String flag,Integer user_id,@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "size", defaultValue = "50")Integer size){
        LitemallUser userDb = litemallUserService.findById(user_id);
        if(userDb==null){
            return ResponseUtil.badArgumentValue();
        }
        Integer lock;
        if(StringUtils.isEmpty(userDb.getAccount())){
            lock = 0;
        }else {
            //规划师
            lock = 1;
        }
        //文章评论列表
        List<ArticleComment> articleCommentList=articleCommentService.querySelective(article_id,flag,lock,page,size);
        if(articleCommentList == null || articleCommentList.size()==0){
            return ResponseUtil.ok();
        }
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
            if(comment.getCreateDate().contains(".0")){
                comment.setCreateDate(comment.getCreateDate().substring(0,comment.getCreateDate().length()-2));
            }
            articleCommentVo.put("createDate",comment.getCreateDate());
            articleCommentVo.put("countReply",countReply);
            LitemallUser user=litemallUserService.queryById(comment.getFromUserid());
            if(user!=null){
                articleCommentVo.put("nickname",user.getNickname());
                articleCommentVo.put("avatar",user.getAvatar());
            }
            articleCommentVo.put("countPraise",countPraise);
            articleCommentVo.put("praiseStatus",praiseCommentService.countComment(comment.getId(),user_id,null,null));
            if(user_id==null){
                articleCommentVo.put("praiseStatus",0);
            }
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

    /**
      * @author lanye
      * @Description 我的评论
      * @Date 2018/7/20 10:01
      * @Param [ flag, user_id, page, size]
      * @return java.lang.Object
      **/
    @GetMapping("mylist")
    public Object mylist(String flag,Integer user_id,@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "size", defaultValue = "20")Integer size){
        //文章评论列表
        List<ArticleComment> articleCommentList=articleCommentService.myquery(user_id,flag,page,size);
        if(articleCommentList == null || articleCommentList.size()==0){
            return ResponseUtil.ok();
        }
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
            articleCommentVo.put("title",comment.getTitle());
            articleCommentVo.put("photoUrl",comment.getPhotoUrl());
            articleCommentVo.put("fromUserid",comment.getFromUserid());
            articleCommentVo.put("status",comment.getStatus());
            if(comment.getCreateDate().contains(".0")){
                comment.setCreateDate(comment.getCreateDate().substring(0,comment.getCreateDate().length()-2));
            }
            articleCommentVo.put("createDate",comment.getCreateDate());
            articleCommentVo.put("countReply",countReply);
            LitemallUser user=litemallUserService.queryById(comment.getFromUserid());
            if(user!=null){
                articleCommentVo.put("nickname",user.getNickname());
                articleCommentVo.put("avatar",user.getAvatar());
            }
            articleCommentVo.put("countPraise",countPraise);
            articleCommentVo.put("praiseStatus",praiseCommentService.countComment(comment.getId(),user_id,null,null));
            if(user_id==null){
                articleCommentVo.put("praiseStatus",0);
            }
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

    @PostMapping("delete")
    public Object delete(Integer id,Integer userId){
        LitemallUser userDb = litemallUserService.findById(userId);
        if(userDb==null){
            return ResponseUtil.badArgumentValue();
        }
        articleCommentService.deleteById(id);
        return ResponseUtil.ok();
    }

    @PostMapping("create")
    public Object create(@RequestBody ArticleComment comment,Integer from_userid){
        if(from_userid!=null)
            comment.setFromUserid(from_userid);
        LitemallUser user = litemallUserService.findById(comment.getFromUserid());
        if(user==null){
            return ResponseUtil.badArgument();
        }
        if(!StringUtils.isEmpty(user.getAccount())){
            Set<String> set = new HashSet<>();
            List<ArticleComment> articleCommentList = articleCommentService.myquery(comment.getFromUserid(),"",null,null);
            for(ArticleComment articleComment:articleCommentList){
                set.add(articleComment.getContent());
            }
            int old = set.size();
            set.add(comment.getContent());
            int ne = set.size();
            if(old==ne&&comment.getContent().length()>50){
                return ResponseUtil.fail(101,"该内容已经回复过了，换个内容吧");
            }
        }
        articleCommentService.add(comment);
        return ResponseUtil.ok();
    }

    /**
     *
     * @return
     */

    @GetMapping("detail")
    public Object detail(Integer id,Integer status,Integer userId){
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
        if(status!=null){
            if(status == 0){
                reply.setContent("create_date desc");
            }else if(status == 1){
                reply.setContent("amount desc");
            }
        }
        List<ArticleReply> replyList=articleReplyService.queryByList(reply);
        data.put("replyList",replyList);
        List<Map<String, Object>> articleReplyVoList = new ArrayList<>(replyList.size());
        for (ArticleReply articleReply:replyList){
            //统计文章回复数量
//            Integer countReply=articleReplyService.countReply(comment.getId());
            //统计点赞数量
//            Integer countPraise=praiseCommentService.count(comment.getId());
            Map<String, Object> articleReplyVo = new HashMap<>();
            articleReplyVo.put("id",articleReply.getId());
            articleReplyVo.put("commentId",articleReply.getCommentId());
            articleReplyVo.put("content",articleReply.getContent());
            articleReplyVo.put("replyId",articleReply.getReplyId());
            articleReplyVo.put("fromUserid",articleReply.getFromUserid());
            articleReplyVo.put("fromAvatar",articleReply.getFrom_avatar());
            articleReplyVo.put("fromNickname",articleReply.getFrom_nickname());
            articleReplyVo.put("status",articleReply.getStatus());
            articleReplyVo.put("replyType",articleReply.getReplyType());
            if(articleReply.getCreateDate().contains(".0")){
                articleReply.setCreateDate(articleReply.getCreateDate().substring(0,articleReply.getCreateDate().length()-2));
            }
            articleReplyVo.put("createDate",articleReply.getCreateDate());
            articleReplyVo.put("amount",articleReply.getAmount());
            articleReplyVo.put("toUserid",articleReply.getToUserid());
            articleReplyVo.put("toNickname",articleReply.getTo_nickname());
            articleReplyVo.put("toAvatar",articleReply.getTo_avatar());
            /*LitemallUser user=litemallUserService.queryById(comment.getFromUserid());
            if(user!=null){
                articleReplyVo.put("nickname",user.getNickname());
                articleReplyVo.put("avatar",user.getAvatar());
            }*/
            articleReplyVo.put("praiseStatus",praiseCommentService.countComment(null,userId,null,articleReply.getId()));
            articleReplyVoList.add(articleReplyVo);
        }
        //articleReplyVoList.add(data);
        return ResponseUtil.ok(articleReplyVoList);
    }
}
