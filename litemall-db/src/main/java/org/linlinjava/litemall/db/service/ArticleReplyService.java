package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleReplyService {
    @Resource
    private ArticleReplyMapper articleReplyMapper;
}
