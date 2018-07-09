package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.SysRoleMapper;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
}
