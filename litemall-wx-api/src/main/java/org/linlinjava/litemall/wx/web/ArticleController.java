package org.linlinjava.litemall.wx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/article")
public class ArticleController {
    private final Log logger = LogFactory.getLog(ArticleController.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private ArticleCollectionService articleCollectionService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private ArticleDetailsService articleDetailsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private PraiseService praiseService;
    @Autowired
    private WxMessService wxMessService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private WxConfigService wxConfigService;
    @Value("${code_url}")
    private String codeurl;
    @Value("${miniprogram.appid}")
    private String appid;
    @Value("${miniprogram.secret}")
    private String secret;
    @Value("${access_token.url}")
    private String token_url;
    @Value("${create_codeA.url}")
    private String create_codeA_url;
    @Value("${create_codeB.url}")
    private String create_codeB_url;
    @Value("${create_codeC.url}")
    private String create_codeC_url;
    @Value("${article.url}")
    private String article_url;
    @Value("${web.upload-path}")
    private String webUploadPath;
/**
    *@Author:LeiQiang
    *@Description:全部图文模块列表接口
    *@Date:22:46 2018/5/4
    */
    @GetMapping("list")
    private Object list(String categoryIds,String flag,Integer userId){
        Map<String, Object> data = new HashMap<>();
        LitemallUser user = litemallUserService.findById(userId);
        if(user==null){
            return ResponseUtil.badArgumentValue();
        }
        data.put("isOrder",user.getIsOrder());
        Integer lock;
        if(StringUtils.isEmpty(user.getAccount())){
            lock = 0;
        }else {
            //规划师
            lock = 1;
        }
        List<Article> articleList=articleService.querySelective2(categoryIds,flag,lock);
        articleService.sortDesc(articleList);
        if(!StringUtils.isEmpty(flag)&&flag.equals("date2")) {
            articleService.sortAsc(articleList);
        }
        //人气排序
        if(!StringUtils.isEmpty(flag)&&flag.equals("reader")) {
            articleService.sortReader(articleList);
        }
        List<Map<String, Object>> articleVoList = new ArrayList<>(articleList.size());
        //Map<String, Object> data = new HashMap<>();
        //data.put("isOrder",user.getIsOrder());
        //articleVoList.add(data);
        if(articleList!=null && articleList.size()>0){
            for(Article article : articleList){
                if(article==null){
                    return ResponseUtil.ok(articleVoList);
                }
                Map<String, Object> articleVo = new HashMap<>();
                if(article.getPhotoUrl()!=null){
                    articleVo.put("photo_url",article.getPhotoUrl());
                }
                if(article.getHeadUrl()!=null){
                    articleVo.put("headUrl",article.getHeadUrl());
                }
                if(article.getPhotoName()!=null){
                    articleVo.put("photo_name",article.getPhotoName());
                }
                if(article.getArticleId()!=null){
                    articleVo.put("article_id",article.getArticleId());
                }
                if(article.getCategoryId()!=null){
                    articleVo.put("category_id",article.getCategoryId());
                }
                if(article.getTitle()!=null){
                    articleVo.put("title",article.getTitle());
                }
                if(article.getBrief()!=null){
                    articleVo.put("brief",article.getBrief());
                }
                if(article.getCreateDate().contains(".0")){
                    article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
                }
                if(article.getCreateDate()!=null){
                    articleVo.put("create_date",article.getCreateDate());
                }
                if(article.getDaodu()!=null){
                    articleVo.put("daodu",article.getDaodu());
                }
                if(article.getAuthor()!=null){
                    articleVo.put("author",article.getAuthor());
                }
                if(article.getStatus()!=null){
                    articleVo.put("status",article.getStatus());
                }
                if(article.getIsView()!=null){
                    articleVo.put("is_view",article.getIsView());
                }
                if(article.getReader()!=null){
                    articleVo.put("reader",article.getReader());
                }
                if(article.getReadCount()!=null){
                    articleVo.put("readCount",article.getReadCount());
                }
                if(article.getUpdateDate()!=null){
                    articleVo.put("update_date",article.getUpdateDate());
                }
                if(userId==null){
                    articleVo.put("flag",0);
                    articleVo.put("collectStatus",0);
                    articleVoList.add(articleVo);
                    continue;
                }
                //查当前用户是否收藏了这本书
                articleVo.put("collectStatus", articleCollectionService.countSeletive(article.getArticleId(),userId,1,null,null,"",""));
                articleVo.put("flag", medalDetailsService.countSeletive(0,article.getArticleId(),userId,null,null,null,null,"",""));
                articleVoList.add(articleVo);
            }
        }
        data.put("articleVoList",articleVoList);
        return ResponseUtil.ok(data);
    }

    /**
      * @author lanye
      * @Description 推荐图文
      * @Date 2018/5/28 14:41
      * @Param [userId]
      * @return java.lang.Object
      **/
    @GetMapping("recommendedList")
    private Object recommendedList(Integer userId){
        LitemallUser user = litemallUserService.findById(userId);
        if(user==null){
            return ResponseUtil.badArgumentValue();
        }
        Integer lock;
        if(StringUtils.isEmpty(user.getAccount())){
            lock = 0;
        }else {
            //规划师
            lock = 1;
        }
        Map<String,Object> data = new HashMap<>();
        Map<String, Object> dataItem;
        List<Article> articleList = articleService.recommendedList(lock);

        List<Map<String,Object>> returnArticles = new ArrayList<>();
        int allCount;
        int readCount;
        for(Article article:articleList){
            dataItem = new HashMap<>();
            allCount = articleNotesService.findByArtitleid(article.getArticleId(),null).size();
            if(allCount == 0){
                continue;
            }
            readCount = articleDetailsService.selectList(userId,null,article.getArticleId(),null,"").size();
            BigDecimal bd = new BigDecimal(readCount);
            bd = bd.divide(new BigDecimal(allCount),2,BigDecimal.ROUND_HALF_UP);
            dataItem.put("percentage",bd.toString());
            dataItem.put("readCount",readCount);
            dataItem.put("allCount",allCount);
            dataItem.put("title",article.getTitle());
            dataItem.put("brief",article.getBrief());
            dataItem.put("photoName",article.getPhotoName());
            dataItem.put("photoUrl",article.getPhotoUrl());
            dataItem.put("daodu",article.getDaodu());
            dataItem.put("author",article.getAuthor());
            dataItem.put("articleId",article.getArticleId());
            dataItem.put("headUrl",article.getHeadUrl());
            //查当前用户是否收藏了这本书
            dataItem.put("collectStatus", articleCollectionService.countSeletive(article.getArticleId(),userId,1,null,null,"",""));
            //查当前用户是否喜欢了这本书 0表示未点赞 1表示已点赞
            dataItem.put("praiseStatus", praiseService.countSeletive(article.getArticleId(),userId,null,null,null,null,"",""));
            if(userId==null){
                dataItem.put("readCount",0);
                dataItem.put("collectStatus",0);
                dataItem.put("praiseStatus",0);
            }
            returnArticles.add(dataItem);
        }
        data.put("returnArticles", returnArticles);
        return ResponseUtil.ok(data);
    }

/**
    *@Author:LeiQiang
    *@Description:文章详情接口
    *@Date:22:54 2018/5/4
    */
    @GetMapping("detail")
    public Object detail(Integer article_id, Integer userId) {
        /*if(userId == null){
            return ResponseUtil.unlogin();
        }*/
        if(article_id == null){
            return ResponseUtil.badArgument();
        }
        /*if(userId == null){
            return ResponseUtil.badArgument();
        }*/

        Article article=articleService.findById(article_id);
        article.setReadCount(article.getReadCount()+1);
        articleService.updateById(article);
        if(article == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<String, Object> data = new HashMap<>();
        JSONArray categoryIds = JSON.parseArray(article.getCategoryIds());
        String categoryName = "";
        if(categoryIds!=null && categoryIds.size()>0){
            for(Object categoryId:categoryIds){
                categoryName += articleCategoryService.findById((Integer) categoryId).getName()+",";
            }
            data.put("categoryName",categoryName);
        }
        LitemallUser user;
        user = litemallUserService.findById(article.getUserId());
        if(user!=null){
            data.put("nickName",user.getNickname());
        }
        data.put("photo_url",article.getPhotoUrl());
        data.put("photo_name",article.getPhotoName());
        data.put("article_id",article.getArticleId());
        data.put("category_id",article.getCategoryId());
        data.put("categoryIds",article.getCategoryIds());
        data.put("title",article.getTitle());
        data.put("brief",article.getBrief());
        if(article.getCreateDate().contains(".0")){
            article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
        }
        if(!StringUtils.isEmpty(article.getCodeUrl())){
            data.put("codeUrl",article.getCodeUrl());
        }else{
            data.put("codeUrl",codeurl);
        }
        data.put("create_date",article.getCreateDate());
        data.put("daodu",article.getDaodu());
        data.put("author",article.getAuthor());
        data.put("status",article.getStatus());
        data.put("is_view",article.getIsView());
        data.put("reader",article.getReader());
        data.put("update_date",article.getUpdateDate());
        data.put("readCount",article.getReadCount());
        //评论数量
        Long comentCount=articleCommentService.countSelective(article_id);
        //目录列表
        List<ArticleNotes> notesList=articleNotesService.findByArtitleid(article_id,1);
        List<Map<String, Object>> notesVoList = new ArrayList<>(notesList.size());
        List<ArticleDetails> articleDetailsList;
        for(ArticleNotes notes : notesList) {
            Map<String, Object> notesVo = new HashMap<>();
            notesVo.put("id",notes.getId());
            notesVo.put("no",notes.getNo());
            notesVo.put("name",notes.getName());
            notesVo.put("content",notes.getContent());
            notesVo.put("status",notes.getStatus());
            notesVo.put("sort_no",notes.getSortNo());
            notesVo.put("brief",notes.getBrief());
            if(notes.getCreateDate().contains(".0")){
                notes.setCreateDate(notes.getCreateDate().substring(0,notes.getCreateDate().length()-2));
            }
            notesVo.put("create_date",notes.getCreateDate());
            notesVo.put("daodu",notes.getDaodu());
            notesVo.put("author",notes.getAuthor());
            notesVo.put("status",notes.getStatus());
            notesVo.put("reader",notes.getRender());
            notesVo.put("photo_url",notes.getPhotoUrl());
            notesVo.put("photo_name",notes.getPhotoName());
            notesVo.put("article_id",notes.getArtileId());
            notesVo.put("read","no");
            if(userId==null){
                notesVo.put("flag",0);
                notesVo.put("read","no");
                notesVoList.add(notesVo);
                continue;
            }
            notesVo.put("flag", medalDetailsService.countSeletive(notes.getId(),article_id,userId,null,null,null,null,"",""));
            articleDetailsList = articleDetailsService.selectList(userId,null,article_id,notes.getId(),"");
            if(articleDetailsList!=null && articleDetailsList.size()>0){
                notesVo.put("read","yes");
            }
            notesVoList.add(notesVo);
        }
        data.put("notesList",notesVoList);
        data.put("comentCount",comentCount);
        if(userId==null){
            data.put("flag",0);
            data.put("collectStatus",0);
            return ResponseUtil.ok(data);
        }
        data.put("flag", medalDetailsService.countSeletive(0,article_id,userId,null,null,null,null,"",""));
        if(article.getCategoryId()!=null&&article.getCategoryId()==1){
            data.put("flag", medalDetailsService.countSeletive(null,article_id,userId,null,null,null,null,"",""));
            if(article.getUserId()==null){
//                    articleVo.put("nickName","萤火虫");
                data.put("nickName",article.getAuthor());
            }else{
                LitemallUser user2 = litemallUserService.findById(article.getUserId());
                if(user2!=null){
                    data.put("nickName",user2.getNickname());
                }
            }
        }
        data.put("collectStatus", articleCollectionService.countSeletive(article_id,userId,1,null,null,"",""));
        return ResponseUtil.ok(data);
    }
/**
    *@Author:LeiQiang
    *@Description:图文-收藏接口
    *@Date:23:24 2018/5/4
    */
@PostMapping("collect")
public Object collect(@RequestBody Article model) {
    if(model.getArticleId() == null){
        return ResponseUtil.badArgument();
    }
    if(model.getUser_id() == null){
        return ResponseUtil.badArgument();
    }
    Article article=articleService.findById(model.getArticleId());
    //保存至收藏表
    if(model.getStatus() == 0){
        //用户取消收藏
        List<ArticleCollection> articleCollectionList = articleCollectionService.querySelective(model.getArticleId(),model.getUser_id(),1,null,null,"","");
        for(ArticleCollection articleCollection:articleCollectionList){
            articleCollection.setStatus(0);
            articleCollection.setIsView(model.getType());
            articleCollectionService.update(articleCollection);
        }
    }else if(model.getStatus() == 1){
        //用户收藏
        List<ArticleCollection> articleCollectionList = articleCollectionService.querySelective(model.getArticleId(),model.getUser_id(),null,null,null,"","");
        if(articleCollectionList.size()==0){
            ArticleCollection collection = new ArticleCollection();
            collection.setArticleId(model.getArticleId());
            collection.setUserId(model.getUser_id());
            collection.setStatus(model.getStatus());
            collection.setIsView(model.getType());
            articleCollectionService.add(collection);
        }else
        {
            for(ArticleCollection articleCollection:articleCollectionList){
                articleCollection.setStatus(1);
                articleCollectionService.update(articleCollection);
            }
        }
    }
    return ResponseUtil.ok();
}

    /**
      * @author lanye
      * @Description 创建用户自定义图书
      * @Date 2018/5/29 9:54
      * @Param [model]
      * @return java.lang.Object
      **/
    @PostMapping("add")
    public Object add(@RequestBody Article article) {
        if(article == null){
            return ResponseUtil.badArgument();
        }
        if(article.getUserId() == null){
            return ResponseUtil.badArgument();
        }
        article.setCategoryId(1);
        articleService.add(article);
        saveCode(article);
        return ResponseUtil.ok(article);
    }
    @PostMapping("sendArticleTemplete")
    public Object sendArticleTemplete(@RequestBody WxMess mess){
        String url=mess.getUrl();
        String keyword1=mess.getKeyword1();
        String keyword2=mess.getKeyword2();
        String keyword3=mess.getKeyword3();
        String formId=mess.getFormId();
        Integer userId=mess.getUserId();
        JSONObject result=wxMessService.articleCheck(url,keyword1,keyword2,keyword3,formId,userId);
        return ResponseUtil.ok(result);
    }

    /**
      * @author lanye
      * @Description 用户自定义图书列表
      * @Date 2018/5/29 11:36
      * @Param [flag, userId, page, size]
      * @return java.lang.Object
      **/
    @GetMapping("customList")
    private Object customList(String flag,Integer userId,Integer isMy,String time,@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "size", defaultValue = "30")Integer size){
        logger.debug("传入标识flag："+flag+",用户id："+userId);

        if(StringUtils.isEmpty(userId)){
            return ResponseUtil.badArgument();
        }
        List<Article> articleList=articleService.querySelective3(flag,page, size,isMy,time);
        if(articleList==null || articleList.size()<=0){
            return ResponseUtil.ok();
        }
        logger.debug("初步查询结果articleList："+articleList);
        List<Map<String, Object>> articleVoList = new ArrayList<>(articleList.size());
        if(articleList!=null && articleList.size()>0){
            for(Article article : articleList){
                if(article==null){
                    return ResponseUtil.ok(articleVoList);
                }
                Map<String, Object> articleVo = new HashMap<>();
                articleVo.put("photo_url",article.getPhotoUrl());
                articleVo.put("headUrl",article.getHeadUrl());
                articleVo.put("userId",article.getUserId());
                articleVo.put("photo_name",article.getPhotoName());
                articleVo.put("article_id",article.getArticleId());
                articleVo.put("category_id",article.getCategoryId());
                articleVo.put("title",article.getTitle());
                articleVo.put("brief",article.getBrief());
                if(article.getCreateDate().contains(".0")){
                    article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
                }
                if(!StringUtils.isEmpty(article.getCodeUrl())){
                    articleVo.put("codeUrl",article.getCodeUrl());
                }else{
                    articleVo.put("codeUrl",codeurl);
                }

                articleVo.put("create_date",article.getCreateDate());
                articleVo.put("daodu",article.getDaodu());
                articleVo.put("author",article.getAuthor());
                articleVo.put("status",article.getStatus());
                articleVo.put("is_view",article.getIsView());
                articleVo.put("reader",article.getReader());
                articleVo.put("readCount",article.getReadCount());
                articleVo.put("update_date",article.getUpdateDate());
                articleVo.put("medalName",medalDetailsService.getMedalByScore(medalDetailsService.getScoreByUserId(article.getUserId(),"","")).getName());
                if(article.getUserId()==null){
//                    articleVo.put("nickName","萤火虫");
                    articleVo.put("nickName",article.getAuthor());
                    articleVo.put("avatar","https://sunlands.ministudy.com/images/yhc_logo.png");
                }else{
                    LitemallUser user = litemallUserService.findById(article.getUserId());
                    if(user == null){
                        continue;
                    }
                    articleVo.put("nickName",user.getNickname());
                    articleVo.put("avatar",user.getAvatar());
                }
                //查当前用户是否收藏了这本书
                articleVo.put("collectStatus", articleCollectionService.countSeletive(article.getArticleId(),userId,1,null,null,"",""));
                //查当前用户是否喜欢了这本书 0表示未点赞 1表示已点赞
                articleVo.put("praiseStatus", praiseService.countSeletive(article.getArticleId(),userId,null,1,null,null,"",""));
                articleVo.put("praiseCount", praiseService.countSeletive(article.getArticleId(),null,null,1,null,null,"",""));
                articleVo.put("flag", medalDetailsService.countSeletive(null,article.getArticleId(),userId,null,null,null,null,"",""));
                if(userId == null){
                    articleVo.put("collectStatus", 0);
                    articleVo.put("praiseStatus", 0);
                    articleVo.put("flag", 0);
                }
                articleVoList.add(articleVo);
            }
        }
        return ResponseUtil.ok(articleVoList);
    }

    /**
      * @author lanye
      * @Description 删除自定义图书
      * @Date 2018/6/4 10:23
      * @Param [article]
      * @return java.lang.Object
      **/
    @PostMapping("/delete")
    public Object delete(@RequestBody Article article){
        articleService.deleteById(article.getArticleId());
        return ResponseUtil.ok();
    }

    /**
      * @author lanye
      * @Description 更新分享次数
      * @Date 2018/6/15 9:24
      * @Param [article]
      * @return java.lang.Object
      **/
    @PostMapping("/share")
    public Object share(Integer articleId){
        Article article = articleService.findById(articleId);
        if(article!=null){
            article.setShareCount(article.getShareCount()+1);
            articleService.updateById(article);
        }
        return ResponseUtil.ok(article);
    }

    /**
     * 图文详情模块二维码图片生成及保存
     * @author leiqiang
     * @date 2018-5-31 14:15:07
     */
    @PostMapping("/code")
    public Object saveCode(@RequestBody Article a){
        WxConfig config=wxConfigService.getToken();
        String path=article_url.replace("ARTICLEID",Integer.toString(a.getArticleId()));
        Article article=new Article();
        JSONObject object=new JSONObject();
        object.put("path",path);
        object.put("width",430);//小程序二维码宽度
        String requestUrl=create_codeA_url.replace("ACCESS_TOKEN",config.getAccessToken());
        InputStream i=HttpClientUtil.doPostInstream(requestUrl,object);
        byte[] data = new byte[1024];
        int len = -1;
        FileOutputStream fileOutputStream = null;
        try {
            String temp = "images" + File.separator + "code" + File.separator;
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            // 文件路径
            String filePath = webUploadPath.concat(temp);

            File dest = new File(filePath, newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            fileOutputStream = new FileOutputStream(dest);
            while ((len = i.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            // 将反斜杠转换为正斜杠
            String datapath = temp.replaceAll("\\\\", "/") + newFileName;
            article.setArticleId(a.getArticleId());
            article.setCodeUrl(datapath);
            articleService.update(article);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (i != null) {
                try {
                    i.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseUtil.ok(article);
    }
}