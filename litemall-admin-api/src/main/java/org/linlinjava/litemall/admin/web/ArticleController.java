package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Object list(String title,String author,Integer articleId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Article> articleList = articleService.queryBySelective(title,author,articleId, page, limit, sort, order);
        int total = articleService.countSelective(title,author,articleId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Article article){
        articleService.add(article);
        return ResponseUtil.ok(article);
    }

    @GetMapping("/read")
    public Object read(Integer articleId){
        if(articleId == null){
            return ResponseUtil.badArgument();
        }

        Article article = articleService.findById(articleId);
        return ResponseUtil.ok(article);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Article article){
        articleService.updateById(article);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Article article){
        articleService.deleteById(article.getArticleId());
        return ResponseUtil.ok();
    }
}
