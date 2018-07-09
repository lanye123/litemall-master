package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
}
