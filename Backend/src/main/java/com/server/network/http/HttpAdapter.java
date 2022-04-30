package com.server.network.http;

import com.server.request.RequestWrapper;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.Arrays;

public abstract class HttpAdapter extends ChannelInboundHandlerAdapter {

    public <R> void writeResponse(ChannelHandlerContext ctx, RequestWrapper request, Response<R> response) {
        HttpResponse httpResponse;
        if (response instanceof StringResponse) {
            if (response.getContent() != null && response.getContent().getContent() != null && response.getContent().getContentAsString().length() > 0) {
                ByteBuf byteBuf = Unpooled.copiedBuffer(response.getContent().getContentAsString(), CharsetUtil.UTF_8);
                httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, response.getStatus(), byteBuf);
                httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            } else {
                httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, response.getStatus());
                httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
            }
        } else {
            return;
        }

        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, response.getContent().getType());
        if (request.getRequest().getHeader(HttpHeaderNames.ORIGIN.toString()) != null) {
            httpResponse.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,
                    request.getRequest().getHeader(HttpHeaderNames.ORIGIN.toString()));
        }

        if (request.getMapping() != null && request.getMapping().getSecurity().getExposedHeaders() != null && request.getMapping().getSecurity().getExposedHeaders().length > 0) {
            httpResponse.headers().set(HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS,
                    Arrays.toString(request.getMapping().getSecurity().getExposedHeaders()).replace("[", "").replace(
                            "]", ""));
        }

        ctx.writeAndFlush(httpResponse);
    }
}
