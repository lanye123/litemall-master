package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.MedalDetailsMapper;
import org.linlinjava.litemall.db.dao.MedalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MedalService {
    @Resource
    private MedalMapper medalMapper;
}
