package com.orient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

public class HBTask extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(HBTask.class);
	
	private long lastRecvedHB = 0;
	
	private int hbCheckInterval = 5;
	private int hbTimeout = 15;
	
	private Channel conn = null;
	
	public HBTask() {
		setHBRecved();
	}
	
	public void setHBRecved() {
		this.lastRecvedHB = System.currentTimeMillis();
	}
	
	public void setChannel(Channel conn) {
		this.conn = conn;
	}
	
	@Override
	public void run() {
		int initialInterval = 2;
		
		while (true) {
			sleep(initialInterval); // sleep 2s
			
 			long now = System.currentTimeMillis();
			
			if (now > lastRecvedHB && now - lastRecvedHB > hbTimeout * 1000)
			{
				//if (conn != null && conn.isActive()) {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
					}
					
					String msg = String.format("now:%d,last_recv_hb:%d,timeout:%d", now, lastRecvedHB, (int)((now - lastRecvedHB) / 1000));
//					System.out.println(msg);
					logger.info(msg);
				}
			}
			else
			{
				String msg = String.format("HBCheck ok, now:%d,last_recv_hb:%d,interval:%d", now, lastRecvedHB, (int)((now - lastRecvedHB) / 1000));
//				System.out.println(msg);
				logger.info(msg);
			}
				
			sleep(hbCheckInterval - initialInterval); // sleep 3s
		}
	} 
	
	private void sleep(int secs)
	{
		secs = secs > 0 ? secs : 5;
		try {
			Thread.sleep(secs * 1000);
		} catch(InterruptedException e) {
			
		}
	}
}
