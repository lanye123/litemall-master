package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping("/list")
    public Object list(String title,String author,Integer articleId,Integer categoryId,String flag,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<Article> articleList = articleService.queryBySelective(title,author,articleId,categoryId,flag,"", page, limit, sort, order);
        int total = articleService.countSelective(title,author,articleId,categoryId,flag,"", page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Article article){
        articleService.add(article);
        saveCode(article.getArticleId());
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
        articleService.updateById(article);
        if(StringUtils.isNotEmpty(article.getCodeUrl()))
            saveCode(article.getArticleId());
        return ResponseUtil.ok();
    }

    @PostMapping("/online")
    public Object online(@RequestBody Article article){
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
            //wxMessService.articleCheck();
        }else if(articleDb.getStatus()==1){
            articleDb.setStatus(0);
            articleService.updateById(articleDb);
        }
        return ResponseUtil.ok(articleDb);
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
            articleDb.setIsView(1);
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
        List<LitemallUser> userList = litemallUserService.querySelective("","","",null,null,"","");
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
    public void saveCode(Integer article_id){
        WxConfig config=wxConfigService.getToken();
        String path=article_url.replace("ARTICLEID",Integer.toString(article_id));
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
            article.setArticleId(article_id);
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
    }
}
