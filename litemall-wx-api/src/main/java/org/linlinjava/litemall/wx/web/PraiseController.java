package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Praise;
import org.linlinjava.litemall.db.service.PraiseService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wx/praise")
public class PraiseController {
    @Autowired
    private PraiseService praiseService;

    /**
      * @author lanye
      * @Description 文章点赞
      * @Date 2018/5/30 9:43
      * @Param [medalDetails]
      * @return java.lang.Object
      **/
    @PostMapping("add")
    public Object add(@RequestBody Praise praise){
        if(praise == null){
            return ResponseUtil.badArgument();
        }
        praiseService.add(praise);
        return ResponseUtil.ok(praise);
    }

    /**
      * @author lanye
      * @Description 更新点赞状态
      * @Date 2018/5/30 9:45
      * @Param [praise]
      * @return java.lang.Object
      **/
    @PostMapping("update")
    public Object update(@RequestBody Praise praise){
        if(praise == null){
            return ResponseUtil.badArgument();
        }
        praiseService.update(praise);
        return ResponseUtil.ok(praise);
    }

}
