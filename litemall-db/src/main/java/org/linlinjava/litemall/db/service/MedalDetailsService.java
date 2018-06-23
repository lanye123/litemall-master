package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.MedalDetailsMapper;
import org.linlinjava.litemall.db.domain.Medal;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.domain.MedalDetailsExample;
import org.linlinjava.litemall.db.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MedalDetailsService {
    @Resource
    private MedalDetailsMapper medalDetailsMapper;
    @Resource
    private MedalService medalService;
    /**
     *@Author:lanye
     *@Description:用户勋章流水接口
     *@Date:23:24 2018/5/7
     */
    public String add(MedalDetails medalDetails) {
        if(this.getScoreByUserId(medalDetails.getUserId(),DateUtils.getDayStartString(),DateUtils.getDayEndString())<300){
            if(this.countSeletive(medalDetails.getNotesId(),medalDetails.getArticleId(),medalDetails.getUserId(),null,null,null,null,"","")<=0){
                medalDetailsMapper.insertSelective(medalDetails);
                return  "ok";
            }
            return "ok";
        }
        return  "fail";
    }

    public void update(MedalDetails medalDetails) {
        medalDetailsMapper.updateByPrimaryKeySelective(medalDetails);
    }

    public List<MedalDetails> selectList(Integer userId, Integer medalId, Integer articleId, Integer notesId,String time1,String time2) {
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
        if(!StringUtils.isEmpty(time1) && !StringUtils.isEmpty(time2)){
            criteria.andCreateDateBetween(time1,time2);
        }
        criteria.example().setOrderByClause("create_date desc");

        return medalDetailsMapper.selectByExample(example);
    }

    public List<MedalDetails> list(Integer userId,String time1,String time2,Integer page,Integer limit) {
        MedalDetailsExample example = new MedalDetailsExample();
        MedalDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(time1) && !StringUtils.isEmpty(time2)){
            criteria.andMDCreateDateBetween(time1,time2);
        }

        if(page!=null && limit!=null){
            BigDecimal bg2 = new BigDecimal(20);
            BigDecimal bg1 = new BigDecimal(medalDetailsMapper.selectByExample(example).size());
            double d3 = bg1.divide(bg2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double pageMax = Math.ceil(d3);
            if(page>pageMax){
                return null;
            }
            PageHelper.startPage(page, limit);
        }

        return medalDetailsMapper.selectList(example);
    }

    /**
     *@param :score
     *@return :Medal
     *@Description:返回用户的勋章
     *@Date:9:50 2018/5/8
     */
    public Medal getMedalByScore(int score) {
        List<Medal> medals = medalService.getMedal(null);
        int min;
        for(int i = medals.size();i>0;i--){
            if(medals.get(i-1).getMin() == null){
                min = 0;
            }else{
                min = medals.get(i-1).getMin();
            }
            if(score>=min){
                return medals.get(i-1);
            }
        }
        return medals.get(0);
    }

    /**
     *@param :userId,time1~time2
     *@return :score
     *@Description:返回用户的成长值
     *@Date:9:50 2018/5/8
     */
    public int getScoreByUserId(Integer userId, String time1,String time2) {
        MedalDetailsExample example = new MedalDetailsExample();
        MedalDetailsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }else{
            return 0;
        }

        int score = 0;
        List<MedalDetails> medalDetailsList = this.selectList(userId,null,null,null,time1,time2);
        for(MedalDetails medalDetails:medalDetailsList){
            score+=medalDetails.getAmount();
        }

        return score;
    }

    public List<MedalDetails> querySelective(Integer notesId, Integer articleId, Integer userId,Integer medalId, Integer amount, Integer page, Integer size, String sort, String order) {
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
        if(!StringUtils.isEmpty(amount)){
            criteria.andAmountEqualTo(amount);
        }
        criteria.example().setOrderByClause("create_date desc");

        PageHelper.startPage(page, size);
        return medalDetailsMapper.selectByExample(example);
    }

    public int countSeletive(Integer notesId, Integer articleId, Integer userId,Integer medalId, Integer amount, Integer page, Integer size, String sort, String order) {
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
        if(!StringUtils.isEmpty(amount)){
            criteria.andAmountEqualTo(amount);
        }

        return (int) medalDetailsMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        medalDetailsMapper.deleteByPrimaryKey(id);
    }

}
