package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.service.MedalService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/medal")
public class MedalController {
    private final Log logger = LogFactory.getLog(MedalController.class);

    @Autowired
    private MedalService medalService;

    @GetMapping("/list")
    public Object list(String name,Integer max,Integer min,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Medal> medalList = medalService.querySelective(name, max, min,page, limit, sort, order);
        int total = medalService.countSeletive(name, max, min,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("medals", medalList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Medal medal){
        logger.debug(medal);

        medalService.add(medal);
        return ResponseUtil.ok(medal);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Medal medal){
        logger.debug(medal);

        medalService.update(medal);
        return ResponseUtil.ok(medal);
    }

    @PostMapping("/hidden")
    public Object hidden(Integer id){

        medalService.hidden(id);
        return ResponseUtil.ok(id);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){

        medalService.deleteById(id);
        return ResponseUtil.ok(id);
    }
}
