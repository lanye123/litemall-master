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
  @GetMapping("list")
  public List<CsTest> list(Integer isHot){
    Map<String,Object> data=new HashMap<>();
    List<CsTest> testList=csTestService.list(isHot);
    return testList;
  }
  @GetMapping("read")
  public CsTest read(Integer id){
    return csTestService.read(id);
  }

  @GetMapping("option")
  public CsTest option(Integer id){
    return csTestService.cascate(id);
  }

  @PostMapping("addDetail")
  public void addDetail(@RequestBody CsDetail detail){
    csDetailService.addDetail(detail);

  }
}
