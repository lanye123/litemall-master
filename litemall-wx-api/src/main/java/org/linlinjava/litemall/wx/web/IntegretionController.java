package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.Integretion;
import org.linlinjava.litemall.db.service.IntegretionService;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/integretion")
public class IntegretionController {
    @Resource
    private IntegretionService integretionService;

    @GetMapping("/list")
    public Object list(@RequestBody Integretion integretion){
        Map<String,Object> data = new HashMap<>();
        List<Integretion> integretionList=integretionService.querySelective(integretion.getUserId());
        for (Integretion a:integretionList)
        {

        }
        return ResponseUtil.ok();

    }

    @PostMapping("/create")
    public Object create(@RequestBody Integretion integretion){

        List<Integretion> integretionList=integretionService.querySelective2(integretion.getUserId(),DateUtils.getDayStartString(),DateUtils.getDayEndString());
        return ResponseUtil.ok(integretion);
    }

    @PostMapping("update")
    public Object update(@RequestBody Integretion integretion){
        return ResponseUtil.ok(integretion);
    }
}
