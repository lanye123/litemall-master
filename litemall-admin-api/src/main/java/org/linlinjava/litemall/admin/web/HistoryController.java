package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallSearchHistory;
import org.linlinjava.litemall.db.service.LitemallSearchHistoryService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/history")
public class HistoryController {
    private final Log logger = LogFactory.getLog(HistoryController.class);

    @Autowired
    private LitemallSearchHistoryService searchHistoryService;

    @GetMapping("/list")
    public Object list(
                       String userId, String keyword,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallSearchHistory> footprintList = searchHistoryService.querySelective(userId, keyword, page, limit, sort, order);
        int total = searchHistoryService.countSelective(userId, keyword, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallSearchHistory history){
        return ResponseUtil.fail501();
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallSearchHistory history = searchHistoryService.findById(id);
        return ResponseUtil.ok(history);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallSearchHistory history){

        searchHistoryService.updateById(history);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallSearchHistory history){

        searchHistoryService.deleteById(history.getId());
        return ResponseUtil.ok();
    }

}
