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
    private Object list(String categoryIds,String flag,Integer userId){
        List<Article> articleList=articleService.querySelective2(categoryIds,flag);
        //Long comentCount=articleCommentService.countSelective(article_id);
        List<Map<String, Object>> articleVoList = new ArrayList<>(articleList.size());
        if(articleList!=null && articleList.size()>0){
            for(Article article : articleList){
                if(article==null){
                    return ResponseUtil.ok(articleVoList);
                }
                Map<String, Object> articleVo = new HashMap<>();
                if(article.getPhotoUrl()!=null){
                    articleVo.put("photo_url",article.getPhotoUrl());
                }
                if(article.getHeadUrl()!=null){
                    articleVo.put("headUrl",article.getHeadUrl());
                }
                if(article.getPhotoName()!=null){
                    articleVo.put("photo_name",article.getPhotoName());
                }
                if(article.getArticleId()!=null){
                    articleVo.put("article_id",article.getArticleId());
                }
                if(article.getCategoryId()!=null){
                    articleVo.put("category_id",article.getCategoryId());
                }
                if(article.getTitle()!=null){
                    articleVo.put("title",article.getTitle());
                }
                if(article.getBrief()!=null){
                    articleVo.put("brief",article.getBrief());
                }
                if(article.getCreateDate().contains(".0")){
                    article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
                }
                if(article.getCreateDate()!=null){
                    articleVo.put("create_date",article.getCreateDate());
                }
                if(article.getDaodu()!=null){
                    articleVo.put("daodu",article.getDaodu());
                }
                if(article.getAuthor()!=null){
                    articleVo.put("author",article.getAuthor());
                }
                if(article.getStatus()!=null){
                    articleVo.put("status",article.getStatus());
                }
                if(article.getIsView()!=null){
                    articleVo.put("is_view",article.getIsView());
                }
                if(article.getReader()!=null){
                    articleVo.put("reader",article.getReader());
                }
                if(article.getReadCount()!=null){
                    articleVo.put("readCount",article.getReadCount());
                }
                if(article.getUpdateDate()!=null){
                    articleVo.put("update_date",article.getUpdateDate());
                }
                //查当前用户是否收藏了这本书
                articleVo.put("collectStatus", articleCollectionService.countSeletive(article.getArticleId(),userId,1,null,null,"",""));
                articleVo.put("flag", medalDetailsService.countSeletive(0,article.getArticleId(),userId,null,null,null,null,"",""));
                articleVoList.add(articleVo);
            }
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
        if(article.getCreateDate().contains(".0")){
            article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
        }
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
            if(notes.getCreateDate().contains(".0")){
                notes.setCreateDate(notes.getCreateDate().substring(0,notes.getCreateDate().length()-2));
            }
            notesVo.put("create_date",notes.getCreateDate());
            notesVo.put("daodu",notes.getDaodu());
            notesVo.put("author",notes.getAuthor());
            notesVo.put("status",notes.getStatus());
            notesVo.put("reader",notes.getRender());
            notesVo.put("photo_url",notes.getPhotoUrl());
            notesVo.put("photo_name",notes.getPhotoName());
            notesVo.put("article_id",notes.getArtileId());
            notesVo.put("flag", medalDetailsService.countSeletive(notes.getId(),article_id,userId,null,null,null,null,"",""));
            notesVoList.add(notesVo);
        }
        data.put("notesList",notesVoList);
        data.put("comentCount",comentCount);
        data.put("flag", medalDetailsService.countSeletive(0,article_id,userId,null,null,null,null,"",""));
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
        List<ArticleCollection> articleCollectionList = articleCollectionService.querySelective(model.getArticleId(),model.getUser_id(),null,null,null,"","");
        if(articleCollectionList.size()==0){
            ArticleCollection collection = new ArticleCollection();
            collection.setArticleId(model.getArticleId());
            collection.setUserId(model.getUser_id());
            collection.setStatus(model.getStatus());
            articleCollectionService.add(collection);
        }else
        {
            for(ArticleCollection articleCollection:articleCollectionList){
                articleCollection.setStatus(1);
                articleCollectionService.update(articleCollection);
            }
        }
    }
    return ResponseUtil.ok();
}
}