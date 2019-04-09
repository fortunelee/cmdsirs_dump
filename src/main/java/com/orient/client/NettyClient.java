package com.orient.client;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.orient.message.MsgType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orient.common.ReadConfig;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {
	private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
	
	private String host = "127.0.0.1";
	
	private int port = 9977;
	
	private String user = "";
	private String pwd = "";
	
	private ChannelFutureListener listener = null;
	private String bondStr = "ALL";
	
	private ReadConfig config = null;
	
	private HBTask hbTask = new HBTask();
	public HBTask getHBTask() 
	{
		return hbTask;
	}
	
	private BlockingQueue<MsgType> jsonQueue = null;
	public BlockingQueue<MsgType> getJsonQueue()
	{
		return jsonQueue;
	}
	
	public NettyClient(ReadConfig config, BlockingQueue<MsgType> jsonQueue)
	{
		//boolean ret = config.loadConfig(pathFileName);
		this.config = config;
		this.jsonQueue = jsonQueue;
		
		if (true) {
			init();
		}
	}
	
	public boolean init()
	{
		String host = config.getConfigItem("server.host");
		if (!(host == null || host.isEmpty()))
			this.host = host;
		
		int port = config.getConfigItemInteger("server.port");
		if (port > 0)
			this.port = port;
			
		String user = config.getConfigItem("user");
		if (!(user == null || user.isEmpty()))
			this.user = user;
		
		String pwd = config.getConfigItem("pwd");
		if (!(pwd == null || pwd.isEmpty()))
			this.pwd = pwd;
		
		String bond = config.getConfigItem("BOND");
		if (!(bond == null || bond.isEmpty()))
			this.bondStr = bond;
		
		hbTask.start();
		return true;
	}
	
	public void connect()
	{
		NettyClient thisclient = this;
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		 .channel(NioSocketChannel.class)
		 .option(ChannelOption.TCP_NODELAY, true)
		 .handler(new LoggingHandler(LogLevel.INFO))
		 .handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				
				ch.pipeline().addLast(new LineBasedFrameDecoder(256 * 1024, true, true));
				ch.pipeline().addLast(new ResponseHandler());
				ch.pipeline().addLast(new RequestHandler(user, pwd, thisclient, bondStr));
			}
		});
		
		listener = new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				if (f.isSuccess()) {
					//System.out.println("connect ok.");
					logger.info("connect ok.");
				}
				else
				{
					//System.out.println("reconnecting...");
					logger.info("reconnecting...");
					f.channel().eventLoop().schedule(new Runnable() {
						@Override
						public void run() {
							connect();
						}
					}, 2, TimeUnit.SECONDS);
				}
			}
		};
		
		ChannelFuture future = b.connect(new InetSocketAddress(host, port));
		future.addListener(listener);
		
		try {
			future.channel().closeFuture().sync();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
		
		//System.out.println("finish from connect");
		logger.info("finish from connect");
	}
}
