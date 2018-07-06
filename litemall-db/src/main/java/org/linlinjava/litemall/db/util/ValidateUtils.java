package org.linlinjava.litemall.db.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
  * @author lanye
  * @Description 校验工具类
  * @Date 2018/6/4 9:53
  * @Param
  * @return
  **/
public class ValidateUtils{

    /**
      * @author lanye
      * @Description 校验正则，是否包含特殊字符，包含返回true
      * @Date 2018/6/4 9:55
      * @Param [str]
      * @return boolean
      **/
    public static boolean validateStr(String str) {
        String regEx = "[✔X_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static void main(String[] args) {
        System.out.println(validateStr("just a test"));
        validateStr("");
    }
}
