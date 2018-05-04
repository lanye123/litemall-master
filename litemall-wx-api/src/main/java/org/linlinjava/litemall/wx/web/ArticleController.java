package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 文章图文列表
     *
     * @param userId 用户ID
     * @return 文章图文列表
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data: xxx
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,Integer category_id){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        List<Article> articleList=articleService.querySelective(category_id);
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

    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, Integer artitle_id) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(artitle_id == null){
            return ResponseUtil.badArgument();
        }

        Article article=articleService.findById(artitle_id);
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
        return ResponseUtil.ok(data);
    }
}
