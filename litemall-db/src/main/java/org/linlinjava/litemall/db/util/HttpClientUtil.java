package org.linlinjava.litemall.db.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class HttpClientUtil {
    public static JSONObject doGet(String url){
        JSONObject response = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse res = httpclient.execute(request);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.fromObject(result);
            }
            httpclient.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static JSONObject doPostISO2UTF(String url,JSONObject param){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            if(param!=null){
                StringEntity s = new StringEntity(param.toString());
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");//发送json数据需要设置contentType
                post.setEntity(s);
            }
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：

                result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                response = JSONObject.fromObject(result);
            }
            httpclient.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static JSONObject doPostISO2UTF(String url){
        return doPostISO2UTF(url,null);
    }

    public static JSONObject doPost(String url,JSONObject param){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            System.out.println(param.toString());

            StringEntity s = new StringEntity(param.toString(),"UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                InputStream is=res.getEntity().getContent();
                response = JSONObject.fromObject(result);
            }
            httpclient.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static InputStream doPostInstream(String url,JSONObject param){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        InputStream is=null;
        JSONObject response = null;
        try {
            System.out.println(param.toString());

            StringEntity s = new StringEntity(param.toString(),"UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                is=res.getEntity().getContent();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return is;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }
}
