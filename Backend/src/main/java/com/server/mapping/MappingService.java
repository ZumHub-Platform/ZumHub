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

package com.server.mapping;

import com.google.common.reflect.ClassPath;
import com.reflection.ClassMapper;
import com.reflection.MethodMapper;
import com.server.mapping.annotation.Controller;
import com.server.request.Request;
import com.server.response.Response;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public final class MappingService {

    private static final MappingService instance = new MappingService();

    private final Logger logger = LoggerFactory.getLogger(MappingService.class);
    private final MappingContainer container = new MappingContainer();

    public Response<?> route(FullHttpRequest request, String url) {
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        String route = decoder.path();

        Mapping<?> mapping = container.findMapping(route);

        System.out.println("Mapping: " + route);
        System.out.println("Routing: " + decoder.uri());
        Request req = Request.buildRequest(request);

        if (mapping == null) {
            Mapping<?> defaultMapping = container.findMapping("/");
            if (defaultMapping == null) {
                return Response.EMPTY_RESPONSE;
            }

            if (req.validateRequestHeaders(defaultMapping.getRequiredHeaders()) &&
                    req.validateRequestParameters(defaultMapping.getRequiredParameters())) {
                return defaultMapping.handle(Request.buildRequest(request));
            } else {
                throw new IllegalArgumentException("Request doesn't match required headers or parameters.");
            }
        }

        if (req.validateRequestHeaders(mapping.getRequiredHeaders()) &&
                req.validateRequestParameters(mapping.getRequiredParameters())) {
            return mapping.handle(Request.buildRequest(request));
        } else {
            throw new IllegalArgumentException("Request doesn't match required headers or parameters.");
        }
    }

    public boolean registerMapping(String path, Mapping<?> mapping) {
        if (container.getMappings().containsKey(path)) {
            return false;
        }

        container.addMapping(path, mapping);

        logger.info("Registered mapping: " + path);
        return true;
    }

    public void findMappings(String packageName) {
        Class<?>[] classes = new ClassMapper(packageName).addRequiredAnnotation(Controller.class).mapClasses();
        if (classes.length == 0) {
            return;
        }

        for (Class<?> clazz : classes) {
            Method[] methods = new MethodMapper(clazz)
                    .addRequiredAnnotation(com.server.mapping.annotation.Mapping.class)
                    .addRequiredParameterType(Request.class)
                    .setReturnType(Response.class)
                    .mapMethods();

            for (Method method : methods) {
                Mapping<?> mapping = new Mapping<String>() {

                    @Override
                    public Response<String> handle(Request request) {
                        if (request.getRequestType() != method.getAnnotation(com.server.mapping.annotation.Mapping.class).method()) {
                            return Response.EMPTY_RESPONSE;
                        }

                        try {
                            Response<?> response = method.invoke(clazz.getDeclaredConstructor().newInstance(), request) == null ?
                                    Response.EMPTY_RESPONSE : (Response<?>) method.invoke(clazz.getDeclaredConstructor().newInstance(), request);
                            return (Response<String>) response;
                        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                                 NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };

                registerMapping(clazz.getAnnotation(Controller.class).defaultPath().length() == 0 ?
                        method.getAnnotation(com.server.mapping.annotation.Mapping.class).value() :
                        clazz.getAnnotation(Controller.class).defaultPath() +
                                method.getAnnotation(com.server.mapping.annotation.Mapping.class).value(), mapping);
            }
        }
    }

    public MappingContainer getContainer() {
        return container;
    }

    public static MappingService getService() {
        return instance;
    }
}
