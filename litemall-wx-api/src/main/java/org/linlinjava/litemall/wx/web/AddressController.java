package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.LitemallAddressService;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wx/userAddress")
public class AddressController {
    @Autowired
    private LitemallAddressService litemallAddressService;
    @Autowired
    private LitemallRegionService litemallRegionService;
    /**
      * @author lanye
      * @Description 根据上级id获取地址信息
      * @Date 2018/5/28 14:49
      * @Param [pid]
      * @return java.lang.Object
      **/
    @GetMapping("region")
    public Object getRegion(Integer pid){
        return ResponseUtil.ok(litemallRegionService.queryByPid(pid));
    }

    /**
      * @author lanye
      * @Description 获取用户收货地址信息
      * @Date 2018/5/28 15:02
      * @Param [userId]
      * @return java.lang.Object
      **/
    @GetMapping("list")
    public Object list(Integer userId){
        if(userId == null){
            return ResponseUtil.badArgument();
        }
        return ResponseUtil.ok(litemallAddressService.queryByUid(userId));
    }

    /**
      * @author lanye
      * @Description 添加用户地址
      * @Date 2018/5/28 15:32
      * @Param [litemallAddress]
      * @return java.lang.Object
      **/
    @PostMapping("add")
    public Object add(@RequestBody LitemallAddress litemallAddress){
        if(litemallAddress == null){
            return ResponseUtil.badArgument();
        }
        if(litemallAddress.getUserId() == null){
            return ResponseUtil.badArgument();
        }
        List<LitemallAddress> litemallAddressList = litemallAddressService.queryByUid(litemallAddress.getUserId());
        if(litemallAddressList!=null && litemallAddressList.size()>0){
            LitemallAddress litemallAddressDb = litemallAddressList.get(0);
            litemallAddressDb.setAddress(litemallAddress.getAddress());
            litemallAddressDb.setMobile(litemallAddress.getMobile());
            litemallAddressDb.setName(litemallAddress.getName());
            return ResponseUtil.ok(litemallAddressService.update(litemallAddressDb));
        }else{
            return ResponseUtil.ok(litemallAddressService.add(litemallAddress));
        }
    }

    /**
      * @author lanye
      * @Description 修改地址
      * @Date 2018/5/28 15:06
      * @Param [litemallAddress]
      * @return java.lang.Object
      **/
    @PostMapping("update")
    public Object update(@RequestBody LitemallAddress litemallAddress){
        if(litemallAddress == null){
            return ResponseUtil.badArgument();
        }
        litemallAddressService.updateById(litemallAddress);
        return ResponseUtil.ok();
    }

    /**
      * @author lanye
      * @Description 删除地址
      * @Date 2018/5/28 15:06
      * @Param [id]
      * @return java.lang.Object
      **/
    @PostMapping("delete")
    public Object delete(Integer id){
        if(id == null){
            return ResponseUtil.badArgument();
        }
        litemallAddressService.delete(id);
        return ResponseUtil.ok();
    }
}
