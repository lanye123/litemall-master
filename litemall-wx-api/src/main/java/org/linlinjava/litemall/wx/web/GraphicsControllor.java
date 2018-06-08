package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object graphicsGeneration(String date, String nickname, String content, String imgurl,String author) {
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
            URL url = new URL(codeurl);
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
        Font tipFont = new Font("微软雅黑", Font.PLAIN, 50);
        tip.setColor(Color.gray);
        tip.setFont(tipFont);
        tip.drawString(date, 600, 1250);
        System.out.println(author.length());
        int authorLength=author.length();
        int authorWidth = tip.getFontMetrics().stringWidth(author);
        if (authorLength==1){
            tip.drawString(author, authorWidth / 2+650, 1900);
        }else if (authorLength==2)
            tip.drawString(author, authorWidth / 2+650, 1900);
        else if (authorLength==3)
           tip.drawString(author, authorWidth / 2+600, 1900);
        else if (authorLength==4)
            tip.drawString(author, authorWidth / 2+550, 1900);
        else if (authorLength==5)
            tip.drawString(author, authorWidth / 2+500, 1900);
        else if (authorLength==6)
            tip.drawString(author, authorWidth / 2+450, 1900);
        else if (authorLength==7)
            tip.drawString(author, authorWidth / 2+400, 1900);
        else if (authorLength==8)
            tip.drawString(author, authorWidth / 2+350, 1900);
        else if (authorLength==9)
            tip.drawString(author, authorWidth / 2+300, 1900);
        else if (authorLength==10)
            tip.drawString(author, authorWidth / 2+250, 1900);
        else if (authorLength==11)
            tip.drawString(author, authorWidth / 2+200, 1900);
        else if (authorLength==12)
            tip.drawString(author, authorWidth / 2+150, 1900);
        else if (authorLength==13)
            tip.drawString(author, authorWidth / 2+100, 1900);
        else if (authorLength==14)
            tip.drawString(author, authorWidth / 2+50, 1900);
        else if (authorLength==15)
            tip.drawString(author, authorWidth / 2, 1900);
        else if (authorLength==16)
            tip.drawString(author, authorWidth / 2-50, 1900);
        else if (authorLength==17)
            tip.drawString(author, authorWidth / 2-100, 1900);
        else if (authorLength==18)
            tip.drawString(author, authorWidth / 2-150, 1900);
        else if (authorLength==19)
            tip.drawString(author, authorWidth / 2-200, 1900);
        else if (authorLength==20)
            tip.drawString(author, authorWidth / 2-250, 1900);
        else
        tip.drawString(author, authorWidth / 2-200, 1900);
        //***********************设置下面的按钮块

        //***********************设置下面的提示框

        /*Graphics2D tip2 = image.createGraphics();
        //设置区域颜色
        tip2.setColor(new Color(240,248,255));
        //填充区域并确定区域大小位置
        //tip2.fillRect(0, 1000, imageWidth, 1000);
        //设置字体颜色，先设置颜色，再填充内容
        tip2.setColor(Color.gray);
        //设置字体
        Font tipFont2 = new Font("微软雅黑", Font.PLAIN, 50);
        tip2.setFont(tipFont2);
        tip2.drawString("EVAN", 580, 2100);*/

        Graphics2D tip3 = image.createGraphics();
        //设置区域颜色
        tip3.setColor(new Color(247,247,247));
        //填充区域并确定区域大小位置
        //tip3.fillRect(0, btn2_2_top, imageWidth, H_tip);
        //设置字体颜色，先设置颜色，再填充内容
        tip3.setColor(Color.gray);
        //设置字体
        Font tipFont3 = new Font("微软雅黑", Font.PLAIN, 50);
        tip3.setFont(tipFont3);
        tip3.drawString("我是"+nickname, 200, 2200);
        tip3.drawString("我为萤火虫代言", 200, 2300);
        //tip3.drawLine(10,2200,1490,2200);

        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.gray);
        String str=content;

        List getStrList=getStrList(str,24);
        System.out.println(getStrList);
        logger.info(getStrList);
        if(getStrList.size()>0){
            if(getStrList.size()>1){
                for (int i = 0; i <getStrList.size() ; i++) {
                    tip.drawString(getStrList.get(i).toString(), 150, 1500+i*80);
                }
            }else{
                int strWidth = tip.getFontMetrics().stringWidth(str);
                int length=str.length();
                if(length==10)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+250, imageWidth);
                else if(length==11)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+200, imageWidth);
                else if(length==12)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+150, imageWidth);
                else if(length==13)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+100, imageWidth);
                else if(length==14)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+100, imageWidth);
                else if(length==15)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2+30, imageWidth);
                else if(length==16)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-30, imageWidth);
                else if(length==17)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-60, imageWidth);
                else if(length==18)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-150, imageWidth);
                else if(length==19)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-150, imageWidth);
                else if(length==20)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-210, imageWidth);
                else if(length==21)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-300, imageWidth);
                else if(length==22)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-360, imageWidth);
                else if(length==23)
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-410, imageWidth);
                else
                    tip.drawString(getStrList.get(0).toString(), strWidth / 2-460, imageWidth);

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
            cg.graphicsGeneration("2018-06-06", "EVAN", "自信和自尊心越低他们啊啊啊", "https://sunlands.ministudy.com/images/upload/1528265355187.jpg","王打累是啊啊啊啊啊啊啊阿斯顿发士大夫");
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
