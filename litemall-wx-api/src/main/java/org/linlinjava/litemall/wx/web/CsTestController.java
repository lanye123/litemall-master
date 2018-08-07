package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
  @Autowired
  private CsResultService csResultService;
  @Autowired
  private LitemallUserService litemallUserService;
  @Autowired
  private IntegretionDetailService integretionDetailService;
  @Autowired
  private CsJieguoService csJieguoService;

  private BufferedImage image;
  private int csimageWidth=750;
  private int csimageHeight=1161;
  @Value("${web.upload-path}")
  private String webUploadPath;
  @Value("${serverurl}")
  private String serverurl;
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

/**
 * @Author leiqiang
 * @Description //TODO 保存用户做题结果流水记录
 * @Date   2018/7/16 11:30
 * @Param  [detail]
 * @return void
 **/
  @PostMapping("addDetail")
  public void addDetail(@RequestBody CsDetail detail){
    CsDetail detail1=csDetailService.retrieve(detail.getUserId(),detail.getTestId(),detail.getTitleId());
      if(detail1==null)
        csDetailService.addDetail(detail);//不存在记录则创建
      else{
        //存在记录则检查选项是否一致，否则更新记录
        if(detail.getOptionId()!=detail1.getOptionId()){
          detail1.setOptionId(detail.getOptionId());
          detail1.setAccount(detail.getAccount());
          csDetailService.update(detail1);
        }

      }

  }
  /**
   * @Author leiqiang
   * @Description //TODO 根据选项不同动态显示下一题趣味测试内容接口
   * @Date   2018/7/16 11:49
   * @Param  [title_id]
   * @return void
   **/
  @GetMapping("title")
  public CsTitle title(Integer pid){
   return csTitleService.option(pid);
  }

  @GetMapping("optionDync")
  public CsTest optionDync(Integer id){
    return csTestService.cascateDync(id);
  }
  /**
   * @Author leiqiang
   * @Description //TODO 检查该项测试用户是否已经测试，如果已经测试则直接返回测试结果
   * @Date   2018/7/18 16:07
   * @Param  [testId, userId]
   * @return java.lang.Object
   **/
  @GetMapping("check")
  public Object check(Integer testId,Integer userId){
    Map data = new HashMap();
    CsTest test=csTestService.findById(testId);
    if(test!=null&&StringUtils.isNotEmpty(test.getInfo()))
      data.put("info",test.getInfo());
   CsJieguo jg=csJieguoService.queryById(testId,userId);
   if(jg!=null){
     data.put("checked",1);
     data.put("CsJieguo",jg);
   }else
     data.put("checked",0);
    return ResponseUtil.ok(data);
  }

  /**
   * @Author leiqiang
   * @Description //TODO 根据用户ID和测试ID查看测试结果
   * @Date   2018/7/16 17:56
   * @Param  [testId, userId]
   * @return java.lang.Object
   **/
  @GetMapping("look")
  public Object look(Integer testId,Integer userId){
    Map data = new HashMap();
    CsTest test=csTestService.findById(testId);
    if(test!=null&&StringUtils.isNotEmpty(test.getInfo()))
      data.put("info",test.getInfo());
    Integer account=0;
    account=csDetailService.sumAccount(testId,userId);//统计该用户该项测试最终得分情况
    if(account!=0){
      CsResult result= csResultService.getPicUrl(testId,account);//根据用户测试得分和测试ID得出背景图片url
      String imgurl= result.getPicUrl();
      //根据图片url及用户ID生成最终图片
      LitemallUser user = litemallUserService.findById(userId);
      String nickname=user.getNickname();
      String avatar=user.getAvatar();
      image = new BufferedImage(csimageWidth, csimageHeight, BufferedImage.TYPE_INT_RGB);
      //设置图片的背景色
      Graphics2D main = image.createGraphics();
      main.fillRect(0, 0, csimageWidth, csimageHeight);


      //***********************插入背景图
      Graphics mainPic = image.getGraphics();
      BufferedImage bimg = null;
      try {
        URL url2 = new URL(imgurl);
        URLConnection con2 = url2.openConnection();
        //不超时
        con2.setConnectTimeout(0);

        //不允许缓存
        con2.setUseCaches(false);
        con2.setDefaultUseCaches(false);
        InputStream is2 = con2.getInputStream();

        //先读入内存
        ByteArrayOutputStream buf2 = new ByteArrayOutputStream(8192);
        byte[] b2 = new byte[1024];
        int len2;
        while ((len2 = is2.read(b2)) != -1) {
          buf2.write(b2, 0, len2);
        }
        //读图像
        is2 = new ByteArrayInputStream(buf2.toByteArray());
        bimg = javax.imageio.ImageIO.read(is2);
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (bimg != null) {
        mainPic.drawImage(bimg, 0, 0, csimageWidth, csimageHeight, null);
        mainPic.dispose();
      }

      //***********************插入用户头像

      if (StringUtils.isNotEmpty(avatar)) {
        Graphics mainPic3 = image.getGraphics();
        BufferedImage bimg3 = null;
        try {
          URL url3 = new URL(avatar);
          URLConnection con3 = url3.openConnection();
          //不超时
          con3.setConnectTimeout(0);

          //不允许缓存
          con3.setUseCaches(false);
          con3.setDefaultUseCaches(false);
          InputStream is3 = con3.getInputStream();

          //先读入内存
          ByteArrayOutputStream buf3 = new ByteArrayOutputStream(8192);
          byte[] b3 = new byte[1024];
          int len3;
          while ((len3 = is3.read(b3)) != -1) {
            buf3.write(b3, 0, len3);
          }
          //读图像
          is3 = new ByteArrayInputStream(buf3.toByteArray());
          bimg3 = ImageIO.read(is3);
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (bimg3 != null) {
          mainPic3.drawImage(bimg3, 65, csimageHeight - 120, 60, 60, null);
          mainPic3.dispose();
        }
      }
      Graphics2D tip4 = image.createGraphics();
      Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 16);
      //tip4.setColor(new Color(51, 129, 246));
      tip4.setFont(tipFont4);
      tip4.drawString(nickname, 135, csimageHeight - 95);

      Graphics2D tip5 = image.createGraphics();
      Font tipFont5 = new Font("苹方 常规", Font.PLAIN, 16);
      //tip4.setColor(new Color(51, 129, 246));
      tip5.setFont(tipFont5);
      tip5.drawString("一本书，一视界", 135, csimageHeight - 75);

      String temp = "images" + File.separator + "temp" + File.separator;
      // 新的图片文件名 = 获取时间戳+"."图片扩展名
      String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
      // 文件路径
      String filePath = webUploadPath.concat(temp);

      File dest = new File(filePath, newFileName);
      if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
      }
      createImage(filePath + newFileName);
      System.out.println(filePath + newFileName);
      // 将反斜杠转换为正斜杠
      String dataPath = temp.replaceAll("\\\\", "/") + newFileName;
      data.put("imgUrl", serverurl + dataPath);
      data.put("desk_url", filePath + newFileName);
      //首次测试加10积分
      Integer integretionCount=0;
      integretionCount=integretionDetailService.queryByTest(testId,userId);
      if(integretionCount==0){
        IntegretionDetail detail=new IntegretionDetail();
        detail.setType((byte) 22);
        detail.setUserId(String.valueOf(userId));
        detail.setIntegretionId(String.valueOf(testId));
        detail.setAmount(10);//趣味测试完成后获得10积分
        integretionDetailService.add(detail);
      }
      //将结果保存进csjieguo中
      CsJieguo jg=csJieguoService.queryById(testId,userId);
      if(jg==null){
        CsJieguo csjg=new CsJieguo();
        csjg.setAccount(account);
        csjg.setTestId(testId);
        csjg.setUserId(userId);
        csjg.setImgUrl(serverurl + dataPath);
        csJieguoService.add(csjg);
      }else{
        jg.setAccount(account);
        jg.setImgUrl(serverurl + dataPath);
        csJieguoService.update(jg);
      }
    }
    return ResponseUtil.ok(data);
  }

  public void createImage(String fileLocation) {
    if(image != null){
      try {
        String formatName = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
        ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File(fileLocation) /* target */ );
                /*FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(image);*/
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
