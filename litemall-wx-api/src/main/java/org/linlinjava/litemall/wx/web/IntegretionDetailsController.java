package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Integretion;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.service.IntegretionDetailService;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分连续签到管理
 */
@RestController
@RequestMapping("/wx/integretionDetails")
public class IntegretionDetailsController {
    @Resource
    private IntegretionDetailService integretionDetailService;

    @GetMapping("/list")
    public Object list(@RequestBody IntegretionDetail integretionDetail){
        Map<String,Object> data = new HashMap<>();
        List<IntegretionDetail> integretionDetailList=integretionDetailService.querySelective(integretionDetail.getUserId());
        IntegretionDetail a=new IntegretionDetail();
        for (IntegretionDetail detail:integretionDetailList)
        {
            System.out.println(DateUtils.compareDate(DateUtils.localToDate(detail.getCreateDate()),DateUtils.dateFormat(new Date()),2));
            integretionDetailService.days(integretionDetail);//
        }
        return ResponseUtil.ok();

    }

    @PostMapping("/create")
    public Object create(@RequestBody IntegretionDetail integretionDetail) {
        Map<String, Object> data = new HashMap<>();
        /*IntegretionDetail detail = integretionDetailService.querySelective2(integretionDetail.getUserId(), DateUtils., Integer.valueOf(integretionDetail.getType()));
        if (detail == null) {
            integretionDetailService.add(integretionDetail);
            data.put("msg", "签到成功!");
        } else
            data.put("msg", "今天已签到，请明天再来哦!");*/
        return ResponseUtil.ok(data);
    }

    @PostMapping("update")
    public Object update(@RequestBody Integretion integretion){
        return ResponseUtil.ok(integretion);
    }

    public static void main(String[] args){

        System.out.println(DateUtils.dateFormat(new Date()));

    }
}
