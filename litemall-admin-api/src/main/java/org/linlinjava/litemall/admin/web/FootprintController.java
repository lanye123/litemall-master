package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallFootprint;
import org.linlinjava.litemall.db.service.LitemallFootprintService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/footprint")
public class FootprintController {
    private final Log logger = LogFactory.getLog(FootprintController.class);

    @Autowired
    private LitemallFootprintService footprintService;

    @GetMapping("/list")
    public Object list(
                       String userId, String goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallFootprint> footprintList = footprintService.querySelective(userId, goodsId, page, limit, sort, order);
        int total = footprintService.countSelective(userId, goodsId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallFootprint footprint){

        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallFootprint footprint = footprintService.findById(id);
        return ResponseUtil.ok(footprint);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallFootprint footprint){

        footprintService.updateById(footprint);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallFootprint footprint){

        footprintService.deleteById(footprint.getId());
        return ResponseUtil.ok();
    }

}
