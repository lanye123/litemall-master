package org.linlinjava.litemall.wx.web;

import org.apache.log4j.Logger;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.domain.ArticleDetails;
import org.linlinjava.litemall.db.service.ArticleCategoryService;
import org.linlinjava.litemall.db.service.ArticleDetailsService;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/articleDetails")
public class ArticleDetailsController {
    private Logger logger = Logger.getLogger(ArticleDetailsController.class);
    @Autowired
    private ArticleDetailsService articleDetailsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleService articleService;
    /**
     *@Author:lanye
     *@Description:添加用户浏览记录接口
     *@Date:13:44 2018/5/7
     */
    @PostMapping("add")
    public Object list(@RequestBody ArticleDetails articleDetails){
        if(articleDetails == null){
            return ResponseUtil.badArgument();
        }
        if(articleDetails.getUserId() == null){
            return ResponseUtil.unlogin();
        }
        /*if(articleDetails.getCategoryId() == null){
            return ResponseUtil.badArgument();
        }*/
        if(articleDetails.getArticleId() == null){
            return ResponseUtil.badArgument();
        }
        if(articleDetails.getNotesId() == null){
            return ResponseUtil.badArgument();
        }
        Article article = articleService.findById(articleDetails.getArticleId());
        articleDetails.setCategoryId(article.getCategoryId());
        articleDetails.setCategoryIds(article.getCategoryIds());

        List<ArticleDetails> articleDetailsList = articleDetailsService.selectList(articleDetails.getUserId(),articleDetails.getCategoryId(),articleDetails.getArticleId(),articleDetails.getNotesId(),article.getCategoryIds());
        if(articleDetailsList!=null && articleDetailsList.size()>0){
            return ResponseUtil.ok(articleDetails);
        }
        articleDetailsService.add(articleDetails);

        return ResponseUtil.ok(articleDetails);
    }

    /**
     *@Author:lanye
     *@Description:雷达图接口
     *@Date:14:50 2018/5/7
     */
    @GetMapping("list")
    public Object list(@RequestParam Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Map<String,Object> data = new HashMap<>();
        //该用户分类id定级
        Integer categoryIdMin = 0;
        Integer categoryIdMax = 0;
        StringBuffer sb = new StringBuffer();
        ArticleCategory articleCategory;
        List<ArticleCategory> articleCategoryList = articleCategoryService.queryByList(userId);
        String[] categoryNameArray = new String[articleCategoryList.size()];
        int[] readCountArray = new int[articleCategoryList.size()];
        int max = 0;
        int min = 9999;
        for(int i = 0;i<articleCategoryList.size();i++){
            categoryNameArray[i] = articleCategoryList.get(i).getName();
            readCountArray[i] = articleCategoryList.get(i).getAmount();//articleDetailsService.selectList(userId,articleCategoryList.get(i).getCategoryId(),null,null).size();
            if(readCountArray[i]>max){
                max = readCountArray[i];
                categoryIdMax = articleCategoryList.get(i).getCategoryId();
            }
            if(readCountArray[i]<=min && readCountArray[i]>0){
                categoryIdMin = articleCategoryList.get(i).getCategoryId();;
            }
        }

        data.put("categoryNameArray",categoryNameArray);
        data.put("readCountArray",readCountArray);
        data.put("content",sb);
        if(max == 0){
            max = 1;
        }else{
            articleCategory = articleCategoryService.findById(categoryIdMax);
            sb.append(articleCategory==null ? "":articleCategory.getMaxContent());
            if(categoryIdMax!=categoryIdMin){
                articleCategory = articleCategoryService.findById(categoryIdMin);
                sb.append(articleCategory==null ? "":articleCategory.getMinContent());
            }
        }
        data.put("max",max);

        return ResponseUtil.ok(data);
    }

    /**
     *@Author:lanye
     *@Description:修改接口
     *@Date:15:20 2018/5/7
     */
    @PostMapping("update")
    public Object save(@RequestParam Integer userId, @RequestBody ArticleDetails articleDetails) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(articleDetails == null){
            return ResponseUtil.badArgument();
        }

        articleDetailsService.update(articleDetails);

        return ResponseUtil.ok(articleDetails);
    }
}
