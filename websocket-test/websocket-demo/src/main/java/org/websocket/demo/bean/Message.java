package org.websocket.demo.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangyibo on 16/12/29.
 * 浏览器向服务器发送的消息使用此类接受
 */
@Getter
@Setter
public class Message implements Serializable{
    private String name;
}