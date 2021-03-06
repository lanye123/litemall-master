package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.domain.ArticleNotes;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.service.ArticleNotesService;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.service.WxConfigService;
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
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private MedalDetailsService medalDetailsService;

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
    @Value("${main.url}")
    private String mainUrl;

    @GetMapping("/list")
    public Object list(String artileName, String name,String no,String content,Integer artileId,Integer sortNo,Integer status,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        order = "create_date desc";
        List<ArticleNotes> articleNotesList = articleNotesService.querySelective(artileName, name,no,content,sortNo,artileId,status, page, limit, sort, order);
        int total = articleNotesService.countSelective(artileName, name,no,content,sortNo, artileId,status,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        //点亮次数 分享次数
        LitemallUser user;
        for(ArticleNotes articleNotes:articleNotesList){
            articleNotes.setShineCount(medalDetailsService.countSeletive(articleNotes.getId(),articleNotes.getArtileId(),null,null,null,null,null,"",""));
            if(articleNotes.getCreateDate().contains(".0")){
                articleNotes.setCreateDate(articleNotes.getCreateDate().substring(0,articleNotes.getCreateDate().length()-2));
            }
            if(articleNotes.getOnlineDate()!=null && articleNotes.getOnlineDate().contains("00:00:00.0")){
                articleNotes.setOnlineDate(articleNotes.getOnlineDate().substring(0,articleNotes.getOnlineDate().length()-11));
            }
        }

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

    @PostMapping("/online")
    public Object online(@RequestBody ArticleNotes articleNotes){
        if(articleNotes == null){
            return ResponseUtil.badArgument();
        }
        ArticleNotes articleNotesDb = articleNotesService.findByID(articleNotes.getId());
        if(articleNotesDb==null){
            return ResponseUtil.ok();
        }
        if(articleNotesDb.getStatus()==0){
            articleNotesDb.setStatus((byte)1);
            articleNotesService.update(articleNotesDb);
        }else if(articleNotesDb.getStatus()==1){
            articleNotesDb.setStatus((byte)0);
            articleNotesService.update(articleNotesDb);
        }
        return ResponseUtil.ok(articleNotesDb);
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

    /**
     * 文章详情模块二维码图片生成及保存
     * @author leiqiang
     * @date 2018-5-31 14:15:07
     */
    @PostMapping("/code")
    public void saveCode(Integer article_id,Integer notesId,String name){
        WxConfig config=wxConfigService.getToken();
        String path=articledetail_url.replace("ARTICLEID",Integer.toString(article_id)).replace("NOTESID",Integer.toString(notesId)).replace("NAME",name);
        ArticleNotes notes=new ArticleNotes();
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
            notes.setId(notesId);
            notes.setCode_url(datapath);
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

  /**
   * 文章详情模块二维码图片生成及保存
   * @author leiqiang
   * @date 2018-5-31 14:15:07
   */
  @PostMapping("/codeb")
  public void saveCodeB(Integer article_id,Integer notesId,String name){
    WxConfig config=wxConfigService.getToken();
    String params="article_id=ARTICLEID&notesId=NOTESID&name=NAME&index=0";
    String scene=params.replace("ARTICLEID",Integer.toString(article_id)).replace("NOTESID",Integer.toString(notesId)).replace("NAME",name);
    ArticleNotes notes=new ArticleNotes();
    JSONObject object=new JSONObject();
    object.put("path",mainUrl);
    object.put("scene",scene);
    object.put("width",430);//小程序二维码宽度
    String requestUrl=create_codeB_url.replace("ACCESS_TOKEN",config.getAccessToken());
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
      notes.setId(notesId);
      notes.setCode_url(datapath);
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
