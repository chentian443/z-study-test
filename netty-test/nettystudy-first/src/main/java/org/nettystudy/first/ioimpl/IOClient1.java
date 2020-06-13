package org.nettystudy.first.ioimpl;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class IOClient1 {

	
    public static void main(String[] args) {
		new Thread(() -> {
			Socket socket = null;
            try {
            	socket = new Socket("127.0.0.1", 8000);
                
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": IOClient1").getBytes());
                        Thread.sleep(8000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            } finally {
            	try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }).start();
		
    }
}
