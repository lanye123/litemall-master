package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.service.ArticleReplyService;
import org.linlinjava.litemall.db.service.PraiseCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/comment")
public class ArticleCommentController {
    private final Log logger = LogFactory.getLog(ArticleCommentController.class);

    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleReplyService articleReplyService;
    @Autowired
    private PraiseCommentService praiseCommentService;

    @GetMapping("/list")
    public Object list(Integer articleId, String categoryName,Integer categoryId,String content,Integer fromUserid,
                       String startDate, String endDate,String nickName,Integer type,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        Map<String, Object> data = new HashMap<>();
        long sss = System.currentTimeMillis();
        List<ArticleComment> articleCommentList = articleCommentService.query(articleId, categoryName,categoryId,content,fromUserid,startDate,endDate,nickName,type, page, limit, sort, order);
        int total = articleCommentService.count(articleId, categoryName,categoryId,content,fromUserid,startDate,endDate,nickName,type, page, limit, sort, order);
        data.put("total", total);
        for(ArticleComment articleComment:articleCommentList){
            //统计点赞数量
            articleComment.setCountPraise(praiseCommentService.count(articleComment.getId()));
            articleComment.setReplyCount(articleReplyService.countReply(articleComment.getId()));
        }
        data.put("items", articleCommentList);
        long ddd = System.currentTimeMillis();
        data.put("hhh",ddd-sss);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/yueduhuilist")
    public Object yueduhuilist(Integer articleId, String startDate, String endDate,Integer type,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        Map<String, Object> data = new HashMap<>();
        long sss = System.currentTimeMillis();
        List<Integer> values = new ArrayList<>();
        List<ArticleComment> articleComments = articleCommentService.list2(articleId,startDate,endDate,type,null, page, limit, "from_userid", order);
        for(ArticleComment articleComment:articleComments){
            values.add(articleComment.getFromUserid());
            int commentCount = articleComment.getCommentCount();
            if(commentCount>=4){
                commentCount = 4;
            }
            articleComment.setIntegral(commentCount*20);
        }
        List<ArticleComment> articleCommentTotal = articleCommentService.list(articleId,startDate,endDate,type,null, null, null, "from_userid", order);
        if(articleCommentTotal!=null&&articleCommentTotal.size()>0){
            data.put("total", articleCommentTotal.size());
        }else {
            data.put("total", 0);
        }
        List<ArticleComment> articleCommentList = articleCommentService.list(articleId,startDate,endDate,type,values, null, null, "", order);
        for(ArticleComment articleComment:articleCommentList){
            //统计点赞数量
            articleComment.setCountPraise(praiseCommentService.count(articleComment.getId()));
            articleComment.setReplyCount(articleReplyService.countReply(articleComment.getId()));
        }
        for(ArticleComment articleComment:articleComments){
            for(ArticleComment articleComment2:articleCommentList){
                if(articleComment2.getFromUserid().intValue()==articleComment.getFromUserid().intValue()){
                    articleComment.setReplyCount(articleComment2.getReplyCount()+articleComment.getReplyCount());
                    articleComment.setCountPraise(articleComment2.getCountPraise()+articleComment.getCountPraise());
                }
            }
            articleComment.setIntegral(articleComment.getIntegral()+articleComment.getReplyCount()+articleComment.getCountPraise());
        }
        data.put("items", articleComments);
        long ddd = System.currentTimeMillis();
        data.put("hhh",ddd-sss);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleComment articleComment){
        logger.debug(articleComment);

        articleCommentService.add(articleComment);
        return ResponseUtil.ok(articleComment);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleComment articleComment){
        logger.debug(articleComment);

        articleCommentService.update(articleComment);
        return ResponseUtil.ok(articleComment);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ArticleComment articleComment){

        articleCommentService.deleteById(articleComment.getId());
        return ResponseUtil.ok(articleComment);
    }

    @PostMapping("/hidden")
    public Object hidden(@RequestBody ArticleComment articleComment){
        if(articleComment == null){
            return ResponseUtil.badArgument();
        }

        if(articleComment.getStatus()==0){
            articleComment.setStatus((byte)1);
            articleCommentService.update(articleComment);
        }else if(articleComment.getStatus()==1){
            articleComment.setStatus((byte)0);
            articleCommentService.update(articleComment);
        }
        return ResponseUtil.ok(articleComment);
    }
}
