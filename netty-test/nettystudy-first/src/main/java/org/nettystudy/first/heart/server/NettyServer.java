package org.nettystudy.first.heart.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;

public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bing(7397);
    }

    private void bing(int port) {
        //配置服务端NIO线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            /**
                             * 心跳监测
                             * 1、readerIdleTimeSeconds 读超时时间
                             * 2、writerIdleTimeSeconds 写超时时间
                             * 3、allIdleTimeSeconds    读写超时时间
                             * 4、TimeUnit.SECONDS 秒[默认为秒，可以指定]
                             */
                            pipeline.addLast(new IdleStateHandler(10, 25, 40));
                            // 基于换行符号
                            pipeline.addLast(new LineBasedFrameDecoder(1024));
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            pipeline.addLast(new StringDecoder(Charset.forName("GBK")));
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
                            pipeline.addLast(new HeartServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            System.out.println("itstack-demo-netty server start done. {关注公众号：bugstack虫洞栈，获取源码}");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }

    }
}
