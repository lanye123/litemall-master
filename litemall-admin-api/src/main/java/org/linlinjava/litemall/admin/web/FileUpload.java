package org.linlinjava.litemall.admin.web;

        import java.io.File;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;

        import org.apache.commons.lang3.StringUtils;
        import org.linlinjava.litemall.db.domain.Article;
        import org.linlinjava.litemall.db.domain.ResultVo;
        import org.linlinjava.litemall.db.service.ArticleService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.MediaType;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * @author Fly
 *
 */
@RestController
@RequestMapping("/fileupload")
public class FileUpload {

    /**
     * 用户管理 -> 业务层
     */
    @Autowired
    private ArticleService articleService;

    /**
     * 文件上传根目录(在Spring的application.yml的配置文件中配置):<br>
     * web:
     * upload-path: （jar包所在目录）/resources/static/
     */
    @Value("${web.upload-path}")
    private String webUploadPath;

    /**
     * ResultVo是一个对象，包含：
     * private int errorCode;
     * private String errorMsg;
     * private Integer total;
     * private Object data;
     */

    /**
     * 基于用户标识的头像上传
     * @param file 图片
     * @param articleId 用户标识
     * @return
     */
    @PostMapping(value = "/fileupload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo fileUpload(@RequestParam("file") MultipartFile file, Integer articleId) {
        ResultVo resultVo = new ResultVo();
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
                    // 判断文章是否上传的有图片
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

                    // 将图片流转换进行BASE64加码
                    //BASE64Encoder encoder = new BASE64Encoder();
                    //String data = encoder.encode(file.getBytes());

                    // 将反斜杠转换为正斜杠
                    String data = datdDirectory.replaceAll("\\\\", "/") + newFileName;
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("file", data);
                    resultVo.setData(resultMap);
                    resultVo.setError(1, "上传成功!");
                } catch (IOException e) {
                    resultVo.setError(0, "上传失败!");
                }
            } else {
                resultVo.setError(0, "上传的文件不是图片类型，请重新上传!");
            }
            return resultVo;
        } else {
            resultVo.setError(0, "上传失败，请选择要上传的图片!");
            return resultVo;
        }
    }

}

