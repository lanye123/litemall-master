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
    public Object list(String artileName, String name,String no,String content,Integer artileId,Integer sortNo,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        order = "create_date";
        List<ArticleNotes> articleNotesList = articleNotesService.querySelective(artileName, name,no,content,sortNo,artileId, page, limit, sort, order);
        int total = articleNotesService.countSelective(artileName, name,no,content,sortNo, artileId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleNotesList);

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
    public Object delete(@RequestBody ArticleNotes articleNotes){

        articleNotesService.deleteById(articleNotes.getId());
        return ResponseUtil.ok(articleNotes);
    }
}
