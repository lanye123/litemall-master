package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.DateUtils;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private NotesTempService notesTempService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private WxMessService wxMessService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private MedalDetailsService medalDetailsService;
    @Autowired
    private PraiseService praiseService;
    @Autowired
    private WxFormidService wxFormidService;
    @Autowired
    private ArticleNotesService articleNotesService;

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
    @Value("${articledetail.url}")
    private String articleurl;

    @GetMapping("/list")
    public Object list(String title,String author,Integer articleId,Integer categoryId,String flag,Integer status,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Article> articleList = articleService.queryBySelective(title,author,articleId,categoryId,flag,"",status, page, limit, sort, order);
        int total = articleService.countSelective(title,author,articleId,categoryId,flag,"",status, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        //点亮次数 点赞次数 用户昵称 用户名
        LitemallUser user;
        if(categoryId!=null && categoryId == 1){
            for(Article article:articleList){
                if(article.getUserId() == null){
                    article.setNickName(article.getAuthor());
                    article.setUserName("官方");
                }else {
                    user = litemallUserService.findById(article.getUserId());
                    if(user!=null){
                        article.setNickName(user.getNickname());
                        article.setUserName(user.getRegisterIp());
                    }
                }
                article.setShineCount(medalDetailsService.countSeletive(null,article.getArticleId(),null,null,null,null,null,"",""));
                article.setPraiseCount(praiseService.countSeletive(article.getArticleId(),null,null,1,null,null,"",""));
                if(article.getCreateDate().contains(".0")){
                    article.setCreateDate(article.getCreateDate().substring(0,article.getCreateDate().length()-2));
                }
                if(article.getUpdateDate()!=null && article.getUpdateDate().contains("00:00:00.0")){
                    article.setUpdateDate(article.getUpdateDate().substring(0,article.getUpdateDate().length()-11));
                }
                article.setPhotoUrl("https://sunlands.ministudy.com/"+article.getPhotoUrl());
            }
        }
        data.put("items", articleList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Article article){
        articleService.add(article);
        if(article.getCategoryId()==1)
        saveCode(article);
        return ResponseUtil.ok(article);
    }

    @GetMapping("/read")
    public Object read(Integer articleId){
        if(articleId == null){
            return ResponseUtil.badArgument();
        }

        Article article = articleService.findById(articleId);
        return ResponseUtil.ok(article);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Article article){
        Map data=new HashMap();
        JSONObject result=null;
        if(article.getPhotoUrl().contains("https://sunlands.ministudy.com/")){
            article.setPhotoUrl(article.getPhotoUrl().replace("https://sunlands.ministudy.com/",""));
        }
        articleService.updateById(article);
        if(StringUtils.isNotEmpty(article.getCodeUrl()))
            saveCode(article);
        Article a=articleService.findById(article.getArticleId());
        //审核不通过通知
        if(article.getStatus()==2&&a.getUserId()!=null)
        {
            LitemallUser user=litemallUserService.findById(a.getUserId());
            if(StringUtils.isNotEmpty(user.getWeixinOpenid())){
                List<WxFormid> list=wxFormidService.queryByStatus(user.getWeixinOpenid());
                if(list.size()>0){
                    WxFormid form=list.get(0);
                    result=wxMessService.articleCheckFail(null,"1：发布图片需为横图，无水印且美观清晰。\n" +
                            "2：发布内容正向积极，不得违反互联网发布内容规范。\n" +
                            "3：用户发布优秀图文，则被官方审核通过并推荐展现。",DateUtils.currentTime(),form.getFormId(),a.getUserId());
                    data.put("result",result);
                    Integer errcode=Integer.parseInt(result.getString("errcode"));
                    switch (errcode){
                        case 0:
                            data.put("errmsg","信息发送成功");
                            break;
                        case 40037:
                            data.put("errmsg","template_id不正确");
                            break;
                        case 41028:
                            data.put("errmsg","form_id不正确，或者过期");
                            break;
                        case 41029:
                            data.put("errmsg","form_id已被使用");
                            break;
                        case 41030:
                            data.put("errmsg","page不正确");
                            break;
                        case 45009:
                            data.put("errmsg","接口调用超过限额（目前默认每个帐号日调用限额为100万）");
                            break;
                        case 40001:
                            data.put("errmsg","不合法的调用凭证");
                            break;
                    }
                    form.setStatus(1);//formid状态更新为已使用
                    wxFormidService.update(form);
                }
            }

        }
        return ResponseUtil.ok(data);
    }

    @PostMapping("/online")
    public Object online(@RequestBody Article article){
        Map data=new HashMap();
        JSONObject result=null;
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        if(articleDb.getStatus()==0){
            articleDb.setStatus(1);
            articleService.updateById(articleDb);
            data.put("articleDb",articleDb);
            //小程序上线提醒
            //by leiqiang
            String url="";
            Article a=articleService.findById(article.getArticleId());
            //图文发布审核通过通知
            if(a.getUserId()!=null){
                url=article_url.replace("ARTICLEID",Integer.toString(article.getArticleId()));
                LitemallUser user=litemallUserService.findById(a.getUserId());
                if(StringUtils.isNotEmpty(user.getWeixinOpenid())){
                    List<WxFormid> list=wxFormidService.queryByStatus(user.getWeixinOpenid());
                    if(list.size()>0){
                        WxFormid form=list.get(0);
                        result=wxMessService.articleCheck(url,a.getDaodu(),a.getCreateDate(),DateUtils.currentTime(),form.getFormId(),a.getUserId());
                        data.put("result",result);
                        Integer errcode=Integer.parseInt(result.getString("errcode"));
                        switch (errcode){
                            case 0:
                                data.put("errmsg","信息发送成功");
                                break;
                            case 40037:
                                data.put("errmsg","template_id不正确");
                                break;
                            case 41028:
                                data.put("errmsg","form_id不正确，或者过期");
                                break;
                            case 41029:
                                data.put("errmsg","form_id已被使用");
                                break;
                            case 41030:
                                data.put("errmsg","page不正确");
                                break;
                            case 45009:
                                data.put("errmsg","接口调用超过限额（目前默认每个帐号日调用限额为100万）");
                                break;
                            case 40001:
                                data.put("errmsg","不合法的调用凭证");
                                break;
                        }
                        form.setStatus(1);//formid状态更新为已使用
                        wxFormidService.update(form);
                    }
                }
            }else
            {
                List<ArticleNotes> notesList=articleNotesService.querySelective(null,null,null,null,null,article.getArticleId(),1,20,null,"sort_no asc");
                if(notesList.size()>0){
                    ArticleNotes notes=notesList.get(0);
                    url=articleurl.replace("ARTICLEID",Integer.toString(notes.getArtileId())).replace("NOTESID",Integer.toString(notes.getId())).replace("NAME",notes.getName());
                    //新书上架通知
                    List<LitemallUser> userList=litemallUserService.queryAll();
                    for(LitemallUser users:userList){
                        List<WxFormid> list=wxFormidService.queryByStatus(users.getWeixinOpenid());
                        if(list.size()>0){
                            WxFormid form=list.get(0);
                           result=wxMessService.articleNotice(url,a.getTitle(),"新书上架啦！"+a.getDaodu(),form.getFormId(),users.getId());
                            data.put("result",result);
                            Integer errcode=Integer.parseInt(result.getString("errcode"));
                            switch (errcode){
                                case 0:
                                    data.put("errmsg","信息发送成功");
                                    break;
                                case 40037:
                                    data.put("errmsg","template_id不正确");
                                    break;
                                case 41028:
                                    data.put("errmsg","form_id不正确，或者过期");
                                    break;
                                case 41029:
                                    data.put("errmsg","form_id已被使用");
                                    break;
                                case 41030:
                                    data.put("errmsg","page不正确");
                                    break;
                                case 45009:
                                    data.put("errmsg","接口调用超过限额（目前默认每个帐号日调用限额为100万）");
                                    break;
                                case 40001:
                                    data.put("errmsg","不合法的调用凭证");
                                    break;
                            }
                            form.setStatus(1);//formid状态更新为已使用
                            wxFormidService.update(form);
                        }

                    }
                }
            }

        }else if(articleDb.getStatus()==1){
            articleDb.setStatus(0);
            articleService.updateById(articleDb);
            data.put("articleDb",articleDb);
        }
        return ResponseUtil.ok(data);
    }

    @PostMapping("/anli")
    public Object anli(@RequestBody Article article){
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        if(articleDb.getIsView()==0){
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            articleDb.setIsView(1);
            articleDb.setAnliDate(simpleDateFormat2.format(new Date()));
            articleService.updateById(articleDb);
        }else if(articleDb.getIsView()==1){
            articleDb.setIsView(0);
            articleService.updateById(articleDb);
        }
        return ResponseUtil.ok(articleDb);
    }

    @PostMapping("/push")
    public Object push(@RequestBody Article article){
        if(article == null){
            return ResponseUtil.badArgument();
        }

        Article articleDb = articleService.findById(article.getArticleId());
        if(articleDb==null){
            return ResponseUtil.ok();
        }
        List<NotesTemp> notesTemps = notesTempService.querySelective("newbook","",null,"",null,null,"","");
        if(notesTemps==null || notesTemps.size()==0){
            return ResponseUtil.ok(article);
        }
        List<LitemallUser> userList = litemallUserService.querySelective("","","","",null,null,"","");
        NotesTemp notesTemp = notesTemps.get(0);
        Notes notes;
        Notes notesDb;
        List<Notes> notesList;
        for(LitemallUser user:userList){
            notes = new Notes();
            notes.setFromUserid(user.getId());
            notes.setTempId(notesTemp.getId());
            notes.setType(notesTemp.getType());
            notes.setContent(notesTemp.getContent().replace("TITLE",articleDb.getTitle()));
            notes.setNo(notesTemp.getNo());
            notes.setInfoid(article.getArticleId());
            notesList = notesService.querySelective(notes.getTempId(),notes.getType(),null,notes.getFromUserid(),notes.getInfoid(),null,null,null,"","");
            if(notesList!=null && notesList.size()>0){
                notesDb = notesList.get(0);
                notesDb.setContent(notes.getContent());
                notesDb.setStatus("0");
                notesService.update(notesDb);
                continue;
            }
            notesService.add(notes);
        }
        return ResponseUtil.ok(articleDb);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Article article){
        articleService.deleteById(article.getArticleId());
        return ResponseUtil.ok();
    }
    @PostMapping("/test")
    public Object test(@RequestBody Article article){
        articleService.add(article);
        return ResponseUtil.ok(article.getArticleId());
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

    /*public static void main(String[] args){
        wxMessService.articleCheck("pages/graphic/main","早上8点早客户CASIO办公室接2个客人到厂里做质检","2018-02-15 12:25:30","2017-05-04 12:30:30","1529055473776",6);
    }*/
}
