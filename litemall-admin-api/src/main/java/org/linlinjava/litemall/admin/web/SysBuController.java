package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.SysBu;
import org.linlinjava.litemall.db.service.SysBuService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sysBu")
public class SysBuController {
    private final Log logger = LogFactory.getLog(SysBuController.class);

    @Autowired
    private SysBuService sysBuService;

    @GetMapping("/list")
    public Object list(String name, Integer userId, Integer deptId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<SysBu> sysBuList = sysBuService.querySelective(name, userId, deptId,page, limit, sort, order);
        int total = sysBuService.countSeletive(name, userId,deptId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", sysBuList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/deptId")
    public Object getBuByDeptId(Integer deptId){
        if(StringUtils.isEmpty(deptId)){
            return ResponseUtil.fail402();
        }
        return ResponseUtil.ok(sysBuService.queryByDeptId(deptId));
    }

    @PostMapping("/create")
    public Object create(@RequestBody SysBu sysBu){
        logger.debug(sysBu);

        sysBuService.add(sysBu);
        return ResponseUtil.ok(sysBu);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysBu sysBu){
        logger.debug(sysBu);

        sysBuService.update(sysBu);
        return ResponseUtil.ok(sysBu);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){
        sysBuService.deleteById(id);
        return ResponseUtil.ok();
    }
}
