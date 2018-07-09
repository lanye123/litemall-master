package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysPermissionMapper;
import org.linlinjava.litemall.db.domain.SysPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    public List<SysPermission> getAllPermission(){
       return sysPermissionMapper.getAllPermission();
    }

    public List<SysPermission> getAllMenu(){
        return sysPermissionMapper.getAllMenu();
    }

}
