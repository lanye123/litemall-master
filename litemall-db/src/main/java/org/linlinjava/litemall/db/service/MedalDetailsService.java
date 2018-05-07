package org.linlinjava.litemall.db.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedalDetailsService {
    @Resource
    private MedalDetailsMapper medalDetailsMapper;
    /**
     *@Author:LeiQiang
     *@Description:用户勋章流水接口
     *@Date:23:24 2018/5/7
     */
    public void add(MedalDetails medalDetails) {
        medalDetailsMapper.insertSelective(medalDetails);
    }

    public void update(MedalDetails medalDetails) {
        medalDetailsMapper.updateByPrimaryKeySelective(medalDetails);
    }

    public List<MedalDetails> selectList(Integer userId,Integer medalId,Integer articleId,Integer notesId) {
        MedalDetailsExample example = new MedalDetailsExample();
        MedalDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(medalId)){
            criteria.andMedalIdEqualTo(medalId);
        }
        if(!StringUtils.isEmpty(notesId)){
            criteria.andNotesIdEqualTo(notesId);
        }
        criteria.example().setOrderByClause("create_date desc");

        return medalDetailsMapper.selectByExample(example);
    }

}
