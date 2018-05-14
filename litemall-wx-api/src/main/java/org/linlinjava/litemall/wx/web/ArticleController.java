package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleCollection;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.ArticleCollectionService;
import org.linlinjava.litemall.db.service.ArticleCommentService;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/article")
public class ArticleController {
    private final Log logger = LogFactory.getLog(ArticleController.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private ArticleCollectionService articleCollectionService;
    @Autowired
    private ArticleCommentService articleCommentService;
/**
    *@Author:LeiQiang
    *@Description:全部图文模块列表接口
    *@Date:22:46 2018/5/4
    */
    @GetMapping("list")
    public Object list(String categoryIds,String flag){
        List<Article> articleList=articleService.querySelective(categoryIds,flag);
        //Long comentCount=articleCommentService.countSelective(article_id);
        List<Map<String, Object>> articleVoList = new ArrayList<>(articleList.size());
        for(Article article : articleList){
            Map<String, Object> articleVo = new HashMap<>();
            articleVo.put("photo_url",article.getPhotoUrl());
            articleVo.put("photo_name",article.getPhotoName());
            articleVo.put("article_id",article.getArticleId());
            articleVo.put("category_id",article.getCategoryId());
            articleVo.put("title",article.getTitle());
            articleVo.put("brief",article.getBrief());
            articleVo.put("create_date",article.getCreateDate());
            articleVo.put("daodu",article.getDaodu());
            articleVo.put("author",article.getAuthor());
            articleVo.put("status",article.getStatus());
            articleVo.put("is_view",article.getIsView());
            articleVo.put("reader",article.getReader());
            articleVo.put("update_date",article.getUpdateDate());
            articleVoList.add(articleVo);
        }
        return ResponseUtil.ok(articleVoList);
    }

/**
    *@Author:LeiQiang
    *@Description:文章详情接口
    *@Date:22:54 2018/5/4
    */
    @GetMapping("detail")
    public Object detail(Integer article_id) {
        /*if(userId == null){
            return ResponseUtil.unlogin();
        }*/
        if(article_id == null){
            return ResponseUtil.badArgument();
        }

        Article article=articleService.findById(article_id);
        article.setReadCount(article.getReadCount()+1);
        articleService.updateById(article);
        if(article == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("photo_url",article.getPhotoUrl());
        data.put("photo_name",article.getPhotoName());
        data.put("article_id",article.getArticleId());
        data.put("category_id",article.getCategoryId());
        data.put("title",article.getTitle());
        data.put("brief",article.getBrief());
        data.put("create_date",article.getCreateDate());
        data.put("daodu",article.getDaodu());
        data.put("author",article.getAuthor());
        data.put("status",article.getStatus());
        data.put("is_view",article.getIsView());
        data.put("reader",article.getReader());
        data.put("update_date",article.getUpdateDate());
        data.put("readCount",article.getReadCount());
        //评论数量
        Long comentCount=articleCommentService.countSelective(article_id);
        //目录列表
        List<ArticleNotes> notesList=articleNotesService.findByArtitleid(article_id);
        List<Map<String, Object>> notesVoList = new ArrayList<>(notesList.size());
        for(ArticleNotes notes : notesList) {
            Map<String, Object> notesVo = new HashMap<>();
            notesVo.put("id",notes.getId());
            notesVo.put("no",notes.getNo());
            notesVo.put("name",notes.getName());
            notesVo.put("content",notes.getContent());
            notesVo.put("status",notes.getStatus());
            notesVo.put("sort_no",notes.getSortNo());
            notesVoList.add(notesVo);
        }
        data.put("notesList",notesVoList);
        data.put("comentCount",comentCount);
        return ResponseUtil.ok(data);
    }
/**
    *@Author:LeiQiang
    *@Description:图文-收藏接口
    *@Date:23:24 2018/5/4
    */
    @PostMapping("collect")
    public Object collect(Integer article_id,String status,@RequestParam Integer user_id) {
        /*if(userId == null){
            return ResponseUtil.unlogin();
        }*/
        if(article_id == null){
            return ResponseUtil.badArgument();
        }
        Article article=articleService.findById(article_id);
        //保存至收藏表
        ArticleCollection collection=new ArticleCollection();
        collection.setArticleId(article_id);
        collection.setUserId(user_id);
        articleCollectionService.add(collection);
        //更新文章阅读总数reader
        Article article1=new Article();
        if(article.getReader()==null){
            article1.setReader(1);
        }else{
            article1.setReader(article.getReader()+1);
        }
        article1.setArticleId(article.getArticleId());
        articleService.update(article1);
        return ResponseUtil.ok(article1);
    }
}