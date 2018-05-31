package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Integretion;
import org.linlinjava.litemall.db.domain.IntegretionDetail;
import org.linlinjava.litemall.db.service.IntegretionDetailService;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 积分连续签到管理
 * @author leiqiang
 * @date 2018年5月30日13:59:48
 */
@RestController
@RequestMapping("/wx/integretiondetail")
public class IntegretionDetailsController {
    @Resource
    private IntegretionDetailService integretionDetailService;

    @RequestMapping("/list")
    public Object list(String userId){
        Map<String,Object> data = new HashMap<>();
        List<IntegretionDetail> integretionDetailList=integretionDetailService.queryByLimit(userId);
        if(integretionDetailList!=null&&integretionDetailList.size()>0){
            for (int i = 0; i < integretionDetailList.size(); i++) {
                IntegretionDetail detail=integretionDetailList.get(i);
                //System.out.println(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i)));
                //getDateDiff获取两个日期之间的间隔数，两个日期相等或者间隔一天则为0，间隔2天则为1
                //localToDate将LocalDateTime转换为Date类型
                //subDays获取当前日期前n天的日期，n为数字
                if(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i))==0){
                    data.put("day"+i,1);
                }
            }
        }
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody IntegretionDetail integretionDetail) {
        Map<String,Object> data = new HashMap<>();
        Integer j=0;
        List<IntegretionDetail> integretionDetailList=integretionDetailService.queryByLimit(integretionDetail.getUserId());
        if(integretionDetailList!=null&&integretionDetailList.size()>0){
            for (int i = 0; i < integretionDetailList.size(); i++) {
                IntegretionDetail detail=integretionDetailList.get(i);
                System.out.println(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i)));
                if(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i))==0){
                    j++;
                }
            }
        }
        //如果用户连续签到第七天则获得15积分否则获取5积分
        if(j==6){

            integretionDetail.setAmount(15);
            integretionDetail.setStatus((byte) 1);
        }else{
            integretionDetail.setAmount(5);
        }
        return ResponseUtil.ok(data);
    }

    @PostMapping("/add")
    public Object add(@RequestBody IntegretionDetail integretionDetail) {
        if(integretionDetail==null){
            return ResponseUtil.badArgument();
        }
        if(integretionDetail.getAmount()==null){
            return ResponseUtil.badArgument();
        }
        if(integretionDetail.getType()==null){
            return ResponseUtil.badArgument();
        }
        if(integretionDetail.getUserId()==null){
            return ResponseUtil.badArgument();
        }
        if(integretionDetailService.countSeletive(integretionDetail.getUserId(),integretionDetail.getType().intValue())>0){
            return ResponseUtil.ok();
        }
        integretionDetailService.add(integretionDetail);
        return ResponseUtil.ok(integretionDetail);
    }

    @PostMapping("update")
    public Object update(@RequestBody Integretion integretion){
        return ResponseUtil.ok(integretion);
    }

    public static void main(String[] args){

        System.out.println(DateUtils.subDays(1));

    }
}
