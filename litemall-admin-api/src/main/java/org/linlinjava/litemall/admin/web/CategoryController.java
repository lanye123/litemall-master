package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallCategory;
import org.linlinjava.litemall.db.service.LitemallCategoryService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    private final Log logger = LogFactory.getLog(CategoryController.class);

    @Autowired
    private LitemallCategoryService categoryService;

    @GetMapping("/list")
    public Object list(
                       String id, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallCategory> collectList = categoryService.querySelective(id, name, page, limit, sort, order);
        int total = categoryService.countSelective(id, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collectList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallCategory category){

        categoryService.add(category);
        return ResponseUtil.ok();
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallCategory category = categoryService.findById(id);
        return ResponseUtil.ok(category);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallCategory category){

        categoryService.updateById(category);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallCategory category){

        categoryService.deleteById(category.getId());
        return ResponseUtil.ok();
    }

    @GetMapping("/l1")
    public Object catL1() {
        // 所有一级分类目录
        List<LitemallCategory> l1CatList = categoryService.queryL1();
        HashMap<Integer, String> data = new HashMap<>(l1CatList.size());
        for(LitemallCategory category : l1CatList){
            data.put(category.getId(), category.getName());
        }
        return ResponseUtil.ok(data);
    }

}
