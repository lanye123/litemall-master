package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.CsDetail;
import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTitle;
import org.linlinjava.litemall.db.service.CsDetailService;
import org.linlinjava.litemall.db.service.CsOptionService;
import org.linlinjava.litemall.db.service.CsTestService;
import org.linlinjava.litemall.db.service.CsTitleService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 趣味测试接口
 * leiq
 * 2018-7-16 11:07:37
 */

@RestController
@RequestMapping("/wx/cstest")
public class CsTestController {
  @Autowired
  private CsTestService csTestService;
  @Autowired
  private CsDetailService csDetailService;
  @Autowired
  private CsTitleService csTitleService;
  @Autowired
  private CsOptionService csOptionService;
  //普通类型测试列表
  @GetMapping("list")
  public List<CsTest> list(Integer isHot){
    Map<String,Object> data=new HashMap<>();
    List<CsTest> testList=csTestService.list(isHot);
    return testList;
  }

  //根据测试题ID返回对应记录
  @GetMapping("read")
  public CsTest read(Integer id){
    return csTestService.read(id);
  }

  //根据测试题ID级联返回测试题目及选项信息
  @GetMapping("option")
  public CsTest option(Integer id){
    return csTestService.cascate(id);
  }

  //添加详情流水记录
  @PostMapping("addDetail")
  public void addDetail(@RequestBody CsDetail detail){
    csDetailService.addDetail(detail);
  }
}
