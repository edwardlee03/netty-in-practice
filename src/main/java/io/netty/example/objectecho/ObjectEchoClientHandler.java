package io.netty.example.objectecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 * 对象echo客户端处理器实现，通过向服务器发送第一条消息来启动对象echo客户端和服务器之间的反复交换/乒乓流量。
 *
 * @since 2019-06-04
 */
class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ObjectEchoClientHandler.class);

    private final List<Integer> firstMessage;

    ObjectEchoClientHandler() {
        logger.info("Create ObjectEchoClientHandler");

        int size = 3;
        firstMessage = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            firstMessage.add(i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channelActive");
        // Send the first message if this handler is a client-side handler.
        // 发送第一条消息
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("channelRead");
        // Echo back the received object to the server.
        // 将收到的对象回送到服务器
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        logger.warn("exception caught", cause);
        ctx.close();
    }
}
