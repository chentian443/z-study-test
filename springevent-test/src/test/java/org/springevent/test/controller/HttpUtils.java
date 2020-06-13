package org.springevent.test.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

public class HttpUtils {
	
    public static String getHttpInterface(String path) {
        BufferedReader in = null;
        StringBuffer result = null;
        try {
            URL url = new URL(path);
            // 打开和url之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestMethod("GET");
            connection.connect();

            result = new StringBuffer();
            // 读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static String getResource(String targetURL, String method) {
        String result = "";
        try {
            URL restServiceURL = new URL(escape(targetURL));
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL
                    .openConnection();
            httpConnection.setDoOutput(true);// 允许输出
            httpConnection.setDoInput(true);// 允许输入
            httpConnection.setUseCaches(false);// 不用缓存
            httpConnection.setRequestMethod(method);
            httpConnection.setRequestProperty("Charset", "UTF-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException(
                        "HTTP GET Request Failed with Error code : "
                                + httpConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream())));
            String output;

            while ((output = responseBuffer.readLine()) != null) {
                result += output;
            }
            httpConnection.disconnect();
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String addResource(String urlPath,String method, JSONObject obj) {
        String result = "";
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod(method);
            httpConnection.setRequestProperty("Connection", "Keep-Alive");
            httpConnection.setRequestProperty("Charset", "UTF-8");
            byte[] data = (obj.toString()).getBytes();
            httpConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpConnection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            // 开始连接请求
            httpConnection.connect();
            OutputStream out = httpConnection.getOutputStream();
            // 写入请求的字符串
            out.write((obj.toString()).getBytes());
            out.flush();
            out.close();

            // 请求返回的状态
            if (httpConnection.getResponseCode() == 200) {
                InputStream in = httpConnection.getInputStream();
                try {
                    byte[] data1 = new byte[in.available()];
                    in.read(data1);
                    result += new String(data1);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                throw new RuntimeException(
                        "HTTP GET Request Failed with Error code : "
                                + httpConnection.getResponseCode());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    
    public static String escape(String str){
        return str.replace("{", "%7b").replace("}", "%7d").replace("\"", "%22");
    }

    public static void main(String[] args) {
    	String httpUrl = "http://172.16.10.172:8080/predict";
		JSONObject paramJson=new JSONObject();
		paramJson.element("title", "路边电线杆要断。");
		paramJson.element("description", "下完雪，路边电线杆断裂，移动，联通还有供电所都来看过了，说不是他们负责，咱政府看看这电线杆属于谁的。");
		paramJson.element("token", "d98e776d499d671bc85cf5bf5f991f8e8e8d830bf67cf1");
		
		String res=HttpUtils.addResource(httpUrl, "POST", paramJson);
		JSONObject jsonO=JSONObject.fromObject(res);
		System.out.println(jsonO);
    }
}