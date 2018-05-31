package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.Feedback;
import org.linlinjava.litemall.db.service.FeedbackService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/list")
    public Object list(String title,String content,Integer type,Integer userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Feedback> feedbackList = feedbackService.queryBySelective(title,content,type,userId, page, limit, sort, order);
        int total = feedbackService.countSelective(title,content,type,userId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", feedbackList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Feedback feedback){
        feedbackService.update(feedback);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Feedback feedback){
        feedbackService.deleteById(feedback.getId());
        return ResponseUtil.ok();
    }
}
