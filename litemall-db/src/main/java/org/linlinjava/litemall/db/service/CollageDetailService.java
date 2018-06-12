package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.CollageDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollageDetailService {
    @Resource
    private CollageDetailMapper collageDetailMapper;
}
