package org.linlinjava.litemall.wx.web;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/wx/graphics")
public class GraphicsControllor {
    @Autowired
    private LitemallUserService litemallUserService;
    @Autowired
    private LitemallGoodsService litemallGoodsService;
    @Autowired
    private HelpOrderService helpOrderService;
    @Autowired
    private WxConfigService wxConfigService;
    @Autowired
    private HbPhotoHisService hbPhotoHisService;
    private final Log logger = LogFactory.getLog(GraphicsControllor.class);
    private BufferedImage image;
    private BufferedImage image2;
    private int imageWidth = 1500;  //图片的宽度
    private int imageHeight =2500; //图片的高度

  private int csimageWidth=750;
  private int csimageHeight=1161;
  private int helpimageHeight=1180;
    @Value("${web.upload-path}")
    private String webUploadPath;
    @Value("${serverurl}")
    private String serverurl;
    @Value("${codeurl}")
    private String codeurl;
    @Value("${create_codeB.url}")
    private String create_codeB_url;

    //生成图片文件
    @SuppressWarnings("restriction")
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
    @GetMapping("generatorPhoto")
    public Object graphicsGeneration(String date, String nickname, String content, String imgurl,String author,Integer userId,String codeUrl) {
        Map data=new HashMap();
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);


        //***********************插入中间广告图
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
            is2=new ByteArrayInputStream(buf2.toByteArray());
            bimg = javax.imageio.ImageIO.read(is2);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg!=null){
            mainPic.drawImage(bimg, 0, 0, imageWidth, 1066, null);
            mainPic.dispose();
        }

        //***********************插入二维码图片
        Graphics mainPic1 = image.getGraphics();
        BufferedImage bimg1 = null;
        try {
            URL url=null;
            if(StringUtils.isEmpty(codeUrl)){
                url = new URL(codeurl);
            }else{
                url = new URL(codeUrl);
            }

            URLConnection con = url.openConnection();
            //不超时
            con.setConnectTimeout(0);

            //不允许缓存
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            InputStream is = con.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                buf.write(b, 0, len);
            }
            //读图像
            is=new ByteArrayInputStream(buf.toByteArray());
            bimg1 = ImageIO.read(is);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg1!=null){
            mainPic1.drawImage(bimg1, 1050, 2100, 300, 300, null);
            mainPic1.dispose();
        }

        //***********************插入用户头像
        if(userId!=null){
            LitemallUser user=litemallUserService.findById(userId);
        if(StringUtils.isNotEmpty(user.getAvatar())){
            Graphics mainPic3 = image.getGraphics();
            BufferedImage bimg3 = null;
            try {
                URL url3 = new URL(user.getAvatar());
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
                is3=new ByteArrayInputStream(buf3.toByteArray());
                bimg3 = ImageIO.read(is3);
            } catch (Exception e) {e.printStackTrace();}

            if(bimg3!=null){
                mainPic3.drawImage(bimg3, 115, 2165, 160, 160, null);
                mainPic3.dispose();
            }
        }
        }
        //***********************插入圆圈圈
        Graphics mainPic5 = image.getGraphics();
        BufferedImage bimg5 = null;
        try {
            URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
            URLConnection con5 = url5.openConnection();
            //不超时
            con5.setConnectTimeout(0);

            //不允许缓存
            con5.setUseCaches(false);
            con5.setDefaultUseCaches(false);
            InputStream is5 = con5.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
            byte[] b5 = new byte[1024];
            int len5;
            while ((len5 = is5.read(b5)) != -1) {
                buf5.write(b5, 0, len5);
            }
            //读图像
            is5=new ByteArrayInputStream(buf5.toByteArray());
            bimg5 = ImageIO.read(is5);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg5!=null){
            mainPic5.drawImage(bimg5, 100, 2150, 200, 200, null);
            mainPic5.dispose();
        }
        //***********************设置下面的提示框

        Graphics2D tip = image.createGraphics();
        //设置区域颜色
        tip.setColor(new Color(247,247,247));
        tip.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.gray);
        //设置字体
        Font tipFont = new Font("苹方 常规", Font.PLAIN, 54);
        tip.setColor(Color.gray);
        tip.setFont(tipFont);
        //***********************设置下面的按钮块
        //***********************插入波塞冬logo
        Graphics mainPic4 = image.getGraphics();
        BufferedImage bimg4 = null;
        try {
            URL url4 = new URL("https://sunlands.ministudy.com/images/bosaidong.png");
            URLConnection con4 = url4.openConnection();
            //不超时
            con4.setConnectTimeout(0);

            //不允许缓存
            con4.setUseCaches(false);
            con4.setDefaultUseCaches(false);
            InputStream is4 = con4.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf4 = new ByteArrayOutputStream(8192);
            byte[] b4 = new byte[1024];
            int len4;
            while ((len4 = is4.read(b4)) != -1) {
                buf4.write(b4, 0, len4);
            }
            //读图像
            is4=new ByteArrayInputStream(buf4.toByteArray());
            bimg4 = ImageIO.read(is4);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg4!=null){
            mainPic4.drawImage(bimg4, 450, 1200, 572, 596, null);
            mainPic4.dispose();
        }

        //***********************设置下面的提示框

        /*Graphics2D tip2 = image.createGraphics();
        //设置区域颜色
        tip2.setColor(new Color(240,248,255));
        //填充区域并确定区域大小位置
        //tip2.fillRect(0, 1000, imageWidth, 1000);
        //设置字体颜色，先设置颜色，再填充内容
        tip2.setColor(Color.gray);
        //设置字体
        Font tipFont2 = new Font("苹方 常规", Font.PLAIN, 50);
        tip2.setFont(tipFont2);
        tip2.drawString("EVAN", 580, 2100);*/

        Graphics2D tip3 = image.createGraphics();
        //设置区域颜色
        tip3.setColor(new Color(247,247,247));
        //填充区域并确定区域大小位置
        //tip3.fillRect(0, btn2_2_top, imageWidth, H_tip);
        //设置字体颜色，先设置颜色，再填充内容
        tip3.setColor(new Color(40,40,40));
        //设置字体
        Font tipFont3 = new Font("苹方 常规", Font.PLAIN, 48);
        tip3.setFont(tipFont3);
        //tip3.drawString("", 300, 2230);
        tip3.drawString("一本书，一视界", 300, 2310);
        tip3.drawString(date, 605, 1140);
        System.out.println(author.length());
        int authorLength=author.length();
        int authorWidth = tip3.getFontMetrics().stringWidth(author);
        if (authorLength==1){
            tip3.drawString(author, authorWidth / 2+700, 1896);
        }else if (authorLength==2)
            tip3.drawString(author, authorWidth / 2+650, 1886);
        else if (authorLength==3)
            tip3.drawString(author, authorWidth / 2+600, 1886);
        else if (authorLength==4)
            tip3.drawString(author, authorWidth / 2+550, 1886);
        else if (authorLength==5)
            tip3.drawString(author, authorWidth / 2+500, 1886);
        else if (authorLength==6)
            tip3.drawString(author, authorWidth / 2+450, 1886);
        else if (authorLength==7)
            tip3.drawString(author, authorWidth / 2+400, 1886);
        else if (authorLength==8)
            tip3.drawString(author, authorWidth / 2+350, 1886);
        else if (authorLength==9)
            tip3.drawString(author, authorWidth / 2+300, 1886);
        else if (authorLength==10)
            tip3.drawString(author, authorWidth / 2+250, 1886);
        else if (authorLength==11)
            tip3.drawString(author, authorWidth / 2+200, 1886);
        else if (authorLength==12)
            tip3.drawString(author, authorWidth / 2+150, 1886);
        else if (authorLength==13)
            tip3.drawString(author, authorWidth / 2+100, 1886);
        else if (authorLength==14)
            tip3.drawString(author, authorWidth / 2+50, 1886);
        else if (authorLength==15)
            tip3.drawString(author, authorWidth / 2, 1886);
        else if (authorLength==16)
            tip3.drawString(author, authorWidth / 2-50, 1886);
        else if (authorLength==17)
            tip3.drawString(author, authorWidth / 2-100, 1886);
        else if (authorLength==18)
            tip3.drawString(author, authorWidth / 2-150, 1886);
        else if (authorLength==19)
            tip3.drawString(author, authorWidth / 2-200, 1886);
        else if (authorLength==20)
            tip3.drawString(author, authorWidth / 2-250, 1886);
        else
            tip3.drawString(author, authorWidth / 2-200, 1886);
        //tip3.drawLine(10,2200,1490,2200);

        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(new Color(40,40,40));
        String str=content;

        List getStrList=getStrList(str,22);
        System.out.println(getStrList);
        logger.info(getStrList);
        if(getStrList.size()>0){
            if(getStrList.size()>1){
                for (int i = 0; i <getStrList.size() ; i++) {
                    tip.drawString(getStrList.get(i).toString(), 155, 1410+i*100);
                }
            }else{
                int strWidth = tip.getFontMetrics().stringWidth(str);
                int length=str.length();
                if(length==10)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+180, imageWidth);
                else if(length==11)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+130, imageWidth);
                else if(length==12)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+80, imageWidth);
                else if(length==13)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+30, imageWidth);
                else if(length==14)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-30, imageWidth);
                else if(length==15)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-80, imageWidth);
                else if(length==16)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-130, imageWidth);
                else if(length==17)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-180, imageWidth);
                else if(length==18)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-230, imageWidth);
                else if(length==19)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-280, imageWidth);
                else if(length==20)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-330, imageWidth);
                else if(length==21)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-380, imageWidth);
                else if(length==22)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-430, imageWidth);
                else
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-480, imageWidth);

            }
        }
        Graphics2D tip4 = image.createGraphics();
        //设置区域颜色
        //tip4.setColor(new Color(247,247,247));
        //tip4.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        //tip4.setColor(new Color(51,129,246));
        //设置字体
        Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 48);
        tip4.setColor(new Color(51,129,246));
        tip4.setFont(tipFont4);
        tip4.drawString(nickname, 300, 2230);
        String temp = "images" + File.separator + "temp" + File.separator;
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        // 文件路径
        String filePath = webUploadPath.concat(temp);

        File dest = new File(filePath, newFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        createImage(filePath+newFileName);
        System.out.println(filePath+newFileName);
        // 将反斜杠转换为正斜杠
        String dataPath = temp.replaceAll("\\\\", "/") + newFileName;
        data.put("imgUrl",serverurl+dataPath);
        data.put("desk_url",filePath+newFileName);
        return ResponseUtil.ok(data);
    }


  @GetMapping("csResult")
  public Object csResult(String imgurl,Integer userId) {
    Map data = new HashMap();
      LitemallUser user = litemallUserService.findById(userId);
      String nickname=user.getNickname();
      String avatar=user.getAvatar();
      image = new BufferedImage(csimageWidth, csimageHeight, BufferedImage.TYPE_INT_RGB);
      //设置图片的背景色
      Graphics2D main = image.createGraphics();
      main.fillRect(0, 0, csimageWidth, csimageHeight);


      //***********************插入中间广告图
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
//      //***********************插入圆圈圈
//      Graphics mainPic5 = image.getGraphics();
//      BufferedImage bimg5 = null;
//      try {
//        URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
//        URLConnection con5 = url5.openConnection();
//        //不超时
//        con5.setConnectTimeout(0);
//
//        //不允许缓存
//        con5.setUseCaches(false);
//        con5.setDefaultUseCaches(false);
//        InputStream is5 = con5.getInputStream();
//
//        //先读入内存
//        ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
//        byte[] b5 = new byte[1024];
//        int len5;
//        while ((len5 = is5.read(b5)) != -1) {
//          buf5.write(b5, 0, len5);
//        }
//        //读图像
//        is5 = new ByteArrayInputStream(buf5.toByteArray());
//        bimg5 = ImageIO.read(is5);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//
//      if (bimg5 != null) {
//        mainPic5.drawImage(bimg5, 50, csimageHeight - 155, 100, 100, null);
//        mainPic5.dispose();
//      }
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
    return ResponseUtil.ok(data);
  }


    @GetMapping("helpPhoto")
    public Object helpPhoto(Integer orderId,Integer userId,Integer flag) {
        Map data = new HashMap();
        String photoUrl=hbPhotoHisService.load(userId,orderId);
        if(StringUtils.isNotEmpty(photoUrl)){
            data.put("imgUrl",photoUrl);
        }else{
        String codeUrl=this.generatorCodeUrl(orderId,userId,flag);
        //String codeUrl="http://10.248.63.150/images/code/1528189722585.jpg";
        HelpOrder order=helpOrderService.load(orderId);
        LitemallGoods goods=litemallGoodsService.findById(order.getGoodsId());
        if(StringUtils.isNotEmpty(goods.getGoodsBrief()))
        data.put("content",goods.getContent());
        String imgurl=goods.getPosterPicUrl();
        LitemallUser user = litemallUserService.findById(userId);
        String nickname=user.getNickname();
        String avatar=user.getAvatar();
        image = new BufferedImage(csimageWidth, csimageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.fillRect(0, 0, csimageWidth, csimageHeight);


        //***********************插入中间广告图
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
//      //***********************插入圆圈圈
//      Graphics mainPic5 = image.getGraphics();
//      BufferedImage bimg5 = null;
//      try {
//        URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
//        URLConnection con5 = url5.openConnection();
//        //不超时
//        con5.setConnectTimeout(0);
//
//        //不允许缓存
//        con5.setUseCaches(false);
//        con5.setDefaultUseCaches(false);
//        InputStream is5 = con5.getInputStream();
//
//        //先读入内存
//        ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
//        byte[] b5 = new byte[1024];
//        int len5;
//        while ((len5 = is5.read(b5)) != -1) {
//          buf5.write(b5, 0, len5);
//        }
//        //读图像
//        is5 = new ByteArrayInputStream(buf5.toByteArray());
//        bimg5 = ImageIO.read(is5);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//
//      if (bimg5 != null) {
//        mainPic5.drawImage(bimg5, 50, csimageHeight - 155, 100, 100, null);
//        mainPic5.dispose();
//      }
        Graphics2D tip4 = image.createGraphics();
        Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 22);
        tip4.setColor(Color.black);
        tip4.setFont(tipFont4);
        tip4.drawString("我是", 135, csimageHeight - 95);

        Graphics2D tip6 = image.createGraphics();
        Font tipFont6 = new Font("苹方 常规", Font.PLAIN, 22);
        tip6.setColor(Color.red);
        tip6.setFont(tipFont6);
        tip6.drawString(nickname, 178, csimageHeight - 95);


        Graphics2D tip5 = image.createGraphics();
        Font tipFont5 = new Font("苹方 常规", Font.PLAIN, 22);
        tip5.setColor(Color.black);
        tip5.setFont(tipFont5);
        tip5.drawString("朋友帮我砍个价吧", 135, csimageHeight - 65);

        if (StringUtils.isNotEmpty(codeUrl)) {
            Graphics mainPic6 = image.getGraphics();
            BufferedImage bimg6 = null;
            try {
                URL url6 = new URL(codeUrl);
                URLConnection con6 = url6.openConnection();
                //不超时
                con6.setConnectTimeout(0);

                //不允许缓存
                con6.setUseCaches(false);
                con6.setDefaultUseCaches(false);
                InputStream is6 = con6.getInputStream();

                //先读入内存
                ByteArrayOutputStream buf6 = new ByteArrayOutputStream(8192);
                byte[] b6 = new byte[1024];
                int len6;
                while ((len6 = is6.read(b6)) != -1) {
                    buf6.write(b6, 0, len6);
                }
                //读图像
                is6 = new ByteArrayInputStream(buf6.toByteArray());
                bimg6 = ImageIO.read(is6);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bimg6 != null) {
                mainPic6.drawImage(bimg6, 565, helpimageHeight - 200, 150, 150, null);
                mainPic6.dispose();
            }
        }


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
        HbPhotoHis his=new HbPhotoHis();
        his.setUserId(userId);
        his.setImgUrl(serverurl + dataPath);
        his.setOrderId(orderId);
        his.setType(2);//海报类型1抽奖单0拼团单2助力单
        hbPhotoHisService.create(his);
        }
        return ResponseUtil.ok(data);
    }

    public static void main(String[] args) {
        GraphicsControllor cg = new GraphicsControllor();
        try {
            //cg.helpPhoto(6,5589);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object graphicsGeneration2(String date, String nickname, String content, String imgurl,String author,String photoUrl,String codeUrl) {
        Map data=new HashMap();
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);


        //***********************插入中间广告图
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
            is2=new ByteArrayInputStream(buf2.toByteArray());
            bimg = javax.imageio.ImageIO.read(is2);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg!=null){
            mainPic.drawImage(bimg, 0, 0, imageWidth, 1066, null);
            mainPic.dispose();
        }

        //***********************插入二维码图片
        Graphics mainPic1 = image.getGraphics();
        BufferedImage bimg1 = null;
        try {
            URL url=null;
            if(codeUrl==null){
                url = new URL(codeUrl);
            }else{
                url = new URL(codeUrl);
            }

            URLConnection con = url.openConnection();
            //不超时
            con.setConnectTimeout(0);

            //不允许缓存
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            InputStream is = con.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                buf.write(b, 0, len);
            }
            //读图像
            is=new ByteArrayInputStream(buf.toByteArray());
            bimg1 = ImageIO.read(is);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg1!=null){
            mainPic1.drawImage(bimg1, 1050, 2100, 300, 300, null);
            mainPic1.dispose();
        }

        //***********************插入用户头像
        LitemallUser user=null;
        if(user==null){
            Graphics mainPic3 = image.getGraphics();
            BufferedImage bimg3 = null;
            try {
                URL url3 = new URL(photoUrl);
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
                is3=new ByteArrayInputStream(buf3.toByteArray());
                bimg3 = ImageIO.read(is3);
            } catch (Exception e) {e.printStackTrace();}

            if(bimg3!=null){
                mainPic3.drawImage(bimg3, 115, 2165, 160, 160, null);
                mainPic3.dispose();
            }
        }
        //***********************插入圆圈圈
        Graphics mainPic5 = image.getGraphics();
        BufferedImage bimg5 = null;
        try {
            URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
            URLConnection con5 = url5.openConnection();
            //不超时
            con5.setConnectTimeout(0);

            //不允许缓存
            con5.setUseCaches(false);
            con5.setDefaultUseCaches(false);
            InputStream is5 = con5.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
            byte[] b5 = new byte[1024];
            int len5;
            while ((len5 = is5.read(b5)) != -1) {
                buf5.write(b5, 0, len5);
            }
            //读图像
            is5=new ByteArrayInputStream(buf5.toByteArray());
            bimg5 = ImageIO.read(is5);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg5!=null){
            mainPic5.drawImage(bimg5, 100, 2150, 200, 200, null);
            mainPic5.dispose();
        }
        //***********************设置下面的提示框

        Graphics2D tip = image.createGraphics();
        //设置区域颜色
        tip.setColor(new Color(247,247,247));
        tip.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.gray);
        //设置字体
        Font tipFont = new Font("苹方 常规", Font.PLAIN, 54);
        tip.setColor(Color.gray);
        tip.setFont(tipFont);
        //***********************设置下面的按钮块
        //***********************插入波塞冬logo
        Graphics mainPic4 = image.getGraphics();
        BufferedImage bimg4 = null;
        try {
            URL url4 = new URL("https://sunlands.ministudy.com/images/bosaidong.png");
            URLConnection con4 = url4.openConnection();
            //不超时
            con4.setConnectTimeout(0);

            //不允许缓存
            con4.setUseCaches(false);
            con4.setDefaultUseCaches(false);
            InputStream is4 = con4.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf4 = new ByteArrayOutputStream(8192);
            byte[] b4 = new byte[1024];
            int len4;
            while ((len4 = is4.read(b4)) != -1) {
                buf4.write(b4, 0, len4);
            }
            //读图像
            is4=new ByteArrayInputStream(buf4.toByteArray());
            bimg4 = ImageIO.read(is4);
        } catch (Exception e) {e.printStackTrace();}

        if(bimg4!=null){
            mainPic4.drawImage(bimg4, 450, 1200, 572, 596, null);
            mainPic4.dispose();
        }

        //***********************设置下面的提示框

        /*Graphics2D tip2 = image.createGraphics();
        //设置区域颜色
        tip2.setColor(new Color(240,248,255));
        //填充区域并确定区域大小位置
        //tip2.fillRect(0, 1000, imageWidth, 1000);
        //设置字体颜色，先设置颜色，再填充内容
        tip2.setColor(Color.gray);
        //设置字体
        Font tipFont2 = new Font("苹方 常规", Font.PLAIN, 50);
        tip2.setFont(tipFont2);
        tip2.drawString("EVAN", 580, 2100);*/

        Graphics2D tip3 = image.createGraphics();
        //设置区域颜色
        tip3.setColor(new Color(247,247,247));
        //填充区域并确定区域大小位置
        //tip3.fillRect(0, btn2_2_top, imageWidth, H_tip);
        //设置字体颜色，先设置颜色，再填充内容
        tip3.setColor(new Color(40,40,40));
        //设置字体
        Font tipFont3 = new Font("苹方 常规", Font.PLAIN, 48);
        tip3.setFont(tipFont3);
        //tip3.drawString("我是", 300, 2230);
        tip3.drawString("一本书，一视界", 300, 2310);
        tip3.drawString(date, 605, 1140);
        System.out.println(author.length());
        int authorLength=author.length();
        int authorWidth = tip3.getFontMetrics().stringWidth(author);
        if (authorLength==1){
            tip3.drawString(author, authorWidth / 2+700, 1896);
        }else if (authorLength==2)
            tip3.drawString(author, authorWidth / 2+650, 1886);
        else if (authorLength==3)
            tip3.drawString(author, authorWidth / 2+600, 1886);
        else if (authorLength==4)
            tip3.drawString(author, authorWidth / 2+550, 1886);
        else if (authorLength==5)
            tip3.drawString(author, authorWidth / 2+500, 1886);
        else if (authorLength==6)
            tip3.drawString(author, authorWidth / 2+450, 1886);
        else if (authorLength==7)
            tip3.drawString(author, authorWidth / 2+400, 1886);
        else if (authorLength==8)
            tip3.drawString(author, authorWidth / 2+350, 1886);
        else if (authorLength==9)
            tip3.drawString(author, authorWidth / 2+300, 1886);
        else if (authorLength==10)
            tip3.drawString(author, authorWidth / 2+250, 1886);
        else if (authorLength==11)
            tip3.drawString(author, authorWidth / 2+200, 1886);
        else if (authorLength==12)
            tip3.drawString(author, authorWidth / 2+150, 1886);
        else if (authorLength==13)
            tip3.drawString(author, authorWidth / 2+100, 1886);
        else if (authorLength==14)
            tip3.drawString(author, authorWidth / 2+50, 1886);
        else if (authorLength==15)
            tip3.drawString(author, authorWidth / 2, 1886);
        else if (authorLength==16)
            tip3.drawString(author, authorWidth / 2-50, 1886);
        else if (authorLength==17)
            tip3.drawString(author, authorWidth / 2-100, 1886);
        else if (authorLength==18)
            tip3.drawString(author, authorWidth / 2-150, 1886);
        else if (authorLength==19)
            tip3.drawString(author, authorWidth / 2-200, 1886);
        else if (authorLength==20)
            tip3.drawString(author, authorWidth / 2-250, 1886);
        else
            tip3.drawString(author, authorWidth / 2-200, 1886);
        //tip3.drawLine(10,2200,1490,2200);

        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(new Color(40,40,40));
        String str=content;

        List getStrList=getStrList(str,22);
        System.out.println(getStrList);
        logger.info(getStrList);
        if(getStrList.size()>0){
            if(getStrList.size()>1){
                for (int i = 0; i <getStrList.size() ; i++) {
                    tip.drawString(getStrList.get(i).toString(), 155, 1410+i*100);
                }
            }else{
                int strWidth = tip.getFontMetrics().stringWidth(str);
                int length=str.length();
                if(length==10)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+180, imageWidth);
                else if(length==11)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+130, imageWidth);
                else if(length==12)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+80, imageWidth);
                else if(length==13)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+30, imageWidth);
                else if(length==14)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-30, imageWidth);
                else if(length==15)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-80, imageWidth);
                else if(length==16)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-130, imageWidth);
                else if(length==17)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-180, imageWidth);
                else if(length==18)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-230, imageWidth);
                else if(length==19)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-280, imageWidth);
                else if(length==20)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-330, imageWidth);
                else if(length==21)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-380, imageWidth);
                else if(length==22)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-430, imageWidth);
                else
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-480, imageWidth);

            }
        }
        Graphics2D tip4 = image.createGraphics();
        //设置区域颜色
        //tip4.setColor(new Color(247,247,247));
        //tip4.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        //tip4.setColor(new Color(51,129,246));
        //设置字体
        Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 48);
        tip4.setColor(new Color(51,129,246));
        tip4.setFont(tipFont4);
        tip4.drawString(nickname, 400, 2230);
        String temp = "images" + File.separator + "temp" + File.separator;
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        // 文件路径
        String filePath = "/data/".concat(temp);

        File dest = new File(filePath, newFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        createImage(filePath+newFileName);
        System.out.println(filePath+newFileName);
        // 将反斜杠转换为正斜杠
        String dataPath = temp.replaceAll("\\\\", "/") + newFileName;
        data.put("imgUrl",serverurl+dataPath);
        data.put("desk_url",filePath+newFileName);
        return ResponseUtil.ok(data);
    }


    @GetMapping("helpPhoto1")
    public Object helpPhoto1(Integer orderId,Integer userId,String path) {
       String codeUrl="http://10.248.63.150/images/code/1528189722585.jpg"; //this.generatorCodeUrl(path,orderId,userId);
       HelpOrder order=helpOrderService.load(orderId);
       LitemallGoods goods=litemallGoodsService.findById(order.getGoodsId());
       LitemallUser user=litemallUserService.findById(userId);
       String name=goods.getName();
       String nickname=user.getNickname();
       String memo=goods.getMemo();
       String imgurl=goods.getPrimaryPicUrl();
        Map data = new HashMap();
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);


        //***********************插入中间广告图
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            URL url2 = new URL("http://10.248.63.150/images/upload/haibaobg.png");
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
            mainPic.drawImage(bimg, 0, 0, imageWidth, 1066, null);
            mainPic.dispose();
        }

        //***********************插入二维码图片
        Graphics mainPic1 = image.getGraphics();
        BufferedImage bimg1 = null;
        try {
            URL url = null;
            if (StringUtils.isEmpty(codeUrl)) {
                url = new URL(codeurl);
            } else {
                url = new URL(codeUrl);
            }

            URLConnection con = url.openConnection();
            //不超时
            con.setConnectTimeout(0);

            //不允许缓存
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            InputStream is = con.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                buf.write(b, 0, len);
            }
            //读图像
            is = new ByteArrayInputStream(buf.toByteArray());
            bimg1 = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bimg1 != null) {
            mainPic1.drawImage(bimg1, 1050, 2100, 300, 300, null);
            mainPic1.dispose();
        }

        //***********************插入用户头像
        if (userId != null) {
            if (StringUtils.isNotEmpty(user.getAvatar())) {
                Graphics mainPic3 = image.getGraphics();
                BufferedImage bimg3 = null;
                try {
                    URL url3 = new URL(user.getAvatar());
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
                    mainPic3.drawImage(bimg3, 115, 2165, 160, 160, null);
                    mainPic3.dispose();
                }
            }
        }
        //***********************插入圆圈圈
        Graphics mainPic5 = image.getGraphics();
        BufferedImage bimg5 = null;
        try {
            URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
            URLConnection con5 = url5.openConnection();
            //不超时
            con5.setConnectTimeout(0);

            //不允许缓存
            con5.setUseCaches(false);
            con5.setDefaultUseCaches(false);
            InputStream is5 = con5.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
            byte[] b5 = new byte[1024];
            int len5;
            while ((len5 = is5.read(b5)) != -1) {
                buf5.write(b5, 0, len5);
            }
            //读图像
            is5 = new ByteArrayInputStream(buf5.toByteArray());
            bimg5 = ImageIO.read(is5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bimg5 != null) {
            mainPic5.drawImage(bimg5, 100, 2150, 200, 200, null);
            mainPic5.dispose();
        }
        //***********************设置下面的提示框

        Graphics2D tip = image.createGraphics();
        //设置区域颜色
        tip.setColor(new Color(247, 247, 247));
        tip.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.gray);
        //设置字体
        Font tipFont = new Font("苹方 常规", Font.PLAIN, 54);
        tip.setColor(Color.gray);
        tip.setFont(tipFont);
        //***********************设置下面的按钮块
        //***********************插入波塞冬logo
        Graphics mainPic4 = image.getGraphics();
        BufferedImage bimg4 = null;
        try {
            URL url4 = new URL("https://sunlands.ministudy.com/images/bosaidong.png");
            URLConnection con4 = url4.openConnection();
            //不超时
            con4.setConnectTimeout(0);

            //不允许缓存
            con4.setUseCaches(false);
            con4.setDefaultUseCaches(false);
            InputStream is4 = con4.getInputStream();

            //先读入内存
            ByteArrayOutputStream buf4 = new ByteArrayOutputStream(8192);
            byte[] b4 = new byte[1024];
            int len4;
            while ((len4 = is4.read(b4)) != -1) {
                buf4.write(b4, 0, len4);
            }
            //读图像
            is4 = new ByteArrayInputStream(buf4.toByteArray());
            bimg4 = ImageIO.read(is4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bimg4 != null) {
            mainPic4.drawImage(bimg4, 450, 1200, 572, 596, null);
            mainPic4.dispose();
        }

        //***********************设置下面的提示框

        /*Graphics2D tip2 = image.createGraphics();
        //设置区域颜色
        tip2.setColor(new Color(240,248,255));
        //填充区域并确定区域大小位置
        //tip2.fillRect(0, 1000, imageWidth, 1000);
        //设置字体颜色，先设置颜色，再填充内容
        tip2.setColor(Color.gray);
        //设置字体
        Font tipFont2 = new Font("苹方 常规", Font.PLAIN, 50);
        tip2.setFont(tipFont2);
        tip2.drawString("EVAN", 580, 2100);*/

        Graphics2D tip3 = image.createGraphics();
        //设置区域颜色
        tip3.setColor(new Color(247, 247, 247));
        //填充区域并确定区域大小位置
        //tip3.fillRect(0, btn2_2_top, imageWidth, H_tip);
        //设置字体颜色，先设置颜色，再填充内容
        tip3.setColor(new Color(40, 40, 40));
        //设置字体
        Font tipFont3 = new Font("苹方 常规", Font.PLAIN, 48);
        tip3.setFont(tipFont3);
        //tip3.drawString("", 300, 2230);
        tip3.drawString("一本书，一视界", 300, 2310);
        //tip3.drawString(date, 605, 1140);
        System.out.println(memo.length());
        int authorLength = memo.length();
        int authorWidth = tip3.getFontMetrics().stringWidth(memo);
        if (authorLength == 1) {
            tip3.drawString(memo, authorWidth / 2 + 700, 1896);
        } else if (authorLength == 2)
            tip3.drawString(memo, authorWidth / 2 + 650, 1886);
        else if (authorLength == 3)
            tip3.drawString(memo, authorWidth / 2 + 600, 1886);
        else if (authorLength == 4)
            tip3.drawString(memo, authorWidth / 2 + 550, 1886);
        else if (authorLength == 5)
            tip3.drawString(memo, authorWidth / 2 + 500, 1886);
        else if (authorLength == 6)
            tip3.drawString(memo, authorWidth / 2 + 450, 1886);
        else if (authorLength == 7)
            tip3.drawString(memo, authorWidth / 2 + 400, 1886);
        else if (authorLength == 8)
            tip3.drawString(memo, authorWidth / 2 + 350, 1886);
        else if (authorLength == 9)
            tip3.drawString(memo, authorWidth / 2 + 300, 1886);
        else if (authorLength == 10)
            tip3.drawString(memo, authorWidth / 2 + 250, 1886);
        else if (authorLength == 11)
            tip3.drawString(memo, authorWidth / 2 + 200, 1886);
        else if (authorLength == 12)
            tip3.drawString(memo, authorWidth / 2 + 150, 1886);
        else if (authorLength == 13)
            tip3.drawString(memo, authorWidth / 2 + 100, 1886);
        else if (authorLength == 14)
            tip3.drawString(memo, authorWidth / 2 + 50, 1886);
        else if (authorLength == 15)
            tip3.drawString(memo, authorWidth / 2, 1886);
        else if (authorLength == 16)
            tip3.drawString(memo, authorWidth / 2 - 50, 1886);
        else if (authorLength == 17)
            tip3.drawString(memo, authorWidth / 2 - 100, 1886);
        else if (authorLength == 18)
            tip3.drawString(memo, authorWidth / 2 - 150, 1886);
        else if (authorLength == 19)
            tip3.drawString(memo, authorWidth / 2 - 200, 1886);
        else if (authorLength == 20)
            tip3.drawString(memo, authorWidth / 2 - 250, 1886);
        else
            tip3.drawString(memo, authorWidth / 2 - 200, 1886);
        //tip3.drawLine(10,2200,1490,2200);

        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(new Color(40, 40, 40));
        String str = name;

        List getStrList = getStrList(str, 22);
        System.out.println(getStrList);
        logger.info(getStrList);
        if (getStrList.size() > 0) {
            if (getStrList.size() > 1) {
                for (int i = 0; i < getStrList.size(); i++) {
                    tip.drawString(getStrList.get(i).toString(), 155, 1410 + i * 100);
                }
            } else {
                int strWidth = tip.getFontMetrics().stringWidth(str);
                int length = str.length();
                if (length == 10)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 + 180, imageWidth);
                else if (length == 11)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 + 130, imageWidth);
                else if (length == 12)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 + 80, imageWidth);
                else if (length == 13)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 + 30, imageWidth);
                else if (length == 14)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 30, imageWidth);
                else if (length == 15)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 80, imageWidth);
                else if (length == 16)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 130, imageWidth);
                else if (length == 17)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 180, imageWidth);
                else if (length == 18)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 230, imageWidth);
                else if (length == 19)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 280, imageWidth);
                else if (length == 20)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 330, imageWidth);
                else if (length == 21)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 380, imageWidth);
                else if (length == 22)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 430, imageWidth);
                else
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2 - 480, imageWidth);

            }
        }
        Graphics2D tip4 = image.createGraphics();
        //设置区域颜色
        //tip4.setColor(new Color(247,247,247));
        //tip4.fillRect(0, 1000, imageWidth, 1000);
        //填充区域并确定区域大小位置
        //tip.fillRect(0, 553, imageWidth, 50);
        //设置字体颜色，先设置颜色，再填充内容
        //tip4.setColor(new Color(51,129,246));
        //设置字体
        Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 48);
        tip4.setColor(new Color(51, 129, 246));
        tip4.setFont(tipFont4);
        tip4.drawString(nickname, 300, 2230);
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
        return ResponseUtil.ok(data);
    }

    //该方法已废弃
    @GetMapping("helpPhoto2")
    public Object helpPhoto2(Integer orderId,Integer userId) {

        Map data = new HashMap();

        HelpOrder order=helpOrderService.load(orderId);
        LitemallGoods goods=litemallGoodsService.findById(order.getGoodsId());
        LitemallUser user=litemallUserService.findById(userId);
        String codeUrl="http://10.248.63.150/images/code/1528189722585.jpg"; //this.generatorCodeUrl(path,orderId,userId);
        String name=goods.getName();
        String nickname=user.getNickname();
        String memo=goods.getMemo();
        String goodsUrl=goods.getPrimaryPicUrl();
        String avatar=user.getAvatar();
        image = new BufferedImage(csimageWidth, helpimageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.fillRect(0, 0, csimageWidth, helpimageHeight);


        //***********************插入海报背景图
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            URL url2 = new URL("http://10.248.63.150/images/upload/haibaobg.png");
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
            mainPic.drawImage(bimg, 0, 0, csimageWidth, helpimageHeight, null);
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
                mainPic3.drawImage(bimg3, 65, helpimageHeight - 120, 60, 60, null);
                mainPic3.dispose();
            }
        }
//      //***********************插入圆圈圈
      Graphics mainPic5 = image.getGraphics();
      BufferedImage bimg5 = null;
      try {
        URL url5 = new URL("https://sunlands.ministudy.com/images/toux.png");
        URLConnection con5 = url5.openConnection();
        //不超时
        con5.setConnectTimeout(0);

        //不允许缓存
        con5.setUseCaches(false);
        con5.setDefaultUseCaches(false);
        InputStream is5 = con5.getInputStream();

        //先读入内存
        ByteArrayOutputStream buf5 = new ByteArrayOutputStream(8192);
        byte[] b5 = new byte[1024];
        int len5;
        while ((len5 = is5.read(b5)) != -1) {
          buf5.write(b5, 0, len5);
        }
        //读图像
        is5 = new ByteArrayInputStream(buf5.toByteArray());
        bimg5 = ImageIO.read(is5);
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (bimg5 != null) {
        mainPic5.drawImage(bimg5, 47, csimageHeight - 122, 100, 100, null);
        mainPic5.dispose();
      }


        Graphics2D tip4 = image.createGraphics();
        Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 22);
        tip4.setColor(Color.red);
        tip4.setFont(tipFont4);
        tip4.drawString("我是"+nickname, 138, helpimageHeight - 95);

        Graphics2D tip5 = image.createGraphics();
        Font tipFont5 = new Font("苹方 常规", Font.PLAIN, 22);
        tip5.setColor(Color.black);
        tip5.setFont(tipFont5);
        tip5.drawString("朋友帮我砍个价吧", 138, helpimageHeight - 70);

        Graphics2D tip6 = image.createGraphics();
        Font tipFont6 = new Font("苹方 常规", Font.PLAIN, 22);
        tip6.setColor(Color.darkGray);
        tip6.setFont(tipFont6);
        int authorLength = memo.length();
        int authorWidth = tip6.getFontMetrics().stringWidth(memo);
        if (authorLength == 1) {
            tip6.drawString(memo, authorWidth / 2 + 700, helpimageHeight-30);
        } else if (authorLength == 2)
            tip6.drawString(memo, authorWidth / 2 + 650, helpimageHeight-300);
        else if (authorLength == 3)
            tip6.drawString(memo, authorWidth / 2 + 600, helpimageHeight-300);
        else if (authorLength == 4)
            tip6.drawString(memo, authorWidth / 2 + 550, helpimageHeight-300);
        else if (authorLength == 5)
            tip6.drawString(memo, authorWidth / 2 + 500, helpimageHeight-300);
        else if (authorLength == 6)
            tip6.drawString(memo, authorWidth / 2 + 450, helpimageHeight-300);
        else if (authorLength == 7)
            tip6.drawString(memo, authorWidth / 2 + 400, helpimageHeight-300);
        else if (authorLength == 8)
            tip6.drawString(memo, authorWidth / 2 + 350, helpimageHeight-300);
        else if (authorLength == 9)
            tip6.drawString(memo, authorWidth / 2 + 300, helpimageHeight-300);
        else if (authorLength == 10)
            tip6.drawString(memo, authorWidth / 2 + 250, helpimageHeight-300);
        else if (authorLength == 11)
            tip6.drawString(memo, 310, helpimageHeight-300);
        else if (authorLength == 12)
            tip6.drawString(memo, authorWidth / 2 + 150, helpimageHeight-300);
        else if (authorLength == 13)
            tip6.drawString(memo, authorWidth / 2 + 100, helpimageHeight-300);
        else if (authorLength == 14)
            tip6.drawString(memo, authorWidth / 2 + 50, helpimageHeight-300);
        else if (authorLength == 15)
            tip6.drawString(memo, authorWidth / 2, helpimageHeight-300);
        else if (authorLength == 16)
            tip6.drawString(memo, authorWidth / 2 - 50, helpimageHeight-300);
        else if (authorLength == 17)
            tip6.drawString(memo, authorWidth / 2 - 100, helpimageHeight-300);
        else if (authorLength == 18)
            tip6.drawString(memo, authorWidth / 2 - 150, helpimageHeight-300);
        else if (authorLength == 19)
            tip6.drawString(memo, authorWidth / 2 - 200, helpimageHeight-300);
        else if (authorLength == 20)
            tip6.drawString(memo, authorWidth / 2 - 250, helpimageHeight-300);
        else
            tip6.drawString(memo, authorWidth / 2, helpimageHeight-300);

        Graphics2D tip7 = image.createGraphics();
        Font tipFont7 = new Font("苹方 常规", Font.PLAIN, 28);
        tip7.setColor(Color.black);
        tip7.setFont(tipFont7);
        tip7.drawString(name, 110, helpimageHeight - 340);

        if (StringUtils.isNotEmpty(codeUrl)) {
            Graphics mainPic6 = image.getGraphics();
            BufferedImage bimg6 = null;
            try {
                URL url6 = new URL(codeUrl);
                URLConnection con6 = url6.openConnection();
                //不超时
                con6.setConnectTimeout(0);

                //不允许缓存
                con6.setUseCaches(false);
                con6.setDefaultUseCaches(false);
                InputStream is6 = con6.getInputStream();

                //先读入内存
                ByteArrayOutputStream buf6 = new ByteArrayOutputStream(8192);
                byte[] b6 = new byte[1024];
                int len6;
                while ((len6 = is6.read(b6)) != -1) {
                    buf6.write(b6, 0, len6);
                }
                //读图像
                is6 = new ByteArrayInputStream(buf6.toByteArray());
                bimg6 = ImageIO.read(is6);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bimg6 != null) {
                mainPic6.drawImage(bimg6, 565, helpimageHeight - 180, 150, 150, null);
                mainPic6.dispose();
            }
        }

        if (StringUtils.isNotEmpty(goodsUrl)) {
            Graphics mainPic7 = image.getGraphics();
            BufferedImage bimg7 = null;
            try {
                URL url7 = new URL(goodsUrl);
                URLConnection con7 = url7.openConnection();
                //不超时
                con7.setConnectTimeout(0);

                //不允许缓存
                con7.setUseCaches(false);
                con7.setDefaultUseCaches(false);
                InputStream is7 = con7.getInputStream();

                //先读入内存
                ByteArrayOutputStream buf7 = new ByteArrayOutputStream(8192);
                byte[] b7 = new byte[1024];
                int len7;
                while ((len7 = is7.read(b7)) != -1) {
                    buf7.write(b7, 0, len7);
                }
                //读图像
                is7 = new ByteArrayInputStream(buf7.toByteArray());
                bimg7 = ImageIO.read(is7);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bimg7 != null) {
                mainPic7.drawImage(bimg7, 185, 388, 370, 370, null);
                mainPic7.dispose();
            }
        }

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
        return ResponseUtil.ok(data);
    }

@GetMapping("generatorCodeUrl")
    private String generatorCodeUrl(Integer orderId,Integer userId,Integer flag) {
        String scene="";
        String datapath="";
        LitemallUser user=litemallUserService.findById(userId);
        if(user!=null&&StringUtils.isNotEmpty(user.getPid())){
            scene=userId+"#"+orderId+"#"+flag;
        }else{
            scene="#"+orderId+"#"+flag;
        }
        WxConfig config=wxConfigService.getToken();
        JSONObject object=new JSONObject();
        object.put("page","pages/aid/aid_share/main");
        object.put("scene",scene);
        object.put("width",430);//小程序二维码宽度
        String requestUrl=create_codeB_url.replace("ACCESS_TOKEN",config.getAccessToken());
        InputStream i=HttpClientUtil.doPostInstream(requestUrl,object);
        byte[] data = new byte[1024];
        int len = -1;
        FileOutputStream fileOutputStream = null;
        try {
            String temp = "images" + File.separator + "code" + File.separator;
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
            datapath = serverurl+temp.replaceAll("\\\\", "/") + newFileName;
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
        return datapath;
    }

/**
 * @Author leiqiang
 * @Description //TODO 积分商城拼团单海报生成
 * @Date   2018/8/13 10:33
 * @Param  [goodsId, number, productId, userId, flag, orderId, type]
 * @return java.lang.Object
 **/
    @GetMapping("jfdPhoto")
    public Object jfdPhoto(Integer goodsId,Integer number,Integer productId,Integer userId,Integer flag,Integer orderId,Integer type) {
        Map data = new HashMap();
        String photoUrl=hbPhotoHisService.load(userId,orderId);
        if(StringUtils.isNotEmpty(photoUrl)){
            data.put("imgUrl",photoUrl);
        }else {
            String codeUrl = this.jfdPhotoCodeUrl(userId, goodsId, flag, number, productId);
            //String codeUrl="http://10.248.63.150/images/code/1528189722585.jpg";
            LitemallGoods goods = litemallGoodsService.findById(goodsId);
            if (StringUtils.isNotEmpty(goods.getGoodsBrief()))
                data.put("content", goods.getGoodsBrief());
            String imgurl = goods.getPosterPicUrl();
            LitemallUser user = litemallUserService.findById(userId);
            String nickname = user.getNickname();
            String avatar = user.getAvatar();
            image = new BufferedImage(csimageWidth, csimageHeight, BufferedImage.TYPE_INT_RGB);
            //设置图片的背景色
            Graphics2D main = image.createGraphics();
            main.fillRect(0, 0, csimageWidth, csimageHeight);


            //***********************插入中间广告图
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
            Font tipFont4 = new Font("苹方 常规", Font.PLAIN, 22);
            tip4.setColor(Color.black);
            tip4.setFont(tipFont4);
            tip4.drawString("我是", 135, csimageHeight - 95);

            Graphics2D tip6 = image.createGraphics();
            Font tipFont6 = new Font("苹方 常规", Font.PLAIN, 22);
            tip6.setColor(Color.red);
            tip6.setFont(tipFont6);
            tip6.drawString(nickname, 178, csimageHeight - 95);


            Graphics2D tip5 = image.createGraphics();
            Font tipFont5 = new Font("苹方 常规", Font.PLAIN, 22);
            tip5.setColor(Color.black);
            tip5.setFont(tipFont5);
            tip5.drawString("朋友帮我砍个价吧", 135, csimageHeight - 65);

            if (StringUtils.isNotEmpty(codeUrl)) {
                Graphics mainPic6 = image.getGraphics();
                BufferedImage bimg6 = null;
                try {
                    URL url6 = new URL(codeUrl);
                    URLConnection con6 = url6.openConnection();
                    //不超时
                    con6.setConnectTimeout(0);

                    //不允许缓存
                    con6.setUseCaches(false);
                    con6.setDefaultUseCaches(false);
                    InputStream is6 = con6.getInputStream();

                    //先读入内存
                    ByteArrayOutputStream buf6 = new ByteArrayOutputStream(8192);
                    byte[] b6 = new byte[1024];
                    int len6;
                    while ((len6 = is6.read(b6)) != -1) {
                        buf6.write(b6, 0, len6);
                    }
                    //读图像
                    is6 = new ByteArrayInputStream(buf6.toByteArray());
                    bimg6 = ImageIO.read(is6);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (bimg6 != null) {
                    mainPic6.drawImage(bimg6, 565, helpimageHeight - 200, 150, 150, null);
                    mainPic6.dispose();
                }
            }


            String temp = "images" + File.separator + "code" + File.separator;
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
            HbPhotoHis his=new HbPhotoHis();
            his.setUserId(userId);
            his.setImgUrl(serverurl + dataPath);
            his.setOrderId(orderId);
            his.setType(type);//海报类型1抽奖单0拼团单2助力单
            hbPhotoHisService.create(his);
        }
        return ResponseUtil.ok(data);
    }

    private String jfdPhotoCodeUrl(Integer userId, Integer goodsId, Integer flag,Integer number, Integer productId) {
        String scene="";
        String datapath="";
        LitemallUser user=litemallUserService.findById(userId);
        if(user!=null&&StringUtils.isNotEmpty(user.getPid())){
            scene=userId+"#"+goodsId+"#"+number+"#"+productId+"#"+flag;
        }else{
            scene="#"+goodsId+"#"+number+"#"+productId+"#"+flag;
        }
        WxConfig config=wxConfigService.getToken();
        JSONObject object=new JSONObject();
        object.put("page","pages/GroupOrder/main");
        object.put("scene",scene);
        object.put("width",430);//小程序二维码宽度
        String requestUrl=create_codeB_url.replace("ACCESS_TOKEN",config.getAccessToken());
        InputStream i=HttpClientUtil.doPostInstream(requestUrl,object);
        byte[] data = new byte[1024];
        int len = -1;
        FileOutputStream fileOutputStream = null;
        try {
            String temp = "images" + File.separator + "code" + File.separator;
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
            datapath = serverurl+temp.replaceAll("\\\\", "/") + newFileName;
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
        return datapath;
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

}
