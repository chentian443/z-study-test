package org.springevent.test.call;

import java.util.concurrent.Callable;

public class CallableTest {
    public static void main(String[] args) {
        // 并不会开启多线程
        String res = execute(()->{
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(5000);
            String str = "abc";
            return str;
        });
        System.out.println(Thread.currentThread().getName());
        System.out.println(res);
    }

    private static String execute(Callable<String> callable){
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "moren";
    }
}
