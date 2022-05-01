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

package com.server.network.http;

import com.server.request.RequestWrapper;
import com.server.response.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpHandler extends HttpAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestWrapper request = (RequestWrapper) msg;
        Response<?> response;

        try {
            response = request.getMapping().handle(request.getRequest());
        } catch (Exception e) {
            writeError(ctx, request, e);
            return;
        }

        if (response == null) {
            writeResponse(ctx, request, Response.EMPTY_RESPONSE);
            return;
        }

        if (response.isCancelled()) {
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT));
            return;
        }

        if (response.isDone()) {
            writeResponse(ctx, request, response);
        } else {
            response.thenAccept(content -> writeResponse(ctx, request, response));
        }
    }
}
