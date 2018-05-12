package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.ArticleCollectionService;
import org.linlinjava.litemall.db.service.ArticleDetailsService;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/collection")
public class ArticleCollectionController {
    @Autowired
    private ArticleCollectionService articleCollectionService;
    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private ArticleDetailsService articleDetailsService;
    @Autowired
    private ArticleService articleService;
    /**
     *@Author:lanye
     *@Description:收藏列表接口
     *@Date:13:44 2018/5/7
     */
    @GetMapping("list")
    public Object list(@RequestParam Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Map<String,Object> data = new HashMap<>();
        Map<String, Object> dataItem;
        List<ArticleCollection> articleCollectionList = articleCollectionService.selectByUserId(userId);

        List<Map<String,Object>> returnArticles = new ArrayList<>();
        int allCount;
        int readCount;
        Article article;
        for(ArticleCollection articleCollection:articleCollectionList){
            dataItem = new HashMap<>();
            allCount = articleNotesService.findByArtitleid(articleCollection.getArticleId()).size();
            if(allCount == 0){
                continue;
            }
            article = articleService.findById(articleCollection.getArticleId());
            readCount = articleDetailsService.selectList(userId,null,articleCollection.getArticleId(),null).size();
            BigDecimal bd = new BigDecimal(readCount);
            bd = bd.divide(new BigDecimal(allCount),2,BigDecimal.ROUND_HALF_UP);
            dataItem.put("percentage",bd.toString());
            dataItem.put("readCount",readCount);
            dataItem.put("allCount",allCount);
            dataItem.put("title",article.getTitle());
            dataItem.put("brief",article.getBrief());
            dataItem.put("photoName",article.getPhotoName());
            dataItem.put("photoUrl",article.getPhotoUrl());
            dataItem.put("daodu",article.getDaodu());
            dataItem.put("author",article.getAuthor());
            dataItem.put("articleId",article.getArticleId());
            returnArticles.add(dataItem);
        }
        data.put("returnArticles", returnArticles);

        return ResponseUtil.ok(data);
    }

    /**
     *@Author:lanye
     *@Description:我的收藏中的查看接口
     *@Date:14:20 2018/5/7
     */
    @GetMapping("view")
    public Object view(@RequestParam Integer userId,Integer artile_id){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Map<String,Object> data = new HashMap<>();
        List<ArticleNotes> articleNotes = articleNotesService.findByArtitleid(artile_id);
        List<ArticleDetails> articleDetailsList = articleDetailsService.selectList(userId,null,artile_id,null);
        data.put("articleNotes",articleNotes);
        data.put("readNo",articleDetailsList.size());
        return ResponseUtil.ok(data);
    }

    /**
     *@Author:LeiQiang
     *@Description:图文-收藏接口
     *@Date:23:24 2018/5/4
     */
    @PostMapping("save")
    public Object save(Integer userId, @RequestBody ArticleCollection collection) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(collection == null){
            return ResponseUtil.badArgument();
        }

        if (collection.getId() == null || collection.getId().equals(0)) {
            collection.setId(null);
            collection.setUserId(userId);
            articleCollectionService.add(collection);
        } else {
            collection.setUserId(userId);
            collection.setStatus(false);
            articleCollectionService.update(collection);
        }
        return ResponseUtil.ok();
    }
}
