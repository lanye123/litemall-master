package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.IntegretionDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IntegretionDetailService {
    @Resource
    private IntegretionDetailMapper IntegretionDetailMapper;
}
