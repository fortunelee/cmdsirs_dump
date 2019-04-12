package com.scheduled;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ScheduledThreadPoolTest {


    public static void main(String[] args) {


        //创建一个呗观察者
        ConcreteSubject con = new ConcreteSubject();
        //创建一个观察者
        Observer observer = new ConcreteObserver();
        //添加到被观察者类中
        con.addObserver(observer);
        //完成某项任务，实现更新

        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(6,new ThreadPoolExecutor.CallerRunsPolicy());

        con.doSomething(pool);
    }
}
