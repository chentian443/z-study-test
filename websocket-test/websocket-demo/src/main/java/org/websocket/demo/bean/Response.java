package org.websocket.demo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangyibo on 16/12/29.
 * 服务器向浏览器发送的此类消息。
 */
@Getter
@Setter
public class Response {
	private String responseMessage;
	
    public Response(String responseMessage){
        this.responseMessage = responseMessage;
    }
    
}