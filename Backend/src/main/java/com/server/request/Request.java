package com.server.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AsciiString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    private final RequestMethod requestMethod;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final ByteBuf body;

    public Request(RequestMethod requestMethod, Map<String, String> headers, Map<String, String> parameters,
                   ByteBuf body) {
        this.requestMethod = requestMethod;
        this.headers = headers;
        this.parameters = parameters;
        this.body = body;
    }

    public static Request buildRequest(FullHttpRequest request) {
        RequestMethod requestMethod = RequestMethod.fromString(request.method().name());
        Map<String, String> headers =
                request.headers().entries().stream().map(e -> new HashMap.SimpleEntry<>(e.getKey().toLowerCase(),
                        e.getValue().toLowerCase())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String[] parameters = request.uri().split("\\?");
        Map<String, String> parametersMap;
        if (parameters.length > 1) {
            parametersMap =
                    Arrays.stream(parameters[1].split("&")).collect(Collectors.toMap(parameter -> parameter.split("=")[0], parameter -> parameter.split("=")[1]));
        } else {
            parametersMap = new HashMap<>(0);
        }

        ByteBuf body = request.content();
        return new Request(requestMethod, headers, parametersMap, body);
    }

    public boolean validateRequestHeaders(List<String> headers) {
        if (this.headers == null) {
            return false;
        }

        for (String header : headers) {
            if (!this.headers.containsKey(header)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateRequestParameters(List<String> parameters) {
        if (this.headers == null) {
            return false;
        }

        for (String parameter : parameters) {
            if (!this.parameters.containsKey(parameter)) {
                return false;
            }
        }

        return true;
    }

    public RequestMethod getRequestType() {
        return requestMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String header) {
        return headers.get(header.toLowerCase());
    }

    public String getHeader(AsciiString header) {
        return headers.get(header.toString().toLowerCase());
    }

    public boolean hasHeader(String header) {
        return headers.containsKey(header);
    }

    public boolean hasHeader(AsciiString header) {
        return hasHeader(header.toString());
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public boolean hasParameter(String parameter) {
        return parameters.containsKey(parameter);
    }

    public ByteBuf getBody() {
        return body;
    }
}
