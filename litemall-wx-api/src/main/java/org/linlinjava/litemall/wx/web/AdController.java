package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.LitemallAd;
import org.linlinjava.litemall.db.service.LitemallAdService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wx/ad")
public class AdController {
    @Autowired
    private LitemallAdService litemallAdService;

    @GetMapping("/ad")
    public Object home(String userId,Integer type) {
        Map<String, Object> data = new HashMap<>();
        //查询萤火虫的广告flag=0
        List<LitemallAd> banner = litemallAdService.queryByApid(type);
        data.put("banner", banner);
        return ResponseUtil.ok(data);
    }

}
