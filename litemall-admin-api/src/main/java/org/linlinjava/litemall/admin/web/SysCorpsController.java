package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.SysCorps;
import org.linlinjava.litemall.db.service.SysCorpsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sysCorps")
public class SysCorpsController {
    private final Log logger = LogFactory.getLog(SysCorpsController.class);

    @Autowired
    private SysCorpsService sysCorpsService;

    @GetMapping("/list")
    public Object list(String name, Integer userId, Integer buId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<SysCorps> sysCorpsList = sysCorpsService.querySelective(name, userId, buId,page, limit, sort, order);
        int total = sysCorpsService.countSeletive(name, userId,buId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", sysCorpsList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/buId")
    public Object getBuByDeptId(Integer buId){
        if(StringUtils.isEmpty(buId)){
            return ResponseUtil.fail402();
        }
        return ResponseUtil.ok(sysCorpsService.queryByBuId(buId));
    }

    @PostMapping("/create")
    public Object create(@RequestBody SysCorps sysCorps){
        logger.debug(sysCorps);

        sysCorpsService.add(sysCorps);
        return ResponseUtil.ok(sysCorps);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysCorps sysCorps){
        logger.debug(sysCorps);

        sysCorpsService.update(sysCorps);
        return ResponseUtil.ok(sysCorps);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){
        sysCorpsService.deleteById(id);
        return ResponseUtil.ok();
    }
}
