package com.server.mapping;

import com.server.request.Request;
import com.server.request.RequestType;
import com.server.response.StringResponse;
import io.netty.util.AsciiString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MappingHolder<R> {

    private final String path;
    private final MappingHandler<R> handler;
    private final MappingSecurity security;

    private RequestType[] restrictedRequestTypes;
    private AsciiString[] requiredHeaders;
    private String[] requiredParameters;

    public MappingHolder(@NotNull String path, @NotNull MappingHandler<R> handler) {
        this.path = path;
        this.handler = handler;
        this.security = null;
    }

    public MappingHolder(@NotNull String path, @Nullable MappingSecurity security, @NotNull MappingHandler<R> handler) {
        this.path = path;
        this.security = security;
        this.handler = handler;
    }

    public StringResponse<R> handle(Request request) throws Exception {
        return handler.handle(request);
    }

    public String getPath() {
        return path;
    }

    public MappingSecurity getSecurity() {
        return security;
    }

    public MappingHandler<R> getHandler() {
        return handler;
    }

    public RequestType[] getRestrictedRequestTypes() {
        return restrictedRequestTypes;
    }

    public MappingHolder<R> setRestrictedRequestTypes(RequestType[] restrictedRequestTypes) {
        this.restrictedRequestTypes = restrictedRequestTypes;
        return this;
    }

    public AsciiString[] getRequiredHeaders() {
        return requiredHeaders;
    }

    public MappingHolder<R> setRequiredHeaders(AsciiString[] requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
        return this;
    }

    public String[] getRequiredParameters() {
        return requiredParameters;
    }

    public MappingHolder<R> setRequiredParameters(String[] requiredParameters) {
        this.requiredParameters = requiredParameters;
        return this;
    }
}
