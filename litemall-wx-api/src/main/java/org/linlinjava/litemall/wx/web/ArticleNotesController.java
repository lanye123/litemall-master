package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/notes")
public class ArticleNotesController {
    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private MedalDetailsService medalDetailsService;

    @RequestMapping("detail")
    public Object detail(@RequestParam Integer notesId,@RequestParam Integer articleId,@RequestParam Integer userId){
        ArticleNotes notes=articleNotesService.findByID(notesId);
        if(articleId == null){
            ResponseUtil.badArgument();
        }
        if(userId == null){
            ResponseUtil.badArgument();
        }
        Map<String, Object> data = new HashMap<>();
            data.put("id",notes.getId());
            data.put("articleId",notes.getArtileId());
            data.put("article_name",notes.getArticleName());
            data.put("name",notes.getName());
            data.put("no",notes.getNo());
            data.put("sortNo",notes.getSortNo());
            data.put("content",notes.getContent());
            data.put("brief",notes.getBrief());
            data.put("daodu",notes.getDaodu());
            data.put("author",notes.getAuthor());
            data.put("photoName",notes.getPhotoName());
            data.put("render",notes.getRender());
            data.put("flag", medalDetailsService.countSeletive(notesId,articleId,userId,null,null,null,null,"",""));
            return ResponseUtil.ok(data);
    }
}
