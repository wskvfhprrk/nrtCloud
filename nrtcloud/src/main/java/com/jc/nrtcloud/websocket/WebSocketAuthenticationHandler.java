package com.jc.nrtcloud.websocket;

import com.jc.nrtcloud.sevice.JwtTokenUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class WebSocketAuthenticationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            if (req.uri().contains("?token=")) {
                String token = req.uri().split("\\?token=")[1];
                if (token != null && !token.equals("null") && JwtTokenUtil.validateToken(token)) {
                    ctx.fireChannelRead(msg);
                } else {
                    ctx.close();
                }
            }
            // 由于webSocket不支持头部传送数据，所以不能从头部提取 token (处理 Bearer token 格式)
        } else {
            super.channelRead(ctx, msg);
        }
    }

    // 可选：自定义方法从 WebSocket 消息中提取 token（例如在 URL 参数中）
//    private String extractTokenFromMessage(String message) {
//        // 假设 token 以某种方式嵌入在 WebSocket 消息中
//        // 可以根据实际情况来编写提取逻辑，例如从消息体中解析
//        return null;  // 返回 Token 字符串
//    }
}
