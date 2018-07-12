package org.linlinjava.litemall.admin.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.admin.service.AdminTokenManager;
import org.linlinjava.litemall.admin.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.admin.util.constants.Constants;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.SysRolePermissionService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/admin")
public class AdminController {
  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private LitemallAdminService adminService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @GetMapping("/info")
    public Object info(String token){
        Integer adminId = AdminTokenManager.getUserId(token);
        LitemallAdmin admin = adminService.findById(adminId);
        if(admin == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        // 目前roles不支持，这里简单设置admin
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        data.put("roles", roles);
        data.put("introduction", "admin introduction");
        return ResponseUtil.ok(data);
    }

    @GetMapping("/list")
    public Object list(
                       String username,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){


        List<LitemallAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
        int total = adminService.countSelective(username, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adminList);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create( @RequestBody LitemallAdmin admin){


        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);

        adminService.add(admin);
        return ResponseUtil.ok(admin);
    }

    @GetMapping("/read")
    public Object read( Integer id){


        if(id == null){
            return ResponseUtil.badArgument();
        }

        LitemallAdmin admin = adminService.findById(id);
        return ResponseUtil.ok(admin);
    }

    @PostMapping("/update")
    public Object update( @RequestBody LitemallAdmin admin){


        Integer anotherAdminId = admin.getId();
        if(anotherAdminId.intValue() == 1){
            return ResponseUtil.fail(403, "超级管理员不能修改");
        }

        adminService.updateById(admin);
        return ResponseUtil.ok(admin);
    }

    @PostMapping("/delete")
    public Object delete( @RequestBody LitemallAdmin admin){
        Integer anotherAdminId = admin.getId();
        if(anotherAdminId.intValue() == 1){
            return ResponseUtil.fail(403, "超级管理员不能删除");
        }
        adminService.deleteById(anotherAdminId);
        return ResponseUtil.ok();
    }
    @PostMapping("getInfo")
    public Object getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        LitemallAdmin userInfo = (LitemallAdmin) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getUsername();
        List list = sysRolePermissionService.getUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, list.get(0));
        return ResponseUtil.ok(list.get(0));
    }

    @PostMapping("getUserInfo")
    public Object getUserInfo(@Valid String username) {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        List list = sysRolePermissionService.getUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, list.get(0));
        return ResponseUtil.ok(list.get(0));
    }
}
