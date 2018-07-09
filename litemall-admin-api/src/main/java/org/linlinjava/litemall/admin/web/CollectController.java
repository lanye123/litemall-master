package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallCollect;
import org.linlinjava.litemall.db.service.LitemallCollectService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/collect")
public class CollectController {
    private final Log logger = LogFactory.getLog(CollectController.class);

    @Autowired
    private LitemallCollectService collectService;

    @GetMapping("/list")
    public Object list(
                       String userId, String valueId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallCollect> collectList = collectService.querySelective(userId, valueId, page, limit, sort, order);
        int total = collectService.countSelective(userId, valueId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", collectList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallCollect collect){

        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallCollect collect = collectService.findById(id);
        return ResponseUtil.ok(collect);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallCollect collect){

        collectService.updateById(collect);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallCollect collect){

        collectService.deleteById(collect.getId());
        return ResponseUtil.ok();
    }

}
