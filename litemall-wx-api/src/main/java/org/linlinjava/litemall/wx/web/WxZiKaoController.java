package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.service.ZkAreaService;
import org.linlinjava.litemall.db.service.ZkTikuService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/wx/zikao")
public class WxZiKaoController {

    @Autowired
    private ZkAreaService zkAreaService;
    @Autowired
    private ZkTikuService zkTikuService;

    /**
      * @author lanye
      * @Description 获取自考区域信息
      * @Date 2018/7/5 10:42
      * @Param []
      * @return java.lang.Object
      **/
    @GetMapping("/area")
    public Object getArea(){
        return ResponseUtil.ok(zkAreaService.queryAll());
    }

    /**
      * @author lanye
      * @Description 获取自考题库列表
      * @Date 2018/7/5 10:45
      * @Param [zyId]
      * @return java.lang.Object
      **/
    @GetMapping("/tikuList")
    public Object getTikuList(String zyId){
        return ResponseUtil.ok(zkTikuService.queryByZyId(zyId));
    }

    /**
      * @author lanye
      * @Description 获取自考题库详情
      * @Date 2018/7/5 10:46
      * @Param [id]
      * @return java.lang.Object
      **/
    @GetMapping("/tiku")
    public Object getTiku(Integer id){
        return ResponseUtil.ok(zkTikuService.findById(id));
    }
}