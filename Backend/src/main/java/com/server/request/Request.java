package com.server.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    private final RequestType requestType;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final ByteBuf body;

    public Request(RequestType requestType, Map<String, String> headers, Map<String, String> parameters, ByteBuf body) {
        this.requestType = requestType;
        this.headers = headers;
        this.parameters = parameters;
        this.body = body;
    }

    public static Request buildRequest(FullHttpRequest request) {
        RequestType requestType = RequestType.valueOf(request.method().name());
        Map<String, String> headers = request.headers().entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String[] parameters = request.uri().split("\\?");
        Map<String, String> parametersMap;
        if (parameters.length > 1) {
            parametersMap = Arrays.stream(parameters[1].split("&")).collect(Collectors.toMap(parameter -> parameter.split("=")[0], parameter -> parameter.split("=")[1]));
        } else {
            parametersMap = new HashMap<>(0);
        }

        ByteBuf body = request.content();
        return new Request(requestType, headers, parametersMap, body);
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public ByteBuf getBody() {
        return body;
    }
}
