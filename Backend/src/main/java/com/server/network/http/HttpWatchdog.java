package com.server.network.http;

import com.server.Environment;
import com.server.request.RequestWrapper;
import com.server.response.StringResponse;
import io.netty.channel.ChannelHandlerContext;

public class HttpWatchdog extends HttpAdapter {

    private final Environment environment;

    public HttpWatchdog(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestWrapper request = (RequestWrapper) msg;

        if (!request.validate()) {
            writeResponse(ctx, request, StringResponse.BAD_REQUEST);
            return;
        }

        ctx.fireChannelRead(msg);
    }

    public Environment getEnvironment() {
        return environment;
    }
}
