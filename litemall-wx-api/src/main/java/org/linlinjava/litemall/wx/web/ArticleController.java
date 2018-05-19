package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private ArticleDetailsService articleDetailsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
/**
    *@Author:LeiQiang
    *@Description:全部图文模块列表接口
    *@Date:22:46 2018/5/4
    */
    @GetMapping("list")
    public Object list(String categoryIds,String flag,Integer userId){
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
            articleVo.put("readCount",article.getReadCount());
            //查当前用户是否收藏了这本书
            articleVo.put("collectStatus", articleCollectionService.countSeletive(article.getArticleId(),userId,1,null,null,"",""));
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
    public Object detail(Integer article_id, Integer userId) {
        /*if(userId == null){
            return ResponseUtil.unlogin();
        }*/
        if(article_id == null){
            return ResponseUtil.badArgument();
        }
        if(userId == null){
            return ResponseUtil.badArgument();
        }

        Article article=articleService.findById(article_id);
        article.setReadCount(article.getReadCount()+1);
        articleService.updateById(article);
        if(article == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap<>();
        JSONArray categoryIds = JSON.parseArray(article.getCategoryIds());
        String categoryName = "";
        for(Object categoryId:categoryIds){
            categoryName += articleCategoryService.findById((Integer) categoryId).getName()+",";
        }
        data.put("photo_url",article.getPhotoUrl());
        data.put("photo_name",article.getPhotoName());
        data.put("article_id",article.getArticleId());
        data.put("category_id",article.getCategoryId());
        data.put("categoryIds",article.getCategoryIds());
        data.put("categoryName",categoryName);
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
        List<ArticleDetails> articleDetailsList;
        for(ArticleNotes notes : notesList) {
            Map<String, Object> notesVo = new HashMap<>();
            notesVo.put("read","no");
            articleDetailsList = articleDetailsService.selectList(userId,null,article_id,notes.getId(),"");
            if(articleDetailsList!=null && articleDetailsList.size()>0){
                notesVo.put("read","yes");
            }
            notesVo.put("id",notes.getId());
            notesVo.put("no",notes.getNo());
            notesVo.put("name",notes.getName());
            notesVo.put("content",notes.getContent());
            notesVo.put("status",notes.getStatus());
            notesVo.put("sort_no",notes.getSortNo());
            notesVo.put("brief",notes.getBrief());
            notesVo.put("create_date",notes.getCreateDate());
            notesVo.put("daodu",notes.getDaodu());
            notesVo.put("author",notes.getAuthor());
            notesVo.put("status",notes.getStatus());
            notesVo.put("reader",notes.getRender());
            notesVo.put("photo_url",notes.getPhotoUrl());
            notesVo.put("photo_name",notes.getPhotoName());
            notesVo.put("article_id",notes.getArtileId());
            notesVoList.add(notesVo);
        }
        data.put("notesList",notesVoList);
        data.put("comentCount",comentCount);
        data.put("flag", medalDetailsService.countSeletive(0000,article_id,userId,null,null,null,null,"",""));
        data.put("collectStatus", articleCollectionService.countSeletive(article_id,userId,1,null,null,"",""));
        return ResponseUtil.ok(data);
    }
/**
    *@Author:LeiQiang
    *@Description:图文-收藏接口
    *@Date:23:24 2018/5/4
    */
@PostMapping("collect")
public Object collect(@RequestBody Article model) {
    if(model.getArticleId() == null){
        return ResponseUtil.badArgument();
    }
    if(model.getUser_id() == null){
        return ResponseUtil.badArgument();
    }
    Article article=articleService.findById(model.getArticleId());

    //更新文章阅读总数reader
    Article article1=new Article();
    if(articleCollectionService.countSeletive(model.getArticleId(),model.getUser_id(),null,null,null,"","") == 0){
        //加成长值
        MedalDetails medalDetails = new MedalDetails();
        medalDetails.setUserId(model.getUser_id());
        medalDetails.setArticleId(model.getArticleId());
        medalDetails.setAmount(10);
        medalDetails.setNotesId(0);
        medalDetailsService.add(medalDetails);
        if(article.getReader()==null){
            article1.setReader(1);
        }else{
            article1.setReader(article.getReader()+1);
        }
        article1.setArticleId(article.getArticleId());
        articleService.update(article1);
    }
    //保存至收藏表
    if(model.getStatus() == 0){
        //用户取消收藏
        List<ArticleCollection> articleCollectionList = articleCollectionService.querySelective(model.getArticleId(),model.getUser_id(),1,null,null,"","");
        for(ArticleCollection articleCollection:articleCollectionList){
            articleCollection.setStatus(0);
            articleCollectionService.update(articleCollection);
        }
    }else if(model.getStatus() == 1){
        //用户收藏
        ArticleCollection collection = new ArticleCollection();
        collection.setArticleId(article.getArticleId());
        collection.setUserId(model.getUser_id());
        collection.setStatus(model.getStatus());
        articleCollectionService.add(collection);
    }
    return ResponseUtil.ok(article1);
}
}