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
        test.setSortNo((Integer) csTest.get("sortNo"));
        test.setType((Integer) csTest.get("type"));
        test.setIsHot((Integer) csTest.get("isHot"));
        test.setPicUrl((String) csTest.get("picUrl"));
        csTestService.add(test);
        List titles = (ArrayList) csTest.get("topics");
        List options ;
        CsTitle csTitleDb = new CsTitle();
        csTitleDb.setTestId(test.getId());
        CsOption csOptionDb = new CsOption();
        csOptionDb.setTestId(test.getId());
        LinkedHashMap title;
        LinkedHashMap option;
        for(Object csTitle:titles){
            title = (LinkedHashMap) csTitle;
            csTitleDb.setSortNo((Integer) title.get("sortNo"));
            csTitleDb.setTitle((String) title.get("title"));
            csTitleDb.setStatus((Integer) title.get("status"));
            csTitleService.add(csTitleDb);
            options = (ArrayList) title.get("optionList");
            csOptionDb.setTitleId(csTitleDb.getId());
            for (Object csOption : options) {
                option = (LinkedHashMap) csOption;
                csOptionDb.setSortNo((Integer) option.get("sortNo"));
                csOptionDb.setOptionName((String) option.get("optionName"));
                csOptionDb.setAccount((Integer) option.get("account"));
                csOptionService.add(csOptionDb);
            }
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
        test.setSortNo((Integer) csTest.get("sortNo"));
        test.setType((Integer) csTest.get("type"));
        test.setIsHot((Integer) csTest.get("isHot"));
        test.setPicUrl((String) csTest.get("picUrl"));
        csTestService.update(test);
        List titles = (ArrayList) csTest.get("topics");
        List options;
        CsTitle csTitleDb;
        CsOption csOptionDb = null;
        LinkedHashMap title;
        LinkedHashMap option;
        for(Object csTitle:titles){
            title = (LinkedHashMap) csTitle;
            csTitleDb = csTitleService.findById((Integer)title.get("id"));
            if(csTitleDb==null){
                //题目不存在创建
                csTitleDb = new CsTitle();
                csTitleDb.setTestId(test.getId());
                csTitleDb.setSortNo((Integer) title.get("sortNo"));
                csTitleDb.setTitle((String) title.get("title"));
                csTitleDb.setStatus((Integer) title.get("status"));
                csTitleService.add(csTitleDb);
                options = (ArrayList) title.get("optionList");
                for (Object csOption : options) {
                    option = (LinkedHashMap) csOption;
                    csOptionDb = new CsOption();
                    csOptionDb.setTitleId(csTitleDb.getId());
                    csOptionDb.setTestId(test.getId());
                    csOptionDb.setSortNo((Integer) option.get("sortNo"));
                    csOptionDb.setOptionName((String) option.get("optionName"));
                    csOptionDb.setAccount((Integer) option.get("account"));
                    csOptionDb.setPid((Integer) option.get("pid"));
                    csOptionService.add(csOptionDb);
                }
            }else{
                //题目存在更新
                csTitleDb.setSortNo((Integer) title.get("sortNo"));
                csTitleDb.setTitle((String) title.get("title"));
                csTitleDb.setStatus((Integer) title.get("status"));
                csTitleService.update(csTitleDb);
                options = (ArrayList) title.get("optionList");
                for (Object csOption : options) {
                    option = (LinkedHashMap) csOption;
                    csOptionDb = csOptionService.findById((Integer)option.get("id"));
                    if(csTitleDb==null){
                        //选项不存在创建
                        csOptionDb = new CsOption();
                        csOptionDb.setTitleId(csTitleDb.getId());
                        csOptionDb.setTestId(test.getId());
                        csOptionDb.setSortNo((Integer) option.get("sortNo"));
                        csOptionDb.setOptionName((String) option.get("optionName"));
                        csOptionDb.setAccount((Integer) option.get("account"));
                        csOptionDb.setPid((Integer) option.get("pid"));
                        csOptionService.add(csOptionDb);
                    }else{
                        //选项存在更新
                        csOptionDb.setTitleId(csTitleDb.getId());
                        csOptionDb.setTestId(test.getId());
                        csOptionDb.setSortNo((Integer) option.get("sortNo"));
                        csOptionDb.setOptionName((String) option.get("optionName"));
                        csOptionDb.setAccount((Integer) option.get("account"));
                        csOptionDb.setPid((Integer) option.get("pid"));
                        csOptionService.update(csOptionDb);
                    }
                }
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
