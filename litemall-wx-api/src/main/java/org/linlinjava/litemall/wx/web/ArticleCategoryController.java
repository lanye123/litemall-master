package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.service.ArticleCategoryService;
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
@RequestMapping("/wx/category")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;
    /**
    *@Author:LeiQiang
    *@Description:全部图文-分类列表接口
    *@Date:22:23 2018/5/4
    */
    @GetMapping("list")
    public Object list(){
        /*if(userId == null){
            return ResponseUtil.unlogin();
        }*/
        List<ArticleCategory> categoryList=articleCategoryService.queryAllList();
        List<Map<String, Object>> categoryVo = new ArrayList<>(categoryList.size());
        for (ArticleCategory category:categoryList){
            Map<String, Object> a = new HashMap<>();
            a.put("category_id",category.getCategoryId());
            a.put("name",category.getName());
            //a.put("status",category.getStatus());
            categoryVo.add(a);
        }
        return ResponseUtil.ok(categoryVo);
    }
}
