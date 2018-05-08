package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/articleNotes")
public class ArticleNotesController {
    private final Log logger = LogFactory.getLog(ArticleNotesController.class);

    @Autowired
    private ArticleNotesService articleNotesService;

    @GetMapping("/list")
    public Object list(Integer artileId, String name,String no,String content,Integer sortNo,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleNotes> articleNotesList = articleNotesService.querySelective(artileId, name,no,content,sortNo, page, limit, sort, order);
        int total = articleNotesService.countSelective(artileId, name,no,content,sortNo, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("articleNotesList", articleNotesList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleNotes articleNotes){
        logger.debug(articleNotes);

        articleNotesService.add(articleNotes);
        return ResponseUtil.ok(articleNotes);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleNotes articleNotes){
        logger.debug(articleNotes);

        articleNotesService.update(articleNotes);
        return ResponseUtil.ok(articleNotes);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){

        articleNotesService.deleteById(id);
        return ResponseUtil.ok(id);
    }
}
