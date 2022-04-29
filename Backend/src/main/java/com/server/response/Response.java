package com.server.response;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Response<T> {

    @NotNull
    CompletableFuture<Content<T>> getAsynchronousContent();

    @Nullable
    Content<T> getContent();

    @NotNull
    Response<T> thenApply(@NotNull Consumer<? super Content<T>> action);

    @NotNull
    HttpResponseStatus getStatus();

    Response<T> setStatus(@NotNull HttpResponseStatus status);

    boolean isDone();

    boolean isCancelled();

    boolean cancel(boolean mayInterruptIfRunning);

}
