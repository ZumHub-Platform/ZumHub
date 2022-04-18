package com.server.response;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class StringResponse extends Response<String> {

    public StringResponse() {
    }

    public StringResponse(String response) {
        super(response);
    }

    public StringResponse(@NotNull CompletableFuture<String> content) {
        super(content);
    }

    @Override
    public String getContent() {
        return super.getContent();
    }

    @Override
    public CompletableFuture<String> getAsyncContent() {
        return super.getAsyncContent();
    }
}
