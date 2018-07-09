package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.db.domain.LitemallIssue;
import org.linlinjava.litemall.db.service.LitemallIssueService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/issue")
public class IssueController {
    private final Log logger = LogFactory.getLog(IssueController.class);

    @Autowired
    private LitemallIssueService issueService;

    @GetMapping("/list")
    public Object list(
                       String question,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallIssue> issueList = issueService.querySelective(question, page, limit, sort, order);
        int total = issueService.countSelective(question, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", issueList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallIssue brand){

        issueService.add(brand);
        return ResponseUtil.ok(brand);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallIssue brand = issueService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallIssue brand){

        issueService.updateById(brand);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallIssue brand){

        issueService.deleteById(brand.getId());
        return ResponseUtil.ok();
    }

}
