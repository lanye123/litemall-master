package org.linlinjava.litemall.db;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
class GetPostTest {
    public static String sendGet(String url, String param) {
        String result = "";
        String urlName = url + "?" + param;
        try {
            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                System.out.println(s + "-->" + map.get(s));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url,String param){
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            //post设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while((line = in.readLine()) != null){
                result +="\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPostJxf(String url,JSONObject param){
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection  conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
            conn.setRequestProperty("Referer","http://xj-sb-asia-jxf.prdasbbwla1.com/zh-cn/sports");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Host","xj-sb-asia-jxf.prdasbbwla1.com");
            conn.setRequestProperty("Origin","http://xj-sb-asia-jxf.prdasbbwla1.com");
            //post设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.connect();

            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(conn.getOutputStream());
            String parm = "storeId=" + URLEncoder.encode("32", "utf-8"); // URLEncoder.encode()方法 为字符串进行编码

            // 将参数输出到连接
            dataout.writeBytes(parm);

            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)

            System.out.println(conn.getResponseCode());

            // 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            @SuppressWarnings("unused")
            String line = null;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据

            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
                sb.append(bf.readLine());
            }
            bf.close(); // 重要且易忽略步骤 (关闭流,切记!)
            conn.disconnect(); // 销毁连接
            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        JSONObject object=new JSONObject();
        JSONArray sid=new JSONArray();
        JSONArray dc=null;
        object.put("ifl",true);
        object.put("ipf",false);
        object.put("iip",false);
        object.put("pn",0);
        object.put("tz",480);
        object.put("pt",2);
        object.put("pid",0);
        sid.add(1);
        object.put("sid",sid);
        object.put("ubt","am");
        object.put("dc",dc);
        object.put("dv",-1);
        object.put("ot",0);
//        String sendRecvGet =GetPostTest.sendGet("http://localhost:8989/testServletEncoding/encodingTest","param=xxxxx嘻嘻嘻");
//        System.out.println(sendRecvGet);
        String sendRecvPost =GetPostTest.sendPostJxf("http://xj-sb-asia-jxf.prdasbbwla1.com/zh-cn/serv/getodds",object);
        System.out.println(sendRecvPost);
    }
}

