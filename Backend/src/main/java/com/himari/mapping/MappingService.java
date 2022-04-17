/*
 * Copyright (c)  2022 Daniel Fiala
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

package com.himari.mapping;

import com.himari.response.Response;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

public final class MappingService {

    private static final MappingService instance = new MappingService();

    private MappingContainer container = new MappingContainer();

    public Response<?> route(HttpMethod method, String url) {
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        Mapping<?> mapping = container.findMapping(decoder.uri());

        System.out.println("Routing: " + decoder.uri());

        if (mapping == null) {
            Mapping<?> defaultMapping = container.findMapping("/");
            if (defaultMapping == null) {
                System.out.println("Empty response.");
                return Response.EMPTY_RESPONSE;
            }

            System.out.println("Default value is valid.");
            return defaultMapping.handle();
        }

        System.out.println("Mapped value is valid.");
        System.out.println("Value: " + mapping.handle().getResponse());
        return mapping.handle();
    }

    public boolean registerMapping(String path, Mapping<?> mapping) {
        if (container.getMappings().containsKey(path)) {
            return false;
        }

        container.addMapping(path, mapping);
        return true;
    }

    public MappingContainer getContainer() {
        return container;
    }

    public static MappingService getService() {
        return instance;
    }
}
