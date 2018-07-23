package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleCommentMapper;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleCommentExample;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesTemp;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;
    @Resource
    private NotesService notesService;
    @Resource
    private NotesTempService notesTempService;

    /**
     * @autor by leiqiang
     * @param article_id
     * @date 2018-5-7 15:51:12
     * @return
     */
    public List<ArticleComment> querySelective(Integer article_id,String flag,Integer lock,Integer page, Integer size) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(article_id!=null)
            criteria.andAArticleIdEqualTo(article_id);
        if(lock == 0){
            criteria.andBAccountIsNull();
        }
        example.setOrderByClause("create_date desc");//按时间倒序排序
        if("0".equals(flag)){
            example.setOrderByClause("create_date desc");//按时间倒序排序
        }
        if(page!=null && size!=null){
            BigDecimal bg1 = new BigDecimal(articleCommentMapper.selectByExample(example).size());
            BigDecimal bg2 = new BigDecimal(size);
            double d3 = bg1.divide(bg2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double pageMax = Math.ceil(d3);
            if(page>pageMax){
                return null;
            }
            PageHelper.startPage(page, size);
        }
        return articleCommentMapper.selectByExample(example);
    }

    /**
      * @author lanye
      * @Description 我的评论专属接口
      * @Date 2018/7/20 10:03
      * @Param [article_id, flag, page, size]
      * @return java.util.List<org.linlinjava.litemall.db.domain.ArticleComment>
      **/
    public List<ArticleComment> myquery(Integer userId,String flag,Integer page, Integer size) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(userId!=null)
            criteria.andFromUseridEqualTo(userId);
        example.setOrderByClause("create_date desc");//按时间倒序排序
        if("0".equals(flag)){
            example.setOrderByClause("create_date desc");//按时间倒序排序
        }
        if(page!=null && size!=null){
            BigDecimal bg1 = new BigDecimal(articleCommentMapper.selectByExample(example).size());
            BigDecimal bg2 = new BigDecimal(size);
            double d3 = bg1.divide(bg2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double pageMax = Math.ceil(d3);
            if(page>pageMax){
                return null;
            }
            PageHelper.startPage(page, size);
        }
        return articleCommentMapper.selectByExample(example);
    }

    /**
     * leiqiang
     * 文章评论数量
     * 2018-5-7 16:21:03
     * @param article_id
     * @return
     */
    public Long countSelective(Integer article_id) {
        ArticleCommentExample example=new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria=example.createCriteria();
        if(article_id!=null)
            criteria.andAArticleIdEqualTo(article_id);
        return articleCommentMapper.countByExample(example);
    }

    public void add(ArticleComment comment) {
        articleCommentMapper.insertSelective(comment);
    }

    public List<ArticleComment> query(Integer articleId, String categoryName, Integer categoryId, String content, Integer fromUserid, String startDate, String endDate, String nickName, Integer page, Integer size, String sort, String order) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andAArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryName)){
            criteria.andCategoryNameEqualTo(categoryName);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }
        if(!StringUtils.isEmpty(startDate)){
            criteria.andACreateDateGreaterThanOrEqualTo(startDate);
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andACreateDateLessThanOrEqualTo(endDate);
        }
        if(!StringUtils.isEmpty(nickName)){
            criteria.andBNickNameLike("%" + nickName + "%");
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }
        if(page!=null&&size!=null){
            PageHelper.startPage(page, size);
        }
        return articleCommentMapper.selectByExample(example);
    }

    public int count(Integer articleId, String categoryName, Integer categoryId, String content, Integer fromUserid, String startDate, String endDate, String nickName, Integer page, Integer size, String sort, String order) {
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(articleId)){
            criteria.andAArticleIdEqualTo(articleId);
        }
        if(!StringUtils.isEmpty(categoryName)){
            criteria.andCategoryNameEqualTo(categoryName);
        }
        if(!StringUtils.isEmpty(categoryId)){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(startDate)){
            criteria.andACreateDateGreaterThanOrEqualTo(startDate);
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andACreateDateLessThanOrEqualTo(endDate);
        }
        if(!StringUtils.isEmpty(nickName)){
            criteria.andBNickNameLike("%" + nickName + "%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%" + content + "%");
        }

        return (int) articleCommentMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        articleCommentMapper.deleteByPrimaryKey(id);
        List<NotesTemp> notesTemps = notesTempService.querySelective("reply","",null,"",null,null,"","");
        if(notesTemps==null || notesTemps.size()==0){
            return;
        }
        NotesTemp notesTemp = notesTemps.get(0);
        List<Notes> notesList = notesService.querySelective(notesTemp.getId(),1,null,null,id,null,null,null,"","");
        if(notesList==null || notesList.size()==0){
            return;
        }
        for(Notes notes:notesList){
            notesService.deleteById(notes.getId());
        }
    }

    public void update(ArticleComment articleComment) {
        articleCommentMapper.updateByPrimaryKeySelective(articleComment);
    }

    public ArticleComment queryById(Integer id) {
        return articleCommentMapper.selectByPrimaryKey(id);
    }
}
