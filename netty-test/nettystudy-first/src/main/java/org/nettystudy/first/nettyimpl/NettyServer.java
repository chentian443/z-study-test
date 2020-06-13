package org.nettystudy.first.nettyimpl;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author 闪电侠
 */
public class NettyServer {
	
    public static void main(String[] args) {
    	
    	/**
    	 * 要启动一个Netty服务端，必须要指定三类属性，分别是线程模型、IO 模型、连接读写处理逻辑，
    	 * 有了这三者，之后在调用bind(8000)，就可以在本地绑定一个 8000 端口启动起来
    	 */
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 主要负责创建新连接（监听端口）；BOSS线程池实际上就是JAVA NIO框架中selector工作角色
        // Netty是支持同时监听多个端口的，所以BOSS线程池的大小按照需要监听的服务器端口数量进行设置就行了
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        
        //WORK线程池：这样的申明方式，主要是为了向读者说明Netty的线程组是怎样工作的
        ThreadFactory threadFactory = new DefaultThreadFactory("work thread pool");
        //CPU个数
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        // 负责读取数据的线程，主要用于读取数据以及业务逻辑处理（处理数据的线程组）
        // 大小一般是物理机器/虚拟机器 可用内核的个数 * 2
        NioEventLoopGroup worker = new NioEventLoopGroup(processorsNumber * 2, 
        		threadFactory, SelectorProvider.provider());
        
        serverBootstrap
        		// 给引导类配置两大线程组，这个引导类的线程模型也就定型了
                .group(boss, worker)
                // 指定 IO 模型，当然，这里也有其他的选择，如果你想指定 IO 模型为 BIO，那么这里配置上OioServerSocketChannel.class类型即可，
                // 当然通常我们也不会这么做，因为Netty的优势就在于NIO。(只能是实现了ServerChannel接口的“服务器”通道类)
                .channel(NioServerSocketChannel.class)
                // 给这个引导类创建一个ChannelInitializer，在initChannel方法中定义后续每条连接的数据读写，业务处理逻辑
                // Netty的特色就在这一连串“通道水管”中的“处理器”
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        // 添加channelHandler，上面的StringDecoder也实现了ChannelHandler
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000)
                // 对绑定端口添加一个监听  
        		.addListener(new GenericFutureListener<Future<? super Void>>() {
		            public void operationComplete(Future<? super Void> future) {
		                if (future.isSuccess()) {
		                    System.out.println("端口绑定成功!");
		                } else {
		                    System.err.println("端口绑定失败!");
		                }
		            }
        		});
    }
}

