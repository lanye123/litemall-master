package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesTemp;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.NotesService;
import org.linlinjava.litemall.db.service.NotesTempService;
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
    @Autowired
    private NotesService notesService;
    @Autowired
    private NotesTempService notesTempService;
    @Autowired
    private LitemallUserService litemallUserService;

    @GetMapping("/list")
    public Object list(String title,String author,Integer articleId,Integer categoryId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Article> articleList = articleService.queryBySelective(title,author,articleId,categoryId, page, limit, sort, order);
        int total = articleService.countSelective(title,author,articleId,categoryId, page, limit, sort, order);
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

    @PostMapping("/online")
    public Object online(@RequestBody Article article){
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        if(articleDb.getStatus()==0){
            articleDb.setStatus(1);
            articleService.updateById(articleDb);
        }else if(articleDb.getStatus()==1){
            articleDb.setStatus(0);
            articleService.updateById(articleDb);
        }
        return ResponseUtil.ok(articleDb);
    }

    @PostMapping("/anli")
    public Object anli(@RequestBody Article article){
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        if(articleDb.getIsView()==0){
            articleDb.setIsView(1);
            articleService.updateById(articleDb);
        }else if(articleDb.getIsView()==1){
            articleDb.setIsView(0);
            articleService.updateById(articleDb);
        }
        return ResponseUtil.ok(articleDb);
    }

    @PostMapping("/push")
    public Object push(@RequestBody Article article){
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        List<NotesTemp> notesTemps = notesTempService.querySelective("newbook","",null,"",null,null,"","");
        if(notesTemps==null || notesTemps.size()==0){
            return ResponseUtil.ok(article);
        }
        List<LitemallUser> userList = litemallUserService.querySelective("","","",null,null,"","");
        NotesTemp notesTemp = notesTemps.get(0);
        Notes notes;
        for(LitemallUser user:userList){
            notes = new Notes();
            notes.setFromUserid(user.getId());
            notes.setTempId(notesTemp.getId());
            notes.setType(notesTemp.getType());
            notes.setContent(notesTemp.getContent().replace("TITLE",articleDb.getTitle()));
            notes.setNo(notesTemp.getNo());
            notes.setInfoid(article.getArticleId());
            if(notesService.countSeletive(notes.getTempId(),notes.getType(),null,notes.getFromUserid(),notes.getInfoid(),"",null,null,"","")>0){
                continue;
            }
            notesService.add(notes);
        }
        return ResponseUtil.ok(articleDb);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Article article){
        articleService.deleteById(article.getArticleId());
        return ResponseUtil.ok();
    }
    @PostMapping("/test")
    public Object test(@RequestBody Article article){
        articleService.add(article);
        return ResponseUtil.ok(article.getArticleId());
    }
}
