package com.server.network.http;

import com.server.Environment;
import com.server.request.RequestMethod;
import com.server.request.RequestWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpOptionsHandler extends ChannelInboundHandlerAdapter {

    private final Environment environment;

    public HttpOptionsHandler(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestWrapper request = (RequestWrapper) msg;

        if (request.getRequest().getRequestType().equals(RequestMethod.OPTIONS)) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            List<HttpMethod> requestedMethods =
                    request.getRequest().hasHeader(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD) ?
                            Arrays.stream(request.getRequest().getHeader(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD).split(","))
                                    .map(HttpMethod::valueOf).collect(Collectors.toUnmodifiableList()) : null;

            List<AsciiString> requestedHeaders =
                    request.getRequest().hasHeader(HttpHeaderNames.ACCESS_CONTROL_REQUEST_HEADERS) ?
                            Arrays.stream(request.getRequest().getHeader(HttpHeaderNames.ACCESS_CONTROL_REQUEST_HEADERS).split(","))
                                    .map(AsciiString::of).collect(Collectors.toUnmodifiableList()) : null;

            if (request.getMapping() != null) {
                if (requestedMethods != null) {
                    for (HttpMethod method : requestedMethods) {
                        if (!request.getMapping().getSecurity().isAllowedMethod(method.name())) {
                            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
                            ctx.writeAndFlush(response);
                            return;
                        }
                    }
                }

                if (requestedHeaders != null) {
                    for (AsciiString header : requestedHeaders) {
                        if (!request.getMapping().getSecurity().isAllowedHeader(header.toString())) {
                            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
                            ctx.writeAndFlush(response);
                            return;
                        }
                    }
                }

                if (request.getMapping().getSecurity().getAllowedMethods() != null && request.getMapping().getSecurity().getAllowedMethods().length > 0) {
                    response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS,
                            Arrays.toString(request.getMapping().getSecurity().getAllowedMethods()).replace("[", "").replace("]", ""));
                }

                if (request.getMapping().getSecurity().getAllowedHeaders() != null && request.getMapping().getSecurity().getAllowedHeaders().length > 0) {
                    response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS,
                            Arrays.toString(request.getMapping().getSecurity().getAllowedHeaders()).replace("[", "").replace("]", ""));
                }

                if (request.getMapping().getSecurity().getExposedHeaders() != null && request.getMapping().getSecurity().getExposedHeaders().length > 0) {
                    response.headers().set(HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS,
                            Arrays.toString(request.getMapping().getSecurity().getExposedHeaders()).replace("[", "").replace("]", ""));
                }
            }


            if (requestedHeaders != null && requestedHeaders.contains(HttpHeaderNames.AUTHORIZATION) &&
                    request.getRequest().hasHeader(HttpHeaderNames.ORIGIN) &&
                    request.getMapping() != null && request.getMapping().getSecurity().isAllowCredentials()) {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,
                        request.getRequest().getHeader(HttpHeaderNames.ORIGIN));
            } else {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }

            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                    request.getMapping() != null && request.getMapping().getSecurity().isAllowCredentials() ? "true"
                            : "false");

            if (request.getMapping() != null && request.getMapping().getSecurity().getMaxAge() > -1) {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_MAX_AGE,
                        request.getMapping().getSecurity().getMaxAge());
            }

            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);

            ctx.writeAndFlush(response);
        } else {
            ctx.fireChannelRead(request);
        }
    }

    public Environment getEnvironment() {
        return environment;
    }
}
