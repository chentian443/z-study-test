package org.nettystudy.first.chapter2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
	
	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {

		int port = 8084;
		new EchoServer(port).start();
	}

	public void start() throws Exception {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		
		EventLoopGroup group = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		try {
			// 实例化引导类
			ServerBootstrap b = new ServerBootstrap();
			// group设置组，
			b.group(group, work)
			// 指定nio传输使用的channel
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					System.out.println(ch.remoteAddress().getHostString());
					System.out.println(ch.localAddress().getHostString());
					// EchoServerHandler被@Shareable修饰，所以每次将运用同样的实例
					ch.pipeline().addLast(serverHandler);
				}
			});

			ChannelFuture f = b.bind().sync(); //sync() 阻塞等待直到绑定完成
			System.out.println(EchoServer.class.getName() + " started and listening for connections on "
					+ f.channel().localAddress());
			// 获取channel的closeFuture，并阻塞直到它完成
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
			work.shutdownGracefully().sync();
		}
	}
}
