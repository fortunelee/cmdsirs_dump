package com.scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

abstract class Subject {



    //定义一个观察者集合
    private List<Observer> observerList = new ArrayList<Observer>();

    //添加一个观察者
    public void addObserver(Observer o){
        this.observerList.add(o);
    }
    //删除一个观察者
    public void removeObserver(Observer o){
        this.observerList.remove(o);
    }
    //通知所有的观察者
    public void notifyObserver(ScheduledExecutorService pool,boolean b){
        for(Observer o : observerList){
            o.sys(pool,b);
        }
    }
}
