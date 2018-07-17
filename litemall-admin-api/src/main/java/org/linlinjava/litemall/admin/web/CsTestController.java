package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTitle;
import org.linlinjava.litemall.db.service.CsDetailService;
import org.linlinjava.litemall.db.service.CsOptionService;
import org.linlinjava.litemall.db.service.CsTestService;
import org.linlinjava.litemall.db.service.CsTitleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin/sunlands/cstest")
public class CsTestController {
    @Autowired
    private CsTestService csTestService;
    @Autowired
    private CsDetailService csDetailService;
    @Autowired
    private CsTitleService csTitleService;
    @Autowired
    private CsOptionService csOptionService;

    @GetMapping("/testList")
    public Object testList(String title, Integer type,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<CsTest> csTestList = csTestService.querySelective(title, type,null, page, limit, sort, order);
        int total = csTestService.countSelective(title, type,null);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", csTestList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/titleList")
    public Object titleList(String title, Integer testId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, @RequestParam(value = "order", defaultValue = "")String order){

        List<CsTitle> csTitleList = csTitleService.querySelective(title, testId, page, limit, sort, order);
        int total = csTitleService.countSelective(title, testId);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", csTitleList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/createCsTest")
    public Object create(@RequestBody LinkedHashMap csTest){
        if(csTest.get("title")==null){
            return ResponseUtil.badArgument();
        }
        if(csTest.get("topics")==null){
            return ResponseUtil.badArgument();
        }
        CsTest test = new CsTest();
        test.setTitle((String) csTest.get("title"));
        test.setSubTitle((String) csTest.get("subTitle"));
        test.setSortNo(Integer.valueOf((String) csTest.get("sortNo")));
        test.setType(Integer.valueOf((String) csTest.get("type")));
        test.setIsHot(Integer.valueOf((String) csTest.get("isHot")));
        test.setPicUrl((String) csTest.get("picUrl"));
        csTestService.add(test);
        List titles = (ArrayList) csTest.get("topics");
        CsTitle csTitleDb = new CsTitle();
        LinkedHashMap title;
        for(Object csTitle:titles){
            title = (LinkedHashMap) csTitle;
            csTitleDb.setSortNo(Integer.valueOf((String) title.get("no")));
            csTitleDb.setTitle((String) title.get("value"));
            csTitleDb.setStatus(Integer.valueOf((String) title.get("status")));
            csTitleDb.setTestId(test.getId());
            csTitleService.add(csTitleDb);
        }
        return ResponseUtil.ok(csTest);
    }

    //
    //    }
    //        return ResponseUtil.ok(data);
    //        data.put("items",sysBuList);
    //        List<SysBu> sysBuList = sysBuService.queryByDeptId(deptId);
    //        Map<String, Object> data = new HashMap<>();
    //    public Object getBuByDeptId(Integer deptId){
//    @GetMapping("/deptId")
//
//    @PostMapping("/update")
//    public Object update(@RequestBody SysBu sysBu){
//        logger.debug(sysBu);
//
//        sysBuService.update(sysBu);
//        return ResponseUtil.ok(sysBu);
//    }
//
//    @PostMapping("/delete")
//    public Object delete(Integer id){
//        sysBuService.deleteById(id);
//        return ResponseUtil.ok();
//    }
}
