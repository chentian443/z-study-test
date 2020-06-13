package org.nettystudy.first.ioimpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
	static volatile int n = 0;
	
    public static void main(String[] args) throws Exception {
    	
        ServerSocket serverSocket = new ServerSocket(8000);
        // (1) 接收新连接线程
        new Thread(() -> {
        	System.out.println("start a new thread");
        	
        	while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();// 没有消费到连接，该方法会一直阻塞

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // (3) 按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(Thread.currentThread().getName() 
                                		+ ":" + new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }, "mythread-" + n).start();
                    
                    n ++;
                    System.out.println("a loop is over:" + n);
                } catch (IOException e) {
                } finally {
                	try {
						serverSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }

            }
        }).start();
    }
}
