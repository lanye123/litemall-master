package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.domain.ArticleDetails;
import org.linlinjava.litemall.db.service.ArticleCategoryService;
import org.linlinjava.litemall.db.service.ArticleDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/articleDetails")
public class ArticleDetailsController {
    @Autowired
    private ArticleDetailsService articleDetailsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    /**
     *@Author:lanye
     *@Description:添加用户浏览记录接口
     *@Date:13:44 2018/5/7
     */
    @PostMapping("add")
    public Object list(@LoginUser Integer userId,@RequestBody ArticleDetails articleDetails){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(articleDetails == null){
            return ResponseUtil.badArgument();
        }
        if(articleDetails.getCategoryId() == null){
            return ResponseUtil.badArgument();
        }
        if(articleDetails.getArticleId() == null){
            return ResponseUtil.badArgument();
        }
        if(articleDetails.getNotesId() == null){
            return ResponseUtil.badArgument();
        }

        articleDetails.setUserId(userId);
        List<ArticleDetails> articleDetailsList = articleDetailsService.selectList(userId,articleDetails.getCategoryId(),articleDetails.getArticleId(),articleDetails.getNotesId());
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
    public Object list(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        Map<String,Object> data = new HashMap<>();
       /* List<ArticleDetails> articleDetailsList = articleDetailsService.selectList(userId,null,null,null);
        if(articleDetailsList.size() == 0){
            data.put("count",0);
            return ResponseUtil.ok(data);
        }*/
        List<ArticleCategory> articleCategoryList = articleCategoryService.queryAllList();
        String[] categoryNameArray = new String[articleCategoryList.size()];
        int[] readCountArray = new int[articleCategoryList.size()];
        int max = 0;
        for(int i = 0;i<articleCategoryList.size();i++){
            categoryNameArray[i] = articleCategoryList.get(i).getName();
            readCountArray[i] = articleDetailsService.selectList(userId,articleCategoryList.get(i).getCategoryId(),null,null).size();
            if(readCountArray[i]>max){
                max = readCountArray[i];
            }
        }

        data.put("categoryNameArray",categoryNameArray);
        data.put("readCountArray",readCountArray);
        if(max == 0){
            max = 1;
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
    public Object save(@LoginUser Integer userId, @RequestBody ArticleDetails articleDetails) {
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
