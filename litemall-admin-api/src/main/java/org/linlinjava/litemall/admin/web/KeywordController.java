package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallKeyword;
import org.linlinjava.litemall.db.service.LitemallKeywordService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/keyword")
public class KeywordController {
    private final Log logger = LogFactory.getLog(KeywordController.class);

    @Autowired
    private LitemallKeywordService keywordService;

    @GetMapping("/list")
    public Object list(
                       String keyword, String url,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallKeyword> brandList = keywordService.querySelective(keyword, url, page, limit, sort, order);
        int total = keywordService.countSelective(keyword, url, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", brandList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallKeyword keywords){

        keywordService.add(keywords);
        return ResponseUtil.ok(keywords);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallKeyword brand = keywordService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallKeyword keywords){

        keywordService.updateById(keywords);
        return ResponseUtil.ok(keywords);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallKeyword brand){

        keywordService.deleteById(brand.getId());
        return ResponseUtil.ok();
    }

}
