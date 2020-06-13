package org.nettystudy.first.chapter2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ChannelInboundHandler定义响应入站事件的方法，ChannelInboundHandlerAdapter为适配方法
 */
@Sharable // 该注解标记这个handler可以被多个channel安全的共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);// 将收到的消息写给发送者
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 冲刷消息（），并关闭channel
		System.out.println("channelReadComplete");
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
