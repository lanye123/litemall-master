package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.ArticleNotesMapper;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.domain.ArticleNotesExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleNotesService {
    @Resource
    private ArticleNotesMapper articleNotesMapper;


    public List<ArticleNotes> findByArtitleid(Integer artitle_id) {
        ArticleNotesExample example=new ArticleNotesExample();
        ArticleNotesExample.Criteria criteria=example.createCriteria();
        if (artitle_id!=null)
            criteria.andArtileIdEqualTo(artitle_id);
        return articleNotesMapper.selectByExample(example);
    }
}
