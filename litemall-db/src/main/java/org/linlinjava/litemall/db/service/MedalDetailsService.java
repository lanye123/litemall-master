package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.MedalDetailsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MedalDetailsService {
    @Resource
    private MedalDetailsMapper medalDetailsMapper;
}
