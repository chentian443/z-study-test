package org.springevent.test.resttemp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springevent.test.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTempService {

	@Autowired
	private RestTemplate restTemplate;
	
	public String getSessionJson(){
		
        HttpHeaders headers = new HttpHeaders();//header参数
        headers.setContentType(MediaType.APPLICATION_JSON);
 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "admin");
        map.put("password", "9e8c41c6eacad8c5e43c91a36bf56564");
 
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map,headers); //组装
        String httpUrl = "http://172.16.10.130:11180/website/face/v2/login";
        ResponseEntity<String> response = restTemplate.exchange(httpUrl,HttpMethod.POST,request,String.class);
        return response.getBody();
    }
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(MD5Util.MD5EncodeUtf8("P4jKn8qFUELlX4yC"));// 9E8C41C6EACAD8C5E43C91A36BF56564
	}

}
