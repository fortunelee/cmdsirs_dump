package com.scheduled;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConcreteSubject extends Subject{


        public void doSomething(ScheduledThreadPoolExecutor pool){

            System.out.println("被观察者完成操作....");

            pool.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
                pool.schedule(new Runnable() {

                    @Override
                    public void run() {
                        Integer arr[] = new Integer[]{2,4,3,6,5,7,8,12,10};
                        try {
                            System.out.println("pool start :" + pool.isShutdown());
                            for (int i = 0; i < arr.length; i++) {
                                if (arr[i] % 2 == 0) {
                                    System.out.println("now system number is " + arr[i] + "");
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    System.out.println("now system number is " + arr[i] + "");
                                    int in = 1 / 0;
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }catch (Throwable throwable){
                            System.out.println("pool stop :" + pool.isShutdown());
                        }
                    }
                }, 2,TimeUnit.SECONDS);

        }

}
