package com.alan.springbootbase.threads;

/**
 * @author Alan
 * @Description
 * @date 2020年03月24日 15:46
 */
public class CreateThread2 implements Runnable{

    int count=1, number;
    public CreateThread2(int num) {
        number = num;
        System.out.println("创建线程 " + number);
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("线程 " + number + ":计数 " + count);
            if(++count== 6) return;
        }
    }


    public static void main(String args[]) {
        for(int i = 0; i < 5; i++)
            new Thread(new CreateThread2(i+1)).start();
    }

}
