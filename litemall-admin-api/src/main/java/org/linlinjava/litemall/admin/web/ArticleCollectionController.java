package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleCollection;
import org.linlinjava.litemall.db.service.ArticleCollectionService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/articleCollection")
public class ArticleCollectionController {
    private final Log logger = LogFactory.getLog(ArticleCollectionController.class);

    @Autowired
    private ArticleCollectionService articleCollectionService;

    @GetMapping("/list")
    public Object list(Integer articleId,Integer userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleCollection> articleCollectionList = articleCollectionService.querySelective(articleId, userId, page, limit, sort, order);
        int total = articleCollectionService.countSeletive(articleId, userId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleCollectionList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleCollection articleCollection){
        logger.debug(articleCollection);

        articleCollectionService.add(articleCollection);
        return ResponseUtil.ok(articleCollection);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleCollection articleCollection){
        logger.debug(articleCollection);

        articleCollectionService.update(articleCollection);
        return ResponseUtil.ok(articleCollection);
    }

    @PostMapping("/hidden")
    public Object hidden(Integer id){

        articleCollectionService.hidden(id);
        return ResponseUtil.ok(id);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){

        articleCollectionService.deleteById(id);
        return ResponseUtil.ok(id);
    }
}
