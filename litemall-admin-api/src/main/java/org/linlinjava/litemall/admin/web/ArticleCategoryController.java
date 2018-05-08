package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.ArticleCategory;
import org.linlinjava.litemall.db.service.ArticleCategoryService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/category")
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<ArticleCategory> categoryList = articleCategoryService.querySelective(name, page, limit, sort, order);
        int total = articleCategoryService.countSelective(name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", categoryList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleCategory category){
        articleCategoryService.add(category);
        return ResponseUtil.ok(category);
    }

    @GetMapping("/read")
    public Object read(Integer categoryId){
        if(categoryId == null){
            return ResponseUtil.badArgument();
        }

        ArticleCategory category = articleCategoryService.findById(categoryId);
        return ResponseUtil.ok(category);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleCategory category){
        articleCategoryService.updateById(category);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ArticleCategory category){
        articleCategoryService.deleteById(category.getCategoryId());
        return ResponseUtil.ok();
    }
}
