package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.WxFormid;
import org.linlinjava.litemall.db.service.WxFormidService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/formid")
public class WxFormidController {
    @Autowired
    private WxFormidService wxFormidService;

    @PostMapping("add")
    public Object add(String form_id){
        wxFormidService.add(form_id);
        return ResponseUtil.ok();
    };


    public void delete(){
        List<WxFormid> formidList=wxFormidService.queryByStatus(1);
        for(WxFormid formid:formidList){
            wxFormidService.delete(formid.getId());
        }
    }
}
