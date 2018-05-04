package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleNotesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleNotesService {
    @Resource
    private ArticleNotesMapper articleNotesMapper;
}
