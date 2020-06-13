package org.nettystudy.first.udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(NioDatagramChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
                            pipeline.addLast(new MyClientHandler());
                        }
                    });
            Channel ch = b.bind(7398).sync().channel();
            //向目标端口发送信息
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("你好端口7397的bugstack虫洞栈，我是客户端小爱，你在吗！", Charset.forName("GBK")),
                    new InetSocketAddress("127.0.0.1", 7397))).sync();
            ch.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
