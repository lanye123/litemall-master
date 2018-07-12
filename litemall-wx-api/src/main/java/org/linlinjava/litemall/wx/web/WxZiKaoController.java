package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/zikao")
public class WxZiKaoController {

    @Autowired
    private ZkAreaService zkAreaService;
    @Autowired
    private ZkTikuService zkTikuService;
    @Autowired
    private LitemallAdService adService;
    @Autowired
    private ZkZhuanyeService zkZhuanyeService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private ZkTikuDetailService zkTikuDetailService;

    /**
      * @author lanye
      * @Description 获取自考区域信息
      * @Date 2018/7/5 10:42
      * @Param []
      * @return java.lang.Object
      **/
    @GetMapping("/area")
    public Object getArea(){
        Map<String, Object> data = new HashMap<>();
        List<LitemallAd> banner = adService.queryByApid(2);
        List<ZkArea> zkAreaList = zkAreaService.queryAll();
        data.put("banner", banner);
        data.put("zkAreaList", zkAreaList);
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 获取所有自考专业信息
      * @Date 2018/7/5 14:47
      * @Param []
      * @return java.lang.Object
      **/
    @GetMapping("/zhuanye")
    public Object getZhuanye(){
        return ResponseUtil.ok(zkZhuanyeService.queryAll());
    }

    /**
      * @author lanye
      * @Description 获取自考题库列表
      * @Date 2018/7/5 10:45
      * @Param [zyId]
      * @return java.lang.Object
      **/
    @GetMapping("/tikuList")
    public Object getTikuList(String zyId,Integer userId){
        if(userId==null){
            return ResponseUtil.badArgumentValue();
        }
        Map<String, Object> data = new HashMap<>();
        List<ZkTiku> zkTikuList = zkTikuService.queryByZyId(zyId,userId);
        for(ZkTiku zkTiku:zkTikuList){
            //当用户参与过课程的拼团
            zkTiku.setStatus(zkTikuDetailService.queryByPid(zkTiku.getPid(),zkTiku.getId()).size());
        }
        data.put("zkTikuList", zkTikuList);
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 拼团详情
      * @Date 2018/7/5 16:35
      * @Param [tkId, pid]
      * @return java.lang.Object
      **/
    @GetMapping("/detailList")
    public Object getDetailList(Integer tkId,Integer pid){
        if(tkId==null){
            return ResponseUtil.badArgumentValue();
        }
        if(pid==null){
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(zkTikuDetailService.queryByPid(pid,tkId));
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

    /**
      * @author lanye
      * @Description 创建团流水
      * @Date 2018/7/5 14:52
      * @Param [zkTikuDetail]
      * @return java.lang.Object
      **/
    @PostMapping("/detailAdd")
    public Object createDetail(@RequestBody ZkTikuDetail zkTikuDetail){
        if(zkTikuDetail==null){
            return ResponseUtil.badArgument();
        }
        if(StringUtils.isEmpty(zkTikuDetail.getUserId()) || StringUtils.isEmpty(zkTikuDetail.getTkId()) || StringUtils.isEmpty(zkTikuDetail.getPid()) || StringUtils.isEmpty(zkTikuDetail.getFlag())){
            return ResponseUtil.badArgument();
        }
        LitemallUser user = litemallUserService.findById(zkTikuDetail.getUserId());
        if(user==null){
            return ResponseUtil.badArgument();
        }
        zkTikuDetail.setAvatar(user.getAvatar());
        if(zkTikuDetailService.countSeletive(zkTikuDetail.getTkId(),zkTikuDetail.getUserId(),zkTikuDetail.getPid(),null,null,null,null,"","")==0){
            zkTikuDetailService.add(zkTikuDetail);
        }
        return ResponseUtil.ok(zkTikuDetail);
    }
}