package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.SysTransition;
import org.linlinjava.litemall.db.service.SysTransitionService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sysTransition")
public class SysTransitionController {
    private final Log logger = LogFactory.getLog(SysTransitionController.class);

    @Autowired
    private SysTransitionService sysTransitionService;

    @GetMapping("/list")
    public Object list(String name, Integer userId, Integer corpsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<SysTransition> sysTransitionList = sysTransitionService.querySelective(name, userId, corpsId,page, limit, sort, order);
        int total = sysTransitionService.countSeletive(name, userId,corpsId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", sysTransitionList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/corpsId")
    public Object getBuByCorpsId(Integer corpsId){
        if(StringUtils.isEmpty(corpsId)){
            return ResponseUtil.fail402();
        }
        return ResponseUtil.ok(sysTransitionService.queryByCorpsId(corpsId));
    }

    @PostMapping("/create")
    public Object create(@RequestBody SysTransition sysTransition){
        logger.debug(sysTransition);

        sysTransitionService.add(sysTransition);
        return ResponseUtil.ok(sysTransition);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysTransition sysTransition){
        logger.debug(sysTransition);

        sysTransitionService.update(sysTransition);
        return ResponseUtil.ok(sysTransition);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){
        sysTransitionService.deleteById(id);
        return ResponseUtil.ok();
    }
}
