package com.server.network;

import com.server.Environment;
import com.server.request.Request;
import com.server.request.RequestType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpClientConfiguration extends ChannelInboundHandlerAdapter {

    private final Environment environment;

    public HttpClientConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        Request clientRequest = Request.buildRequest(request);

        System.out.println("Request: " + clientRequest.getRequestType() + " " + ((FullHttpRequest) msg).uri());

        if (clientRequest.getRequestType().equals(RequestType.OPTIONS)) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            List<HttpMethod> requestedMethods =
                    request.headers().contains(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD) ?
                            Arrays.stream(request.headers().getAll(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD)
                                    .toArray()).map(s -> HttpMethod.valueOf((String) s)).collect(Collectors.toUnmodifiableList()) : null;

            List<AsciiString> requestedHeaders =
                    request.headers().contains(HttpHeaderNames.ACCESS_CONTROL_REQUEST_HEADERS) ?
                            request.headers().getAll(HttpHeaderNames.ACCESS_CONTROL_REQUEST_HEADERS).stream().map(AsciiString::of).collect(Collectors.toUnmodifiableList()) : null;


            if (requestedMethods != null) {
                for (HttpMethod method : requestedMethods) {
                    if (!environment.getAllowedRequestTypes().contains(RequestType.valueOf(method.name()))) {
                        response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                        ctx.writeAndFlush(response);
                        return;
                    }
                }
            }

            if (requestedHeaders != null) {
                for (AsciiString headers : requestedHeaders) {
                    if (!environment.getAllowedHeaders().contains(headers)) {
                        response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                        ctx.writeAndFlush(response);
                        return;
                    }
                }
            }

            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS,
                    environment.getAllowedRequestTypes().size() == 0 ? "*" :
                            Arrays.toString(environment.getAllowedRequestTypes().toArray())
                                    .replace("[", "").replace("]", ""));

            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS,
                    environment.getAllowedHeaders().size() == 0 ? "*" :
                            Arrays.toString(environment.getAllowedHeaders().toArray())
                                    .replace("[", "").replace("]", ""));

            if (requestedHeaders != null && requestedHeaders.contains(HttpHeaderNames.AUTHORIZATION) &&
                    request.headers().contains(HttpHeaderNames.ORIGIN) && environment.isAccessControlAllowCredentials()) {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,
                        request.headers().get(HttpHeaderNames.ORIGIN));
            } else {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }

            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                    environment.isAccessControlAllowCredentials() ? "true" : "false");

            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, environment.getAccessControlMaxAge());
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
