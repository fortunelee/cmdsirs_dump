package com.orient.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.orient.constants.Constants;
import com.orient.message.MsgType;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orient.fixd.FixDPacket;
import com.orient.fixd.LogonRequest;
import com.orient.fixd.SubscribeRequest;
import com.orient.utils.EncryptUtil;
import com.orient.utils.GZIPUtils;
import com.orient.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RequestHandler extends SimpleChannelInboundHandler<ByteBuf>{

//	public static void main(String[] args) {
//		String msg = "8=FixD.1.0.0|9=103|35=WELCOME|52=2018-08-08 11:13:28|58=Welcome to FixD|1130=1.0.7|1400=c8d0bbd155494162a4185e17766f26c1|10=164|";
//		String [] sections = msg.split("\\|");
//		for (String sec : sections)
//			System.out.println(sec);
//	}
	
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	
	private String user;
	private String pwd;
	
	private NettyClient client = null;
	private String bondStr = "ALL";
	private boolean subscribeBondList = false;
	private List<String> bondList = null;
	private BlockingQueue<MsgType> jsonQueue = null;

	public RequestHandler(String user, String pwd, NettyClient client, String bondStr)
	{
		this.user = user;
		this.pwd = pwd;
		this.client = client;
		this.jsonQueue = client.getJsonQueue();
		if (jsonQueue == null) {
			logger.error("JsonQueue in RequestHandler is null and exits.");
			System.exit(1);
		}
		
		initSubscribe(bondStr);
	}
	
	private void initSubscribe(String bondStr)
	{
		if (bondStr.indexOf(",") > 0)
		{
			subscribeBondList = true;
			String[] bondArr = bondStr.split(",");
			bondList = java.util.Arrays.asList(bondArr);
			
			List<String> bondList2 = new ArrayList<>();
			for (String bond : bondList)
			{
				bondList2.add(bond.trim());
			}
			bondList = bondList2;
		}
		else
		{
			if (bondStr.equals("ALL"))
			{
				subscribeBondList = false;
			}
			else
			{
				bondStr = bondStr.trim();
				bondList = new ArrayList<>();
				bondList.add(bondStr);
				subscribeBondList = true;
			}
		}
	}
	
	private boolean processPacket(String msg, HashMap<Integer, String> outMap)
	{
		String [] sections = msg.split("\\|");
		for (int i = 0; i < sections.length; i++)
		{
			int idx = sections[i].indexOf("=");
			if (idx < 0)
				continue;
			
			String key = sections[i].substring(0, idx);
			String value = sections[i].substring(idx + 1);
			outMap.put(Integer.parseInt(key), value);
		}
		return true;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		int len = buf.readableBytes();
		byte[] bytes = new byte[len];
		buf.readBytes(bytes);
		
		String msg = new String(bytes, "UTF-8");
		HashMap<Integer, String> sections = new HashMap<>();
		if (processPacket(msg, sections))
		{
			String msgType = sections.get(35);
			if (msgType.equals("WELCOME"))
			{
				onWelcome(ctx, msg, sections);
			} 
			else if (msgType.equals("MD"))
			{
				onDepth(ctx, msg, sections);
			}
			else if (msgType.equals("TS"))
			{
				onTrans(ctx, msg, sections);
			} 
			else if (msgType.equals("MDF"))
			{
				onForwardDepth(ctx, msg, sections);
			}
			else if (msgType.equals("TSF"))
			{
				onForwardTrans(ctx, msg, sections);
			}
			else if (msgType.equals("STF"))
			{
				onForwardSettle(ctx, msg, sections);
			}
			else if (msgType.equals("0"))
			{
				onHeartBeat(ctx, msg, sections);
			}
			else if (msgType.equals("A"))
			{
				onLogonReturn(ctx, msg, sections);
			}
			else if (msgType.equals("V"))
			{
				onSubscribeReturn(ctx, msg, sections);
			}
			else if (msgType.equals("MDI"))
			{
				onInterestRateSwapDepth(ctx, msg, sections);
			}
			else if (msgType.equals("MDBI"))
			{
				onInterestRateSwapBest(ctx, msg, sections);
			}
			else if (msgType.equals("TSI"))
			{
				onInterestRateSwapTrans(ctx, msg, sections);
			}
			else if(msgType.equals("MDOI")){

				onInterestRateSwapDepthOI(ctx, msg, sections);
			}
			else if(msgType.equals("MDCI")){

				onInterestRateQuoteCommond(ctx, msg, sections);
			}
		}
	}

	private void onInterestRateQuoteCommond(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections) {

		String jsonEncoded = sections.get(355);
		// 封装QuoteCommond 行情类型的数据
		MsgType msgType = new MsgType(Constants.Common.MSGTYPE_MDCI,decodeMsg(jsonEncoded));
		jsonQueue.add(msgType);
		System.out.println("IRSQuoteCommond recved:" + decodeMsg(jsonEncoded));
	}

	private void onInterestRateSwapDepthOI(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections) {

		String jsonEncoded = sections.get(355);

		// 封装DepthSingle 行情类型的数据
		MsgType msgType = new MsgType(Constants.Common.MSGTYPE_MDOI,decodeMsg(jsonEncoded));
		jsonQueue.add(msgType);
		System.out.println("IRSDepthOI recved:" + decodeMsg(jsonEncoded));
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//System.out.println("connected to remote_server:" + ctx.channel().remoteAddress().toString());
		logger.info("connected to remote_server:" + ctx.channel().remoteAddress().toString());
		
		HBTask hbTask = client.getHBTask();
		hbTask.setChannel(ctx.channel());
		hbTask.setHBRecved();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//System.out.println("disconnected to remote_server:" + ctx.channel().remoteAddress().toString());
		logger.error("disconnected to remote_server:" + ctx.channel().remoteAddress().toString());
		
		Thread.sleep(3 * 1000);
		
		HBTask hbTask = client.getHBTask();
		hbTask.setChannel(null);
		
		if (client != null)
			client.connect();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exception caught:" + cause.getMessage());
		logger.error("exception caught:" + cause.getMessage());
		
		HBTask hbTask = client.getHBTask();
		hbTask.setChannel(null);
	}
	
	private void onHeartBeat(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
//		System.out.println("hb recved:" + msg);
		Channel conn = ctx.channel();
		logger.info("hb recved from: {}", conn.remoteAddress().toString());
		
		HBTask hbTask = client.getHBTask();
		hbTask.setHBRecved();
	}
	
	private void onLogonReturn(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
//		System.out.println("logon recved:" + msg);
		logger.info("logon recved:" + msg);
		
		SubscribeRequest sr = null;
		if (subscribeBondList) {
			sr = new SubscribeRequest("", "BOND", bondList);
			//System.out.println("subscribe bonds list:" + StringUtils.join(bondList, ","));
			logger.info("subscribe bonds list:" + StringUtils.join(bondList, ","));
		} else {
			sr = new SubscribeRequest("", "BOND");
			//System.out.println("subscribe bond market.");
			logger.info("subscribe bond market.");
		}
		
		FixDPacket fixd = new FixDPacket(sr);
		//System.out.println(fixd.generate());
		ctx.writeAndFlush(fixd);
	}
	
	private void onSubscribeReturn(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
//		System.out.println("subscribe recved:" + msg);
		logger.info("subscribe recved:" + msg);
	}	
	
	private void onWelcome(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections) 
	{
		String identity = sections.get(1400);
		byte[] encrytedPwd = EncryptUtil.encryptPassword(identity, pwd);
		
		try {
			LogonRequest lr = new LogonRequest(user, new String(encrytedPwd, "UTF-8"), identity);
			FixDPacket fixd = new FixDPacket(lr);
			ctx.writeAndFlush(fixd);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void onTrans(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		System.out.println("trans recved:" + decodeMsg(jsonEncoded));
		
		// we do not need DataType of CashBondTrans 
	}
	
	public void onInterestRateSwapDepth(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		// 封装depth 行情类型数据
        MsgType msgType = new MsgType(Constants.Common.MSGTYPE_MDI,decodeMsg(jsonEncoded));
        jsonQueue.add(msgType);
		System.out.println("IRSDepth recved:" + decodeMsg(jsonEncoded));
		
		// we do not need DataType of IRSDepth 
	}
	
	public void onInterestRateSwapBest(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
        // 封装best 行情类型的数据
        MsgType msgType = new MsgType(Constants.Common.MSGTYPE_MDBI,decodeMsg(jsonEncoded));
        jsonQueue.add(msgType);
		System.out.println("IRSBest recved:" + decodeMsg(jsonEncoded));
		
		// we do not need DataType of IRSBest
	}
	
	public void onInterestRateSwapTrans(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		// that's our needs.
		String jsonEncoded = sections.get(355);
		String jsonDecoded = decodeMsg(jsonEncoded);
		// 封装trans 行情类型数据
		MsgType msgType = new MsgType(Constants.Common.MSGTYPE_TSI,jsonDecoded);
		jsonQueue.add(msgType);
		System.out.println("IRSTrans recved:" + jsonDecoded);
	}
	
	public static String decodeMsg(String jsonEncoded)
	{
		byte[] jsonBytes = null;
		try {
			jsonBytes = jsonEncoded.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		
		for (int i = jsonBytes.length - 1; i >= 0; i--)
		{
			if (jsonBytes[i] == (byte)'!')
				jsonBytes[i] = (byte)'=';
			else
				break;
		}
		
		byte[] base64Bytes = Base64.decodeBase64(jsonBytes);
		byte[] uncompressBytes = GZIPUtils.uncompress(base64Bytes, 256 * 1024);
		try {
			String ret = new String(uncompressBytes, "UTF-8");
			return ret;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void onForwardDepth(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		
		System.out.println("ForwardDepth recved:" + decodeMsg(jsonEncoded));
	}
	
	public void onForwardTrans(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		
		System.out.println("ForwardTrans recved:" + decodeMsg(jsonEncoded));
	}
	
	public void onForwardSettle(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		
		System.out.println("ForwardSettle recved:" + decodeMsg(jsonEncoded));
	}
	
	public void onDepth(ChannelHandlerContext ctx, String msg, HashMap<Integer, String> sections)
	{
		String jsonEncoded = sections.get(355);
		
		System.out.println("depth recved:" + decodeMsg(jsonEncoded));
	}
}
