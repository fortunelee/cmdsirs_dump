package com.scheduled;

import java.util.concurrent.ScheduledExecutorService;

public class ConcreteObserver implements Observer{




    @Override
    public void sys(ScheduledExecutorService pool,boolean b) {

        System.out.println("hi hi hi");
    }
}
