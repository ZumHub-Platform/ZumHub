package com.server.response;

import io.netty.util.AsciiString;

import java.util.HashMap;
import java.util.Map;

public class Content<T> {

    private final T content;
    private final ContentType type;

    private final Map<AsciiString, String> headers;

    public Content(T content, ContentType type) {
        this.content = content;
        this.type = type;
        this.headers = new HashMap<>();
    }

    public T getContent() {
        return content;
    }

    public String getContentAsString() {
        return content.toString();
    }

    public ContentType getType() {
        return type;
    }

    public Map<AsciiString, String> getHeaders() {
        return headers;
    }

    public void setHeader(AsciiString key, String value) {
        headers.put(key, value);
    }

    public void removeHeader(String key) {
        headers.remove(AsciiString.of(key));
    }
}
