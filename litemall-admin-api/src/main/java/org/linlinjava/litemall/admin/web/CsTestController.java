package org.linlinjava.litemall.admin.web;

import org.linlinjava.litemall.db.domain.CsOption;
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
                       String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<CsTitle> csTitleList = csTitleService.querySelective(title, testId, page, limit, sort, order);
        int total = csTitleService.countSelective(title, testId);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", csTitleList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/optionList")
    public Object optionList(String optionName,Integer testId,Integer titleId,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                            String sort, @RequestParam(value = "order", defaultValue = "create_date desc")String order){

        List<CsOption> csOptionList = csOptionService.querySelective(optionName, testId,titleId, page, limit, sort, order);
        int total = csOptionService.countSelective(optionName,testId,titleId);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", csOptionList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/createCsTest")
    public Object createCsTest(@RequestBody LinkedHashMap csTest){
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

    @PostMapping("/updateCsTest")
    public Object updateCsTest(@RequestBody LinkedHashMap csTest){
        if(csTest.get("id")==null){
            return ResponseUtil.badArgument();
        }
            CsTest test = csTestService.findById((Integer) csTest.get("id"));
        if(test==null){
            return ResponseUtil.badArgument();
        }
        if(csTest.get("title")==null){
            return ResponseUtil.badArgument();
        }
        if(csTest.get("topics")==null){
            return ResponseUtil.badArgument();
        }
        test.setTitle((String) csTest.get("title"));
        test.setSubTitle((String) csTest.get("subTitle"));
        test.setSortNo(Integer.valueOf((String) csTest.get("sortNo")));
        test.setType(Integer.valueOf((String) csTest.get("type")));
        test.setIsHot(Integer.valueOf((String) csTest.get("isHot")));
        test.setPicUrl((String) csTest.get("picUrl"));
        csTestService.update(test);
        List titles = (ArrayList) csTest.get("topics");
        CsTitle csTitleDb;
        LinkedHashMap title;
        for(Object csTitle:titles){
            title = (LinkedHashMap) csTitle;
            csTitleDb = csTitleService.findById((Integer)title.get("id"));
            if(csTitleDb==null){
                csTitleDb.setSortNo(Integer.valueOf((String) title.get("no")));
                csTitleDb.setTitle((String) title.get("value"));
                csTitleDb.setStatus(Integer.valueOf((String) title.get("status")));
                csTitleDb.setTestId(test.getId());
                csTitleService.add(csTitleDb);
            }else{
                csTitleDb = new CsTitle();
                csTitleDb.setSortNo(Integer.valueOf((String) title.get("no")));
                csTitleDb.setTitle((String) title.get("value"));
                csTitleDb.setStatus(Integer.valueOf((String) title.get("status")));
                csTitleDb.setTestId(test.getId());
                csTitleService.update(csTitleDb);
            }
        }
        return ResponseUtil.ok(csTest);
    }

    @PostMapping("/deleteCsTitle")
    public Object deleteCsTitle(Integer id){
        csTitleService.deleteById(id);
        return ResponseUtil.ok();
    }

    @PostMapping("/deleteCsTest")
    public Object deleteCsTest(Integer id){
        csTestService.deleteById(id);
        return ResponseUtil.ok();
    }

    @PostMapping("/deleteCsOption")
    public Object deleteCsOption(Integer id){
        csOptionService.deleteById(id);
        return ResponseUtil.ok();
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
