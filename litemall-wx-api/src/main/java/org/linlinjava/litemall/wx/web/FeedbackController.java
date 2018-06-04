package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Feedback;
import org.linlinjava.litemall.db.service.FeedbackService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.db.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wx/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**
      * @author lanye
      * @Description 我的反馈
      * @Date 2018/5/31 16:07
      * @Param [userId]
      * @return java.lang.Object
      **/
    @GetMapping("list")
    public Object list(Integer userId){
        if(userId == null){
            return ResponseUtil.badArgument();
        }
        return ResponseUtil.ok(feedbackService.queryBySelective("","",null,userId,null,null,"",""));
    }

    /**
      * @author lanye
      * @Description 保存反馈
      * @Date 2018/5/31 16:09
      * @Param [feedback]
      * @return java.lang.Object
      **/
    @PostMapping("add")
    public Object add(@RequestBody Feedback feedback){
        if(feedback == null){
            return ResponseUtil.badArgument();
        }
        if(ValidateUtils.validateStr(feedback.getContent())||ValidateUtils.validateStr(feedback.getTitle())){
            return ResponseUtil.fail(500,"暂不支持特殊字符哦，请重新输入吧(*^__^*) 嘻嘻……");
        }
        feedbackService.add(feedback);
        return ResponseUtil.ok();
    }

}
