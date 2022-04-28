/*
 * Copyright (c)  2021 Daniel Fiala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.server.request;

import com.google.gson.Gson;
import com.server.mapping.MappingService;
import com.server.response.Content;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class DefaultRequestListener extends RequestListener {

    @Override
    public void handleRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        MappingService mappingService = MappingService.getService();
        Response<?> mappedResponse;
        try {
            mappedResponse = mappingService.route(msg, msg.uri());
        } catch (Exception e) {
            mappedResponse = new StringResponse(new Gson().toJson(e.getMessage()));
        }

        if (mappedResponse == null) {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND));
            return;
        }

        if (mappedResponse.isCancelled()) {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT));
            return;
        }

        if (mappedResponse instanceof StringResponse) {
            if (mappedResponse.isDone()) {
                writeResponse(ctx, msg, RequestType.valueOf(msg.method().name()), mappedResponse);
            } else {
                Response<?> finalMappedResponse = mappedResponse;
                mappedResponse.getAsyncContent().thenAccept(content ->
                        writeResponse(ctx, msg, RequestType.valueOf(msg.method().name()), finalMappedResponse));
            }
        }
    }

    private void writeResponse(ChannelHandlerContext ctx, FullHttpRequest msg, RequestType requestType,
                               Response<?> mappedResponse) {
        FullHttpResponse response;
        if (mappedResponse.getContent().getContent() == null) {
            Content<?> content = mappedResponse.getContent();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, mappedResponse.getStatus());

            if (requestType.hasBody()) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, 0);
            }

            if (msg.headers().contains(HttpHeaderNames.ORIGIN) && msg.headers().contains(HttpHeaderNames.AUTHORIZATION)) {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,
                        msg.headers().get(HttpHeaderNames.ORIGIN));
            } else {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }

            content.getHeaders().forEach((name, value) -> response.headers().set(name, value));
        } else {
            Content<?> content = mappedResponse.getContent();
            ByteBuf contentBuffer = Unpooled.copiedBuffer(mappedResponse.getContent().getContentAsString(),
                    CharsetUtil.UTF_8);

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, mappedResponse.getStatus(), contentBuffer);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, content.getType().getContentType());
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentBuffer.readableBytes());

            if (msg.headers().contains(HttpHeaderNames.ORIGIN) && msg.headers().contains(HttpHeaderNames.AUTHORIZATION)) {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,
                        msg.headers().get(HttpHeaderNames.ORIGIN));
            } else {
                response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }

            content.getHeaders().forEach((name, value) -> response.headers().set(name, value));
        }

        ctx.write(response);
        ctx.flush();
    }
}
