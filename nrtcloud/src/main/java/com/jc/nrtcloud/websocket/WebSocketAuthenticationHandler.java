package com.jc.nrtcloud.websocket;

import com.jc.nrtcloud.sevice.JwtTokenUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class WebSocketAuthenticationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            String token = req.headers().get("Authorization");
            
            // 验证 Token
            if (token != null && JwtTokenUtil.validateToken(token)) {
                // 允许 WebSocket 连接
                ctx.fireChannelRead(msg);  
            } else {
                // 关闭连接，禁止访问
                ctx.close();
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
