package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
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

    public List<ArticleNotes> querySelective(String artileName, String name,String no,String content,Integer sortNo,Integer articleId,Integer page, Integer size, String sort, String order) {
        ArticleNotesExample example = new ArticleNotesExample();
        ArticleNotesExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(!StringUtils.isEmpty(artileName)){
            criteria.andArticleNameLike(artileName);
        }
        if(!StringUtils.isEmpty(no)){
            criteria.andNoLike("%" + no + "%");
        }
        if(!StringUtils.isEmpty(sortNo)){
            criteria.andSortNoEqualTo(sortNo);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArtileIdEqualTo(articleId);
        }

        PageHelper.startPage(page, size);
        return articleNotesMapper.selectByExample(example);
    }

    public int countSelective(String artileName, String name,String no,String content,Integer sortNo,Integer articleId,Integer page, Integer size, String sort, String order) {
        ArticleNotesExample example = new ArticleNotesExample();
        ArticleNotesExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(!StringUtils.isEmpty(artileName)){
            criteria.andArticleNameLike(artileName);
        }
        if(!StringUtils.isEmpty(no)){
            criteria.andNoLike("%" + no + "%");
        }
        if(!StringUtils.isEmpty(sortNo)){
            criteria.andSortNoEqualTo(sortNo);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArtileIdEqualTo(articleId);
        }

        return (int) articleNotesMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        articleNotesMapper.deleteByPrimaryKey(id);
    }

    public void update(ArticleNotes articleNotes) {
        articleNotesMapper.updateByPrimaryKeySelective(articleNotes);
    }

    public void add(ArticleNotes articleNotes) {
        articleNotesMapper.insertSelective(articleNotes);
    }

    public ArticleNotes findByID(Integer id) {
       return articleNotesMapper.selectByPrimaryKey(id);
    }
}
