package org.nettystudy.first.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * DatagramChannel 一个可以接收和发送 udp 数据包的channel。
 *
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)    //广播
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)// 设置UDP读缓冲区为2M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(NioDatagramChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(7397).sync();
            System.out.println("itstack-demo-netty udp server start done. {关注公众号：bugstack虫洞栈，获取源码}");
            f.channel().closeFuture().sync();
        } finally {
            //优雅的关闭释放内存
            group.shutdownGracefully();
        }

    }
}
