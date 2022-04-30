package com.server.request;

import com.server.mapping.MappingHolder;
import io.netty.util.AsciiString;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RequestWrapper {
    private final Request request;
    private final MappingHolder<?> mapping;

    public RequestWrapper(Request request, MappingHolder<?> mapping) {
        this.request = request;
        this.mapping = mapping;
    }

    public boolean validate() {
        if (!request.validateRequestParameters(Arrays
                .stream(mapping.getRequiredParameters()).collect(Collectors.toUnmodifiableList()))) {
            return false;
        }

        if (!request.validateRequestHeaders(Arrays
                .stream(mapping.getRequiredHeaders()).map(AsciiString::toString).collect(Collectors.toUnmodifiableList()))) {
            return false;
        }

        return mapping.hasRestrictedRequestType(request.getRequestType());
    }

    public Request getRequest() {
        return request;
    }

    public MappingHolder<?> getMapping() {
        return mapping;
    }
}
