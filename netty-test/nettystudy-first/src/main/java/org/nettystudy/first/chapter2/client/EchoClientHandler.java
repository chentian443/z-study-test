package org.nettystudy.first.chapter2.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.3 ChannelHandler for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable  // 该注解标记这个handler可以被多个channel安全的共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	// 连接建立时调用
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("建立连接时调用！！");
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}
	// 每当接受数据时都将调用该方法
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
		System.out.println("接受数据时调用！！");
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
