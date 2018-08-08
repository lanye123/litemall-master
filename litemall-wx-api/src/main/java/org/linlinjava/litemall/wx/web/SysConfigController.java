package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.service.SysConfigService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/sysConfig")
public class SysConfigController {
    private final Log logger = LogFactory.getLog(SysConfigController.class);

    @Resource
    private SysConfigService sysconfigService;


    @GetMapping("/code")
    public Object getUserInfo(String code){
        return ResponseUtil.ok(sysconfigService.queryByCode(code));
    }

}