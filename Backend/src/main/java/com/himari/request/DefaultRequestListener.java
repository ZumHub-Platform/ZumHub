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

package com.himari.request;

import com.himari.builder.component.BodyComponent;
import com.himari.builder.component.divider.DividerComponent;
import com.himari.builder.component.graphic.LabelComponent;
import com.himari.builder.component.header.resources.Relationship;
import com.himari.builder.component.header.resources.Resource;
import com.himari.builder.page.Page;
import com.himari.builder.page.PageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class DefaultRequestListener extends RequestListener {

    @Override
    public void handleRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        System.out.println("Channel name: " + ctx.name());
        msg.headers().iterator().forEachRemaining(entry -> System.out.println("Entry: " + entry.getKey() + "  " + entry.getValue()));
        BodyComponent body = new BodyComponent();
        DividerComponent div = new DividerComponent();
        body.appendComponent(div);
        div.appendComponent(new LabelComponent("Test text"));

        div.setIdentifier("test");

        Page page = new Page("Test title....");
        page.getResourceList().add(new Resource("test.css", Relationship.STYLESHEET));
        page.setPageBody(body);

        PageFactory pageFactory = new PageFactory(page);
        String output = pageFactory.createHtml();

        System.out.println("Output: \n" + output);

        ByteBuf content = Unpooled.copiedBuffer(output, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        ctx.write(response);
        ctx.flush();
    }
}
