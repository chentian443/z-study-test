package org.nettystudy.first.ioimpl;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class IOClient2 {
	public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": IOClient2").getBytes());
                        Thread.sleep(15000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
