package com.orient.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.orient.message.MsgType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.orient.common.ReadConfig;
import com.orient.dump.TransDumper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainClass implements CommandLineRunner {


	private static final Logger logger = LoggerFactory.getLogger(MainClass.class);
	
	@Override
	public void run(String... args) throws Exception {

		ReadConfig config = new ReadConfig();
	/*	if (!config.loadConfig("cfg/server.cfg"))
		{
			logger.error("read cfg/server.cfg error.");
			return;
		}*/

		BlockingQueue<MsgType> jsonQueue = new LinkedBlockingQueue<MsgType>();
		new TransDumper(config, jsonQueue).start();
		NettyClient client = new NettyClient(config, jsonQueue);
		client.connect();

		while (true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
