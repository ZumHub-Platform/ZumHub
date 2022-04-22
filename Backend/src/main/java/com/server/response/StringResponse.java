package com.server.response;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StringResponse extends Response<String> {

    public StringResponse() {
    }

    public StringResponse(String response) {
        super(new Content<>(response, ContentType.APPLICATION_JSON));
    }

    public StringResponse(@NotNull CompletableFuture<Content<String>> content) {
        super(content);
    }

    @Override
    public Content<String> getContent() {
        return super.getContent();
    }

    @Override
    public CompletableFuture<Content<String>> getAsyncContent() {
        return super.getAsyncContent();
    }
}
