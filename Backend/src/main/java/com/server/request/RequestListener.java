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

import com.server.network.http.HttpMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public abstract class RequestListener extends SimpleChannelInboundHandler<HttpMapper.RequestWrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpMapper.RequestWrapper request) throws Exception {
        this.handleRequest(channelHandlerContext, request);
    }

    public abstract void handleRequest(ChannelHandlerContext ctx, HttpMapper.RequestWrapper request);
}
