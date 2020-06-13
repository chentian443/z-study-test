package org.nettystudy.first.thread;

public class ThreadTest {

    private int a = 0;

    private void test(){

        for (int i=0; i<10000; i++){
            new Thread(()->{
                a ++;
                System.out.println(a);
            }).start();
        }

    }

    public static void main(String[] args) {
        new ThreadTest().test();
    }
}
