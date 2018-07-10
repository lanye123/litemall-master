package org.linlinjava.litemall.admin.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.admin.util.bcrypt.HttpClientUtil;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.WxConfig;
import org.linlinjava.litemall.db.service.LearnPlannerService;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.WxConfigService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/planner")
public class LearnPlannerController {
    @Autowired
    private LearnPlannerService learnPlannerService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private LitemallAdminService litemallAdminService;
    @Autowired
    private LitemallUserService litemallUserService;
    @Value("${create_codeB.url}")
    private String create_codeB_url;
    @Value("${web.upload-path}")
    private String webUploadPath;

    private Log logger = LogFactory.getLog(LearnPlannerController.class);

    @GetMapping("/list")
    public Object list(@LoginAdmin String telephone,String openid,
                       Integer deptId,Integer buId,Integer corpsId,Integer transitionId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        order = "create_date desc";
        List<LearnPlanner> userList = learnPlannerService.querySelective2(telephone, openid, deptId,buId,corpsId,transitionId,page, limit, sort, order);
        int total = learnPlannerService.countSeletive(telephone, openid, deptId,buId,corpsId,transitionId,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody LearnPlanner learnPlanner){
        logger.debug(learnPlanner);

        learnPlannerService.add(learnPlanner);
        return ResponseUtil.ok(learnPlanner);
    }

    @PostMapping("/update")
    public Object update(@RequestBody LearnPlanner learnPlanner){
        logger.debug(learnPlanner);

        learnPlannerService.update(learnPlanner);
        return ResponseUtil.ok(learnPlanner);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){
        learnPlannerService.deleteById(id);
        return ResponseUtil.ok();
    }

    @PostMapping("add")
    public Object add(@RequestBody LearnPlanner planner){
        if(StringUtils.isEmpty(planner.getOpenid()) || StringUtils.isEmpty(planner.getBuId()) || StringUtils.isEmpty(planner.getCorpsId()) ){
            return ResponseUtil.ok();
        }
        LearnPlanner learnPlannerDb = learnPlannerService.queryByOpenid(planner.getOpenid());
        LitemallUser user=litemallUserService.queryByOid(planner.getOpenid());
        if(user!=null&&StringUtils.isEmpty(user.getPid())){
          user.setPid(user.getWeixinOpenid());
          litemallUserService.update(user);
        }
        if(learnPlannerDb!=null){
            return ResponseUtil.ok(learnPlannerDb);
        }
        WxConfig config=wxConfigService.getToken();
        JSONObject object=new JSONObject();
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
                litemallAdminService.createAdmin(planner);
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
