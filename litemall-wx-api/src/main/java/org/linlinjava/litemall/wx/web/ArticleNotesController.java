package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/notes")
public class ArticleNotesController {
    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("detail")
    public Object detail(@RequestParam Integer notesId,@RequestParam Integer articleId,Integer userId,Integer isCount){
        ArticleNotes notes=articleNotesService.findByID(notesId);
        if(isCount!=null && isCount==1){
            notes.setReadCount(notes.getReadCount()+1);
            articleNotesService.update(notes);
        }
        if(articleId == null){
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
            data.put("photoUrl",notes.getPhotoUrl());
            data.put("codeUrl",notes.getCode_url());
            if(notes.getCreateDate().contains(".0")){
                notes.setCreateDate(notes.getCreateDate().substring(0,notes.getCreateDate().length()-2));
            }
            data.put("createDate",notes.getCreateDate());
            data.put("render",notes.getRender());
            data.put("flag", medalDetailsService.countSeletive(notesId,articleId,userId,null,null,null,null,"",""));
            if(userId == null){
                data.put("flag",0);
            }
            data.put("readCount",notes.getReadCount());
            return ResponseUtil.ok(data);
    }

    /**
     * @author lanye
     * @Description 更新分享次数
     * @Date 2018/6/20 16:02
     * @Param [articleId]
     * @return java.lang.Object
     **/
    @PostMapping("/share")
    public Object share(Integer id){
        ArticleNotes articleNotes = articleNotesService.findByID(id);
        if(articleNotes!=null){
            articleNotes.setShareCount(articleNotes.getShareCount()+1);
            articleNotesService.update(articleNotes);
            Article article = articleService.findById(articleNotes.getArtileId());
            if(article!=null){
                article.setShareCount(article.getShareCount()+1);
                articleService.updateById(article);
            }
        }
        return ResponseUtil.ok(articleNotes);
    }
}
