package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.domain.Article;
import org.linlinjava.litemall.db.service.ArticleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传接口开发
    *@Author:LeiQiang
    *@Description:
    *@Date:0:01 2018/5/11
    */
@RestController
@RequestMapping("/wx/upload")
public class UploadController {
    @Autowired
    private ArticleService articleService;
    public static final String ROOT = "upload-dir";
    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    /**
     * 文件上传根目录(在Spring的application.yml的配置文件中配置):<br>
     * web:
     * upload-path: （jar包所在目录）/resources/static/
     */
    @Value("${web.upload-path}")
    private String webUploadPath;
     /** 文章管理模块图片上传
     * @param file 图片
     * @param articleId 文章ID
     * @return
     */
    @PostMapping(value = "/imageUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object imageUpload(@RequestParam("file") MultipartFile file, Integer articleId) {
        Map<String, Object> map = new HashMap<>();
        if (!file.isEmpty()) {
            if (file.getContentType().contains("image")) {
                try {
                    String temp = "images" + File.separator + "upload" + File.separator;
                    // 获取图片的文件名
                    String fileName = file.getOriginalFilename();
                    // 获取图片的扩展名
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    // 数据库保存的目录
                    String datdDirectory = temp.concat(String.valueOf(articleId)).concat(File.separator);
                    // 文件路径
                    String filePath = webUploadPath.concat(datdDirectory);

                    File dest = new File(filePath, newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    // 判断文章是否上传的有图片，如果有则物理删除老照片
                    Article article = articleService.findById(articleId);
                    if (StringUtils.isNotBlank(article.getPhotoUrl())) {
                        String oldFilePath = webUploadPath.concat(article.getPhotoUrl());
                        File oldFile = new File(oldFilePath);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    // 上传到指定目录
                    file.transferTo(dest);
                    // 将反斜杠转换为正斜杠
                    String data = datdDirectory.replaceAll("\\\\", "/") + newFileName;
                    map.put("tempPath",temp);
                    map.put("fileName",fileName);
                    map.put("extensionName",extensionName);
                    map.put("newFileName",newFileName);
                    map.put("filePath",filePath);
                    map.put("data",data);
                    ResponseUtil.ok(map);
                } catch (IOException e) {
                    ResponseUtil.fail(0,"上传失败");
                }
            } else {
                ResponseUtil.fail(0,"上传的文件不是图片类型，请重新上传!");
            }
        } else {
            return ResponseUtil.fail(0,"上传失败，请选择要上传的图片!");
        }
        return ResponseUtil.ok(map);
    }

    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fileupload(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getSize());
        Object object=null;
        Map<String, Object> map = new HashMap<>();
        if (!file.isEmpty()) {
                try {
                    String temp = "images" + File.separator + "upload" + File.separator;
                    // 获取图片的文件名
                    String fileName = file.getOriginalFilename();
                    // 获取图片的扩展名
                    String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1).trim().toLowerCase();
                    //String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    // 文件路径
                    String filePath = webUploadPath.concat(temp);

                    File dest = new File(filePath, newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    // 上传到指定目录
                    file.transferTo(dest);
                    // 将反斜杠转换为正斜杠
                    String data = temp.replaceAll("\\\\", "/") + newFileName;
                    map.put("tempPath", temp);
                    map.put("fileName", fileName);
                    map.put("extensionName", extensionName);
                    map.put("newFileName", newFileName);
                    map.put("filePath", filePath);
                    map.put("data", data);
                    object=ResponseUtil.ok(map);
                } catch (IOException e) {
                    object=ResponseUtil.fail(501, "文件大小超出限制10M，请从新选择");
                }
            }
        return object;
    }


    @GetMapping(value = "/filelname")
    public Object getFile(String filename) {
        try {
            return ResponseUtil.ok(resourceLoader.getResource("file:" + Paths.get(webUploadPath, filename).toString()));
        } catch (Exception e) {
            return ResponseUtil.fail();
        }
    }
}

