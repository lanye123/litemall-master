package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/comment")
public class ArticleCommentController {
    private final Log logger = LogFactory.getLog(ArticleCommentController.class);

    @Autowired
    private ArticleCommentService articleCommentService;

    @GetMapping("/list")
    public Object list(Integer articleId, String categoryName,Integer categoryId,String content,Integer fromUserid,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleComment> articleCommentList = articleCommentService.query(articleId, categoryName,categoryId,content,fromUserid, page, limit, sort, order);
        int total = articleCommentService.count(articleId, categoryName,categoryId,content,fromUserid, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("articleCommentList", articleCommentList);

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
}
