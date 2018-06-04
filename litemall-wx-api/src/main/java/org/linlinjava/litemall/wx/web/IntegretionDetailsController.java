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
        Integer days=0;
        Integer j=0;
        IntegretionDetail idetail=new IntegretionDetail();
        idetail.setUserId(userId);
        List<IntegretionDetail> integretionDetailList=integretionDetailService.queryByLimit(userId);

        if(integretionDetailList!=null&&integretionDetailList.size()>0){
            for (int x = 0; x < integretionDetailList.size(); x++) {
                IntegretionDetail detail=integretionDetailList.get(x);
                //System.out.println(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i)));
                //getDateDiff获取两个日期之间的间隔数，两个日期相等或者间隔一天则为0，间隔2天则为1
                //localToDate将LocalDateTime转换为Date类型
                //subDays获取当前日期前n天的日期，n为数字
                if(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(x))==0&&detail.getAmount()==5){
                    j++;
                }else
                    break;
            }


            //自动打卡接口如果用户当天未打卡点击打卡后自动打卡
            IntegretionDetail id=integretionDetailList.get(0);
            System.out.println(DateUtils.compareDate(DateUtils.localToDate(id.getCreateDate()),DateUtils.getCurrentDate(),5));
            if(DateUtils.compareDate(DateUtils.localToDate(id.getCreateDate()),DateUtils.getCurrentDate(),5)<0){
                if(j==6){
                    idetail.setAmount(15);
                    idetail.setStatus((byte) 1);
                }else{
                    idetail.setAmount(5);
                }
                integretionDetailService.add(idetail);
            }
        }else{
            data.put("days",1);//默认进入显示一天
        }

        List<IntegretionDetail> integretionDetailList1=integretionDetailService.queryByLimit(userId);

        if(integretionDetailList1!=null&&integretionDetailList1.size()>0) {
            for (int i = 0; i < integretionDetailList1.size(); i++) {
                IntegretionDetail detail2 = integretionDetailList1.get(i);
                //System.out.println(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i)));
                //getDateDiff获取两个日期之间的间隔数，两个日期相等或者间隔一天则为0，间隔2天则为1
                //localToDate将LocalDateTime转换为Date类型
                //subDays获取当前日期前n天的日期，n为数字
                if (DateUtils.getDateDiff(DateUtils.localToDate(detail2.getCreateDate()), DateUtils.subDays(i)) == 0 && detail2.getAmount() == 5) {
                    days++;
                } else
                    break;
            }
            data.put("days", days);
        }
        Integer grade=integretionDetailService.sumByUserid(userId);
        data.put("grade",grade);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody IntegretionDetail integretionDetail) {
        Integer j=0;
        List<IntegretionDetail> integretionDetailList=integretionDetailService.queryByLimit(integretionDetail.getUserId());
        if(integretionDetailList!=null&&integretionDetailList.size()>0){
            for (int i = 0; i < integretionDetailList.size(); i++) {
                IntegretionDetail detail=integretionDetailList.get(i);
                System.out.println(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i)));
                if(DateUtils.getDateDiff(DateUtils.localToDate(detail.getCreateDate()),DateUtils.subDays(i))==0&&detail.getAmount()==5){
                    j++;
                }else
                    break;
            }
        }
        //如果用户连续签到第七天则获得15积分否则获取5积分
        if(j==6){
            integretionDetail.setAmount(15);
            integretionDetail.setStatus((byte) 1);
        }else{
            integretionDetail.setAmount(5);
        }
        integretionDetailService.add(integretionDetail);
        return ResponseUtil.ok(integretionDetail);
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
