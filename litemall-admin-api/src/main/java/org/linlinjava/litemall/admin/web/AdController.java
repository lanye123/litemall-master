package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallAd;
import org.linlinjava.litemall.db.service.LitemallAdService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/ad")
public class AdController {
    private final Log logger = LogFactory.getLog(AdController.class);

    @Autowired
    private LitemallAdService adService;

    @GetMapping("/list")
    public Object list(
                       String name, String content,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        List<LitemallAd> adList = adService.querySelective(name, content, page, limit, sort, order);
        int total = adService.countSelective(name, content, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody LitemallAd ad){
        adService.add(ad);
        return ResponseUtil.ok(ad);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallAd brand = adService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallAd ad){

        adService.updateById(ad);
        return ResponseUtil.ok(ad);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallAd ad){

        adService.deleteById(ad.getId());
        return ResponseUtil.ok();
    }

}
