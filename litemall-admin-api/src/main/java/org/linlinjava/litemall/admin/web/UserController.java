package org.linlinjava.litemall.admin.web;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private LitemallUserService userService;

    @GetMapping("/list")
    public Object list(
                       String username, String mobile,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        order = "add_time desc";
        List<LitemallUser> userList = userService.querySelective(username, mobile, "","",page, limit, sort, order);
        int total = userService.countSeletive(username, mobile,"", "",page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/listPlanner")
    public Object listPlanner(
                       Integer deptId,Integer buId,Integer corpsId,String pid,
                       Integer transitionId,String startDate, String endDate,String mobile,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        List<LitemallUser> userList = userService.queryPlannerSelective(pid,deptId,buId,corpsId,transitionId,startDate,endDate, mobile,page, limit, sort, order);
        int total=0;
        total= userService.countPlannerSeletive(pid,deptId,buId,corpsId,transitionId,startDate,endDate, mobile,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/username")
    public Object username(String username){
        if(StringUtil.isEmpty(username)){
            return ResponseUtil.fail402();
        }

        int total = userService.countSeletive(username, "","" ,"",null, null, null, null);
        if(total == 0){
            return ResponseUtil.ok("不存在");
        }
        return ResponseUtil.ok("已存在");
    }


    @PostMapping("/create")
    public Object create(@RequestBody LitemallUser user){
        logger.debug(user);

        userService.add(user);
        return ResponseUtil.ok(user);
    }

    @PostMapping("/update")
    public Object update(@RequestBody LitemallUser user){
        logger.debug(user);

        userService.update(user);
        return ResponseUtil.ok(user);
    }

    @GetMapping("/validate")
    public Object validate(String account){
        Map<String,Object> data=new HashMap<>();
        if(StringUtil.isEmpty(account)){
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = null;
        userList=userService.queryByAccount(account);
        if(userList != null && userList.size()>0){
            data.put("data",userList);
        }
        return ResponseUtil.ok(data);
    }
    @GetMapping("/tjCorpsPie")
    public Object tjCorpsPie(){
        List<LitemallUser> corpsList=userService.tjCorpsPie();
        List nameList=new ArrayList();
        if(corpsList.size()>0&&corpsList!=null){
            for (LitemallUser user:corpsList) {
                nameList.add(user.getName());
            }
        }
        Map<String,Object> data=new HashMap<>();
        data.put("series",corpsList);
        data.put("legend",nameList);
        return ResponseUtil.ok(data);
    }

    @PostMapping("/updateStatus")
    public Object updateStatus(Integer userId,String flag){
        if(userId == null){
            return ResponseUtil.badArgumentValue();
        }

        LitemallUser userDb = userService.findById(userId);
        if(userDb==null){
            return ResponseUtil.ok();
        }
        if("1".equals(flag)){
            if(userDb.getIsjoin()==0){
                userDb.setIsjoin(1);
                userService.update(userDb);
            }else if(userDb.getIsjoin()==1){
                userDb.setIsjoin(0);
                userService.update(userDb);
            }
        }else if("2".equals(flag)){
            if(userDb.getIntention()==0){
                userDb.setIntention(1);
                userService.update(userDb);
            }else if(userDb.getIntention()==1){
                userDb.setIntention(0);
                userService.update(userDb);
            }
        }else if("3".equals(flag)){
            if(userDb.getIssigin()==0){
                userDb.setIssigin(1);
                userService.update(userDb);
            }else if(userDb.getIssigin()==1){
                userDb.setIssigin(0);
                userService.update(userDb);
            }
        }else {
            return ResponseUtil.fail(110,"不支持的参数类型");
        }
        return ResponseUtil.ok(userDb);
    }

}
