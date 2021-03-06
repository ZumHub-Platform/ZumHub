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

package com.server.network;

import com.server.Environment;
import com.server.network.http.HttpHandler;
import com.server.network.http.HttpMapper;
import com.server.network.http.HttpOptionsHandler;
import com.server.network.http.HttpWatchdog;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class DefaultNetworkInitializer extends NetworkInitializer {

    private final Environment environment;

    public DefaultNetworkInitializer(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast(new HttpMapper(environment));
        pipeline.addLast(new HttpOptionsHandler(environment));
        pipeline.addLast(new HttpWatchdog(environment));
        pipeline.addLast(new HttpHandler());
    }
}
