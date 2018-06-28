package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.service.LearnPlannerService;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/admin/sunlands/planner")
public class LearnPlannerController {
    @Autowired
    private LearnPlannerService learnPlannerService;
    @Autowired
    private WxConfigService wxConfigService;
    @Value("${create_codeB.url}")
    private String create_codeB_url;
    @Value("${web.upload-path}")
    private String webUploadPath;

    @PostMapping("add")
    public Object add(@RequestBody LearnPlanner planner){
        if(StringUtils.isEmpty(planner.getOpenid()) || StringUtils.isEmpty(planner.getBuId()) || StringUtils.isEmpty(planner.getCorpsId()) ){
            return ResponseUtil.ok();
        }
        LearnPlanner learnPlannerDb = learnPlannerService.queryByOpenid(planner.getOpenid());
        if(learnPlannerDb!=null){
            return ResponseUtil.ok(learnPlannerDb);
        }
        WxConfig config=wxConfigService.getToken();
        JSONObject object=new JSONObject();
        LearnPlanner learnPlanner=new LearnPlanner();
        object.put("path","pages/card/main");
        object.put("width",430);//小程序二维码宽度
        object.put("scene",planner.getOpenid());
        String requestUrl=create_codeB_url.replace("ACCESS_TOKEN",config.getAccessToken());
        InputStream i=HttpClientUtil.doPostInstream(requestUrl,object);
        byte[] data = new byte[1024];
        int len = -1;
        FileOutputStream fileOutputStream = null;
        try {
            String temp = "images" + File.separator + "planner" + File.separator;
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

            planner.setCodeUrl(datapath);
            learnPlannerService.add(planner);
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
        return ResponseUtil.ok(planner);

    }



}
