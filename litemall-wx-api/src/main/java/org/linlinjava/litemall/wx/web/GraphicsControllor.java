package org.linlinjava.litemall.wx.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.util.ResponseUtil;
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
    @Resource
    private LitemallUserService litemallUserService;
    private final Log logger = LogFactory.getLog(GraphicsControllor.class);
    private BufferedImage image;
    private BufferedImage image2;
    private int imageWidth = 1500;  //图片的宽度
    private int imageHeight =2500; //图片的高度
    @Value("${web.upload-path}")
    private String webUploadPath;
    @Value("${serverurl}")
    private String serverurl;
    @Value("${codeurl}")
    private String codeurl;

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
            if(codeUrl==null){
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
        tip3.drawString("我是", 300, 2230);
        tip3.drawString("我为萤火虫代言", 300, 2310);
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

    public static void main(String[] args) {
        GraphicsControllor cg = new GraphicsControllor();
        try {
            cg.graphicsGeneration("2018-06-06", "Evan", "自信和自尊心越低他们啊啊啊阿士大夫撒旦六块腹肌洒落的咖啡机", "https://sunlands.ministudy.com/images/upload/1528265355187.jpg","居里夫人",16,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
