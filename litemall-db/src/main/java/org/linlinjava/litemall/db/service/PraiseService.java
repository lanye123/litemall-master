package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.PraiseMapper;
import org.linlinjava.litemall.db.domain.Praise;
import org.linlinjava.litemall.db.domain.PraiseExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
  * @author lanye
  * @Description 文章点赞记录相关实现
  * @Date 2018/5/29 15:39
  * @Param 
  * @return 
  **/
@Service
public class PraiseService {
    @Resource
    private PraiseMapper praiseMapper;
    
    public void add(Praise praise) {
        praiseMapper.insertSelective(praise);
    }

    public void update(Praise praise) {
        praiseMapper.updateByPrimaryKeySelective(praise);
    }

    public Praise findById(Integer id) {
        return praiseMapper.selectByPrimaryKey(id);
    }

    public List<Praise> querySelective(String articleId, Integer userId, Integer fromUserid, Integer status, Integer page, Integer size, String sort, String order) {
        PraiseExample example = new PraiseExample();
        PraiseExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(status)){
            if(status == 0){
                criteria.andStatusEqualTo((byte)0);
            }
            if(status == 1){
                criteria.andStatusEqualTo((byte)1);
            }
        }
        criteria.example().setOrderByClause("create_date desc");

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return praiseMapper.selectByExample(example);
    }

    public int countSeletive(String articleId, Integer userId, Integer fromUserid, Integer status, Integer page, Integer size, String sort, String order) {
        PraiseExample example = new PraiseExample();
        PraiseExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(status)){
            if(status == 1){
                criteria.andStatusEqualTo((byte)1);
            }
            if(status == 0){
                criteria.andStatusEqualTo((byte)0);
            }
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }

        return (int) praiseMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        praiseMapper.deleteByPrimaryKey(id);
    }
    
}
