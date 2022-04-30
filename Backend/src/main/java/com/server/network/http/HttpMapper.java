package com.server.network.http;

import com.server.Environment;
import com.server.mapping.MappingHolder;
import com.server.mapping.MappingService;
import com.server.request.Request;
import com.server.request.RequestWrapper;
import com.server.response.StringResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class HttpMapper extends HttpAdapter {

    private final Environment environment;

    public HttpMapper(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }

        Request request = Request.buildRequest((FullHttpRequest) msg);
        String route = new QueryStringDecoder(((FullHttpRequest) msg).uri()).path();

        MappingHolder<?> mapping = MappingService.getService().getContainer().findMapping(route);

        if (mapping == null) {
            writeResponse(ctx, new RequestWrapper(request, null), StringResponse.FORBIDDEN);
            return;
        }

        ctx.fireChannelRead(new RequestWrapper(request, mapping));
    }

    public Environment getEnvironment() {
        return environment;
    }
}
