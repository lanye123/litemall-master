package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.domain.WxCode;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.ArticleService;
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

/**
 * 文章详情模块
 * @author leiqiang
 * @date 2018-5-31 14:15:07
 */
@RestController
@RequestMapping("/admin/sunlands/articleNotes")
public class ArticleNotesController {
    private final Log logger = LogFactory.getLog(ArticleNotesController.class);

    @Autowired
    private ArticleNotesService articleNotesService;
    @Autowired
    private ArticleService articleService;

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
    @Value("${articledetail.url}")
    private String articledetail_url;
    @Value("${web.upload-path}")
    private String webUploadPath;

    @GetMapping("/list")
    public Object list(String artileName, String name,String no,String content,Integer artileId,Integer sortNo,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        order = "create_date";
        List<ArticleNotes> articleNotesList = articleNotesService.querySelective(artileName, name,no,content,sortNo,artileId, page, limit, sort, order);
        int total = articleNotesService.countSelective(artileName, name,no,content,sortNo, artileId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", articleNotesList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody ArticleNotes articleNotes){
        logger.debug(articleNotes);

        articleNotesService.add(articleNotes);
        //保存该详情页面小程序二维码
        saveCode(articleNotes.getArtileId(),articleNotes.getId(),articleNotes.getName());
        return ResponseUtil.ok(articleNotes);
    }

    @PostMapping("/update")
    public Object update(@RequestBody ArticleNotes articleNotes){
        if(StringUtils.isEmpty(articleNotes.getCode_url()))
            saveCode(articleNotes.getArtileId(),articleNotes.getId(),articleNotes.getName());//保存该详情页面小程序二维码
        articleNotesService.update(articleNotes);
        return ResponseUtil.ok(articleNotes);
    }

    @PostMapping("/updateArticlePhoto")
    public Object updateArticlePhoto(@RequestBody ArticleNotes articleNotes){
        logger.debug(articleNotes);
        if(articleNotes==null){
            return ResponseUtil.badArgument();
        }
        Article article = articleService.findById(articleNotes.getArtileId());
        if(article==null){
            return ResponseUtil.badArgument();
        }
        article.setPhotoUrl(articleNotes.getPhotoUrl());
        article.setDaodu(articleNotes.getDaodu());
        articleService.updateById(article);
        return ResponseUtil.ok(articleNotes);
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody ArticleNotes articleNotes){

        articleNotesService.deleteById(articleNotes.getId());
        return ResponseUtil.ok(articleNotes);
    }


    //获取access_token
    @GetMapping("/token")
    public String getAcessToken(){
        String requestUrl=token_url.replace("APPID",appid).replace("APPSECRET",secret);
        JSONObject re = HttpClientUtil.doGet(requestUrl);
        return re.getString("access_token");
    }


    /**
     * 文章详情模块二维码图片生成及保存
     * @author leiqiang
     * @date 2018-5-31 14:15:07
     */
    public void saveCode(Integer article_id,Integer notesId,String name){
        String path=articledetail_url.replace("ARTICLEID",Integer.toString(article_id)).replace("NOTESID",Integer.toString(notesId)).replace("NAME",name);
        String token=getAcessToken();
        ArticleNotes notes=new ArticleNotes();
        JSONObject object=new JSONObject();
        object.put("path",path);
        object.put("width",430);//小程序二维码宽度
        String requestUrl=create_codeA_url.replace("ACCESS_TOKEN",token);
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
            notes.setId(notesId);
            notes.setCode_url(filePath+newFileName);
            articleNotesService.update(notes);
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
