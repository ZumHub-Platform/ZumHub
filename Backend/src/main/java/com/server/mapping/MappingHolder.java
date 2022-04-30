package com.server.mapping;

import com.server.request.Request;
import com.server.request.RequestMethod;
import com.server.response.Response;
import io.netty.util.AsciiString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MappingHolder<R> {

    private final String path;
    private final MappingHandler<R> handler;
    private final MappingSecurity security;

    private RequestMethod[] restrictedRequestMethods;
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

    public Response<R> handle(Request request) throws Exception {
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

    public RequestMethod[] getRestrictedRequestTypes() {
        return restrictedRequestMethods;
    }

    public MappingHolder<R> setRestrictedRequestTypes(RequestMethod[] restrictedRequestMethods) {
        this.restrictedRequestMethods = restrictedRequestMethods;
        return this;
    }

    public boolean hasRestrictedRequestType(RequestMethod requestMethod) {
        if (restrictedRequestMethods == null) {
            return false;
        }

        for (RequestMethod restrictedRequestMethod : restrictedRequestMethods) {
            if (restrictedRequestMethod == requestMethod) {
                return true;
            }
        }
        return false;
    }

    public AsciiString[] getRequiredHeaders() {
        return requiredHeaders;
    }

    public MappingHolder<R> setRequiredHeaders(AsciiString[] requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
        return this;
    }

    public boolean hasRequiredHeaders(AsciiString header) {
        if (requiredHeaders == null) {
            return false;
        }

        for (AsciiString requiredHeader : requiredHeaders) {
            if (requiredHeader.contentEquals(header)) {
                return true;
            }
        }
        return false;
    }

    public String[] getRequiredParameters() {
        return requiredParameters;
    }

    public MappingHolder<R> setRequiredParameters(String[] requiredParameters) {
        this.requiredParameters = requiredParameters;
        return this;
    }

    public boolean hasRequiredParameter(String parameter) {
        if (requiredParameters == null) {
            return false;
        }

        for (String requiredParameter : requiredParameters) {
            if (requiredParameter.equals(parameter)) {
                return true;
            }
        }
        return false;
    }
}
