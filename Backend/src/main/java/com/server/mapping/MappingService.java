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

import com.reflection.ClassMapper;
import com.reflection.MethodMapper;
import com.server.mapping.annotation.Controller;
import com.server.mapping.annotation.Mapping;
import com.server.mapping.annotation.SecurityPolicy;
import com.server.request.Request;
import com.server.request.RequestMethod;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.util.AsciiString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class MappingService {

    private static final MappingService instance = new MappingService();

    private final Logger logger = LoggerFactory.getLogger(MappingService.class);
    private final MappingContainer container = new MappingContainer();

    public static MappingService getService() {
        return instance;
    }

    public boolean registerMapping(MappingHolder<?> mapping) {
        if (container.getMappings().containsKey(mapping.getPath())) {
            return false;
        }

        container.addMapping(mapping.getPath(), mapping);

        logger.info("Registered mapping: {}", mapping.getPath());
        return true;
    }

    public void findMappings(String packageName) {
        Class<?>[] classes = new ClassMapper(packageName).addRequiredAnnotation(Controller.class).mapClasses();
        if (classes.length == 0) {
            return;
        }

        for (Class<?> clazz : classes) {
            Controller controllerAnnotation = clazz.getAnnotation(Controller.class);
            SecurityPolicy clazzSecurityPolicy = clazz.getAnnotation(SecurityPolicy.class);

            Method[] methods = new MethodMapper(clazz)
                    .addRequiredAnnotation(com.server.mapping.annotation.Mapping.class)
                    .addRequiredParameterType(Request.class)
                    .setReturnType(StringResponse.class)
                    .mapMethods();

            for (Method method : methods) {
                Mapping mappingAnnotation = method.getAnnotation(Mapping.class);
                SecurityPolicy methodSecurityPolicy = method.getAnnotation(SecurityPolicy.class);

                MappingHandler<?> mapping = request -> {
                    try {
                        Response<?> response = method.invoke(clazz.getDeclaredConstructor().newInstance(),
                                request) == null ? StringResponse.EMPTY_RESPONSE :
                                (Response<?>) method.invoke(clazz.getDeclaredConstructor().newInstance(), request);
                        return (Response<Object>) response;
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                };

                String path = controllerAnnotation.defaultPath().length() == 0 ? mappingAnnotation.path() :
                        controllerAnnotation.defaultPath() + mappingAnnotation.path();

                MappingSecurity security = createMappingSecurity(clazzSecurityPolicy, methodSecurityPolicy);
                MappingHolder<?> holder = new MappingHolder<>(path, security, mapping);

                holder.setRestrictedRequestTypes(Arrays.stream(mappingAnnotation.method()).map(type -> RequestMethod.fromString(type.name())).toArray(RequestMethod[]::new));
                holder.setRequiredParameters(Arrays.stream(mappingAnnotation.parameters()).toArray(String[]::new));
                holder.setRequiredHeaders(Arrays.stream(mappingAnnotation.headers()).map(AsciiString::of).toArray(AsciiString[]::new));

                registerMapping(holder);
            }
        }
    }

    private MappingSecurity createMappingSecurity(SecurityPolicy clazzSecurityPolicy,
                                                  SecurityPolicy methodSecurityPolicy) {
        if (clazzSecurityPolicy != null && methodSecurityPolicy != null) {
            return new MappingSecurity(clazzSecurityPolicy, methodSecurityPolicy);
        } else if (clazzSecurityPolicy != null) {
            return new MappingSecurity(clazzSecurityPolicy);
        } else if (methodSecurityPolicy != null) {
            return new MappingSecurity(methodSecurityPolicy);
        } else {
            return new MappingSecurity(null, false, null, null, null, 8600);
        }
    }

    public MappingContainer getContainer() {
        return container;
    }
}
