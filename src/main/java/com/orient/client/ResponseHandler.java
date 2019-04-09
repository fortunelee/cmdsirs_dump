package com.orient.client;

import com.orient.fixd.FixDPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseHandler extends MessageToByteEncoder<FixDPacket>{
	@Override
	protected void encode(ChannelHandlerContext ctx, FixDPacket msg, ByteBuf buf) throws Exception {
		
		String msgStr = msg.generate();
		buf.writeBytes(msgStr.getBytes("UTF-8"));
		//ctx.writeAndFlush(buf);
	}
}
