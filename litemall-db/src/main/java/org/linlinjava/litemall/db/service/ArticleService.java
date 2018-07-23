package org.linlinjava.litemall.db.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ArticleMapper;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleCategoryStatService articleCategoryStatService;
    @Resource
    private NotesService notesService;
    @Resource
    private ArticleCommentService articleCommentService;
    @Resource
    private NotesTempService notesTempService;

    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/**
    *@Author:LeiQiang
    *@Description:全部图文模块列表接口实现
    *@Date:22:45 2018/5/4
    */
    public List<Article> querySelective(String categoryIds,String flag) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(categoryIds))
            criteria.andCategoryIdsContainsTo(categoryIds);
        //criteria.andStatusNotEqualTo(1);//0启用1禁用
        //人气排序
        if(!StringUtils.isEmpty(flag)&&flag.equals("reader"))
            criteria.example().setOrderByClause("reader desc");
        //时间排序倒序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date1"))
            criteria.example().setOrderByClause("create_date desc");
        //时间排序正序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date2"))
            criteria.example().setOrderByClause("create_date asc");
        return articleMapper.selectByExample(example);
    }

    /**
      * @author lanye
      * @Description 推荐图文
      * @Date 2018/5/28 14:40
      * @Param []
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> recommendedList(Integer lock) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(lock == 0){
            criteria.andIsLookEqualTo(0);
        }
        criteria.andIsViewEqualTo(1);
        criteria.andStatusEqualTo(1);
        example.setOrderByClause("anli_date desc");
        return articleMapper.selectByExample(example);
    }

    /**
      * @author lanye
      * @Description 首页导航(后台改版)
      * @Date 2018/5/21 11:44
      * @Param [categoryIds, flag]
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> querySelective2(String categoryIds,String flag,Integer lock) {
        Article article = new Article();
        List<Article> articleListReturn = new ArrayList<>();
        String[] caIds = categoryIds.split(",");
        article.setStatus(1);
        //lock 1表示规划师 0表示用户
        if(lock == 0){
           article.setIsLook(0);
        }
        //人气排序
        if(!StringUtils.isEmpty(flag)&&flag.equals("reader")) {
            article.setTitle("reader desc");
        }
        //时间排序倒序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date1")) {
            article.setTitle("b.create_date desc");
        }
        //时间排序正序
        if(!StringUtils.isEmpty(flag)&&flag.equals("date2")) {
            article.setTitle("b.create_date asc");
        }
        if(StringUtils.isEmpty(categoryIds)){
            articleListReturn.addAll(articleMapper.selectByExample2(article));
            return removeDuplicateArticle(articleListReturn);
        }
        if(caIds.length>=1){
            for(String categoryId:caIds){
                article.setCategoryId(Integer.parseInt(categoryId));
                articleListReturn.addAll(articleMapper.selectByExample2(article));
            }
        }else{
            articleListReturn.addAll(articleMapper.selectByExample2(article));
        }
        return removeDuplicateArticle(articleListReturn);
    }

    /**
      * @author lanye
      * @Description 用户自定义图书列表
      * @Date 2018/5/29 11:25
      * @Param [flag, page, size]
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> querySelective3(String flag, Integer page, Integer size,Integer isMy,String time) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(1);
        if(!StringUtils.isEmpty(isMy)) {
            criteria.andUserIdEqualTo(isMy);
        }else{
            criteria.andUserIdIsNull();
            criteria.andStatusEqualTo(1);
        }
        if(!StringUtils.isEmpty(time)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                criteria.andCreateDateBetween("2018-01-01 00:00:00",dateFormat2.format(DateUtils.getDayEnd(dateFormat.parse(time))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        criteria.example().setOrderByClause("is_view desc,create_date desc");
        /*if(!StringUtils.isEmpty(flag)&&flag.equals("date1")) {
        }*/
        if(!StringUtils.isEmpty(flag)&&flag.equals("date2")) {
            criteria.example().setOrderByClause("create_date");
        }
        //人气排序
        if(!StringUtils.isEmpty(flag)&&flag.equals("reader")) {
            criteria.example().setOrderByClause("read_count desc");
        }
        if(page!=null && size!=null){
            BigDecimal bg1 = new BigDecimal(articleMapper.selectByExample(example).size());
            BigDecimal bg2 = new BigDecimal(20);
            double d3 = bg1.divide(bg2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double pageMax = Math.ceil(d3);
            if(page>pageMax){
                return null;
            }
            PageHelper.startPage(page, size);
        }
        return articleMapper.selectByExample(example);
    }

    /**
      * @author lanye
      * @Description 热度倒序
      * @Date 2018/5/23 18:59
      * @Param [articleList]
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> sortReader(List<Article> articleList){
        Collections.sort(articleList, (s1, s2) ->{
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            return s2.getReadCount()-s1.getReadCount();
        });
        return articleList;
    }

    /**
      * @author lanye
      * @Description 时间倒序
      * @Date 2018/5/23 18:55
      * @Param [articleList]
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> sortDesc(List<Article> articleList){
        Collections.sort(articleList, (s1, s2) ->{
            if(s1 == null)
                return -1;
            if(s2 == null)
                return 1;
            if(s1.getCreateDate().contains(".0")){
                s1.setCreateDate(s1.getCreateDate().substring(0,s1.getCreateDate().length()-2));
            }
            if(s2.getCreateDate().contains(".0")){
                s2.setCreateDate(s2.getCreateDate().substring(0,s2.getCreateDate().length()-2));
            }
            Calendar c = Calendar.getInstance();
            try{
                c.setTime(dateFormat2.parse(s2.getCreateDate()));
                long a = c.getTimeInMillis();
                c.setTime(dateFormat2.parse(s1.getCreateDate()));
                long b = c.getTimeInMillis();
                return (int)a-(int)b;
            }catch (ParseException e){
                return 0;
            }
        });
        return articleList;
    }

    /**
      * @author lanye
      * @Description 时间升序
      * @Date 2018/5/23 18:56
      * @Param [articleList]
      * @return java.util.List<org.linlinjava.litemall.db.domain.Article>
      **/
    public List<Article> sortAsc(List<Article> articleList){
        Collections.sort(articleList, (s1, s2) ->{
            if(s1 == null)
                return 1;
            if(s2 == null)
                return -1;
            if(s1.getCreateDate().contains(".0")){
                s1.setCreateDate(s1.getCreateDate().substring(0,s1.getCreateDate().length()-2));
            }
            if(s2.getCreateDate().contains(".0")){
                s2.setCreateDate(s2.getCreateDate().substring(0,s2.getCreateDate().length()-2));
            }
            Calendar c = Calendar.getInstance();
            try{
                dateFormat2.parse(s1.getCreateDate());
                c.setTime(dateFormat2.parse(s2.getCreateDate()));
                long a = c.getTimeInMillis();
                dateFormat2.parse(s1.getCreateDate());
                c.setTime(dateFormat2.parse(s1.getCreateDate()));
                long b = c.getTimeInMillis();
                return (int)b-(int)a;
            }catch (ParseException e){
                return 0;
            }
        });
        return articleList;
    }

    private static ArrayList<Article> removeDuplicateArticle(List<Article> articles) {
        Set<Article> set = new TreeSet<Article>(new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                //字符串,则按照asicc码升序排列
                if(o1!=null && o2!=null){
                    return o1.getArticleId().compareTo(o2.getArticleId());
                }
                return 0;
            }
        });
        set.addAll(articles);
        return new ArrayList<Article>(set);
    }

    public Article findById(Integer article_id) {

        return articleMapper.selectByPrimaryKey(article_id);
    }
/**
    *@Author:LeiQiang
    *@Description:图文-用户收藏后阅读数量+1
    *@Date:23:34 2018/5/4
    */
    public void update(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
        this.sycArticle(article.getArticleId());
    }

    public List<Article> queryBySelective(String title,String author,Integer articleId,Integer categoryId,String flag,String updateDate,Integer status, Integer page, Integer limit, String sort, String order) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(author))
            criteria.andAuthorLike("%" + author + "%");
        if(!StringUtils.isEmpty(articleId))
            criteria.andArticleIdEqualTo(articleId);
        if(!StringUtils.isEmpty(categoryId))
            criteria.andCategoryIdEqualTo(categoryId);
        if(categoryId==null){
            criteria.andCategoryIdIsNull();
        }
        //0官方 1用户
        if("0".equals(flag)){
            criteria.andUserIdIsNull();
        }else if("1".equals(flag)){
            criteria.andUserIdIsNotNull();
        }
        if(!StringUtils.isEmpty(updateDate)){
            criteria.andUpdateDateEqualTo(updateDate);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(order)){
            criteria.example().setOrderByClause(order);
        }else {
            criteria.example().setOrderByClause("create_date desc");
        }
        if(page!=null&&limit!=null){
            PageHelper.startPage(page,limit);
        }
        return articleMapper.selectByExample(example);
    }

    public int countSelective(String title, String author, Integer articleId,Integer categoryId,String flag,String updateDate,Integer status,Integer page, Integer limit, String sort, String order) {
        ArticleExample example=new ArticleExample();
        ArticleExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(author))
            criteria.andAuthorLike("%" + author + "%");
        if(!StringUtils.isEmpty(articleId))
            criteria.andArticleIdEqualTo(articleId);
        if(!StringUtils.isEmpty(categoryId))
            criteria.andCategoryIdEqualTo(categoryId);
        if(categoryId==null){
            criteria.andCategoryIdIsNull();
        }
        //0官方 1用户
        if("0".equals(flag)){
            criteria.andUserIdIsNull();
        }else if("1".equals(flag)){
            criteria.andUserIdIsNotNull();
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        if(!StringUtils.isEmpty(updateDate)){
            criteria.andUpdateDateEqualTo(updateDate);
        }
        return (int) articleMapper.countByExample(example);
    }

    public void add(Article article) {
        articleMapper.insertSelective(article);
        this.sycArticle(article.getArticleId());
    }

    public void deleteById(Integer articleId) {
        articleMapper.deleteByPrimaryKey(articleId);
        //删除书籍评论及评论通知
        List<ArticleComment> articleCommentList = articleCommentService.query(articleId,null,null,null,null,null,null, "", null,null,null,null);
        for(ArticleComment articleComment:articleCommentList){
            articleCommentService.deleteById(articleComment.getId());
        }
        this.sycArticle(articleId);
        //删除书籍通知
        List<NotesTemp> notesTemps = notesTempService.querySelective("newbook","",null,"",null,null,"","");
        if(notesTemps==null || notesTemps.size()==0){
            return;
        }
        NotesTemp notesTemp = notesTemps.get(0);
        List<Notes> notesList = notesService.querySelective(notesTemp.getId(),notesTemp.getType(),null,null,articleId,null,null,null,"","");
        if(notesList==null || notesList.size()==0){
            return;
        }
        for(Notes notes:notesList){
            notesService.deleteById(notes.getId());
        }
    }

    public void updateById(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
        this.sycArticle(article.getArticleId());
    }

    public void sycArticle(Integer articleId) {
        Article article = this.findById(articleId);
        boolean isUpdate = false;
        if(article!=null && article.getCategoryId()==null){
            JSONArray categoryIdArray = JSON.parseArray(article.getCategoryIds());
            if(categoryIdArray == null){
                return;
            }
            List<ArticleCategoryStat> articleCategoryStatList = articleCategoryStatService.queryBySelective(null,articleId,null,null,"","");
            if(categoryIdArray.size() != articleCategoryStatList.size()){
                isUpdate = true;
            }else{
                //判断分类数据是否更改
                if(articleCategoryStatList.size()>0){
                    if(categoryIdArray.size()>0) {
                        for (int i = 0; i < categoryIdArray.size(); i++) {
                            for(ArticleCategoryStat articleCategoryStat:articleCategoryStatList){
                                if(!articleCategoryStat.getCategoryId().equals(categoryIdArray.get(i))){
                                    isUpdate = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(isUpdate){
                //先删除 然后执行重新创建关联关系
                articleCategoryStatService.deleteByExample(articleId);
                if(categoryIdArray.size()>0) {
                    ArticleCategoryStat articleCategoryStat;
                    for (int i = 0; i < categoryIdArray.size(); i++) {
                        articleCategoryStat = new ArticleCategoryStat();
                        articleCategoryStat.setArticleId(articleId);
                        articleCategoryStat.setCategoryId((Integer) categoryIdArray.get(i));
                        articleCategoryStatService.add(articleCategoryStat);
                    }
                }
            }
        }else{
            articleCategoryStatService.deleteByExample(articleId);
        }
    }
}
