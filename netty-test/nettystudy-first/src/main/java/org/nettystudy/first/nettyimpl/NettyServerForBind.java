package org.nettystudy.first.nettyimpl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServerForBind {

	public static void main(String[] args) {

		/**
		 * 要启动一个Netty服务端，必须要指定三类属性，分别是线程模型、IO 模型、连接读写处理逻辑，
		 * 有了这三者，之后在调用bind(8000)，就可以在本地绑定一个 8000 端口启动起来
		 */
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 主要负责创建新连接（监听端口）
		NioEventLoopGroup boos = new NioEventLoopGroup();
		// 负责读取数据的线程，主要用于读取数据以及业务逻辑处理（处理数据的线程组）
		NioEventLoopGroup worker = new NioEventLoopGroup();
		serverBootstrap
				// 给引导类配置两大线程组，这个引导类的线程模型也就定型了
				.group(boos, worker)
				// 指定 IO 模型，当然，这里也有其他的选择，如果你想指定 IO 模型为
				// BIO，那么这里配置上OioServerSocketChannel.class类型即可，
				// 当然通常我们也不会这么做，因为Netty的优势就在于NIO。
				.channel(NioServerSocketChannel.class)
				// 给这个引导类创建一个ChannelInitializer，在initChannel方法中定义后续每条连接的数据读写，业务处理逻辑
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					protected void initChannel(NioSocketChannel ch) {
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
							@Override
							protected void channelRead0(ChannelHandlerContext ctx, String msg) {
								System.out.println(msg);
							}
						});
					}
				});
		
		bind(serverBootstrap, 1000);

	}

	/**
	 * 递归方式绑定端口，直到绑定成功
	 * 
	 * @param serverBootstrap
	 * @param port
	 */
	private static void bind(final ServerBootstrap serverBootstrap, final int port) {
		serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
			public void operationComplete(Future<? super Void> future) {
				if (future.isSuccess()) {
					System.out.println("端口[" + port + "]绑定成功!");
				} else {
					System.err.println("端口[" + port + "]绑定失败!");
					bind(serverBootstrap, port + 1);
				}
			}
		});
	}

}
