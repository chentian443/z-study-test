package org.springevent.test.resttemp;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class RestTemplateForEvent {
	
	public static String eventApiTest(){
		
		SimpleClientHttpRequestFactory s=new SimpleClientHttpRequestFactory();
		s.setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 80)));
		RestTemplate restTemplate = new RestTemplate(s);
        HttpHeaders headers = new HttpHeaders();//header参数
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "路边电线杆要断。");
        map.put("description", "下完雪，路边电线杆断裂，移动，联通还有供电所都来看过了，说不是他们负责，咱政府看看这电线杆属于谁的。");
        map.put("token", "d98e776d499d671bc85cf5bf5f991f8e8e8d830bf67cf1");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map,headers); //组装
        String httpUrl = "http://172.16.10.172:8000/predict";
        ResponseEntity<String> response = restTemplate.exchange(httpUrl,HttpMethod.POST,request,String.class);
        return response.getBody();
    }
	
	public static String eventApiTest2(){
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();//header参数
        //headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(null,headers); //组装
        String httpUrl = "http://172.16.10.172:8000/predict/1?sentence=123";
        ResponseEntity<String> response = restTemplate.exchange(httpUrl,HttpMethod.GET,request,String.class);
        return response.getBody();
    }
	
	public static void main(String[] args) {
		System.out.println(eventApiTest());
	}
}
