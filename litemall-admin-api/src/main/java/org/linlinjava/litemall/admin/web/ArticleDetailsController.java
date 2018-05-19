package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleDetails;
import org.linlinjava.litemall.db.service.ArticleDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/details")
public class ArticleDetailsController {
    private final Log logger = LogFactory.getLog(ArticleDetailsController.class);

    @Autowired
    private ArticleDetailsService articleDetailsService;

    @GetMapping("/list")
    public Object list(Integer categoryId,Integer articleId,Integer notesId,Integer userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleDetails> articleDetailsList = articleDetailsService.querySelective(categoryId,articleId,notesId, userId,"", page, limit, sort, order);
        int total = articleDetailsService.countSeletive(categoryId,articleId,notesId, userId,"");
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleDetailsList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleDetails articleDetails){
        logger.debug(articleDetails);

        articleDetailsService.add(articleDetails);
        return ResponseUtil.ok(articleDetails);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleDetails articleDetails){
        logger.debug(articleDetails);

        articleDetailsService.update(articleDetails);
        return ResponseUtil.ok(articleDetails);
    }


    @PostMapping("/delete")
    public Object delete(@RequestBody ArticleDetails articleDetails){

        articleDetailsService.deleteById(articleDetails.getId());
        return ResponseUtil.ok(articleDetails);
    }
}
