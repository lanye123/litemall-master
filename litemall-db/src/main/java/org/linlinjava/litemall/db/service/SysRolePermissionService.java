package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysPermissionMapper;
import org.linlinjava.litemall.db.dao.SysRolePermissionMapper;
import org.linlinjava.litemall.db.domain.SysPermission;
import org.linlinjava.litemall.db.domain.SysRolePermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    public List getUserPermission(String username) {
        List list=new ArrayList();
        List<SysRolePermission> userPermission=sysRolePermissionMapper.getUserPermission(username);
        list.add(userPermission);
        //管理员角色ID为1
        int adminRoleId = 1;
        for (SysRolePermission permission:userPermission){
            if (adminRoleId == permission.getRoleId()) {
                //查询所有菜单  所有权限
                List<SysPermission> menuList = sysPermissionMapper.getAllMenu();
                List<SysPermission> permissionList = sysPermissionMapper.getAllPermission();
                list.add(menuList);
                list.add(permissionList);
            }
        }

        return list;

    }
}
