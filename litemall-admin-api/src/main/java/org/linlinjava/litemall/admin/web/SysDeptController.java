package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.SysDept;
import org.linlinjava.litemall.db.service.SysDeptService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sysDept")
public class SysDeptController {
    private final Log logger = LogFactory.getLog(SysDeptController.class);

    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    public Object list(String name, Integer userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit2", defaultValue = "10") Integer limit2,
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<SysDept> sysDeptList = sysDeptService.querySelective(name, userId,page, limit2, sort, order);
        int total = sysDeptService.countSeletive(name, userId,page, limit2, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", sysDeptList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/all")
    public Object queryAll(){
        Map<String, Object> data = new HashMap<>();
        List<SysDept> deptList=sysDeptService.queryAll();
        data.put("items",deptList);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody SysDept sysDept){
        logger.debug(sysDept);

        sysDeptService.add(sysDept);
        return ResponseUtil.ok(sysDept);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysDept sysDept){
        logger.debug(sysDept);

        sysDeptService.update(sysDept);
        return ResponseUtil.ok(sysDept);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){
        sysDeptService.deleteById(id);
        return ResponseUtil.ok();
    }
}
