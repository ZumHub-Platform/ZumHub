package com.server.response;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface Response<T> {

    StringResponse EMPTY_RESPONSE = new StringResponse(HttpResponseStatus.BAD_REQUEST, new Content<>("",
            ContentType.APPLICATION_JSON));
    StringResponse OK_RESPONSE = new StringResponse(HttpResponseStatus.OK, new Content<>("",
            ContentType.APPLICATION_JSON));
    StringResponse UNAUTHORIZED = new StringResponse(HttpResponseStatus.UNAUTHORIZED, new Content<>("",
            ContentType.APPLICATION_JSON));
    StringResponse FORBIDDEN = new StringResponse(HttpResponseStatus.FORBIDDEN, new Content<>("",
            ContentType.APPLICATION_JSON));
    StringResponse NOT_FOUND = new StringResponse(HttpResponseStatus.NOT_FOUND, new Content<>("",
            ContentType.APPLICATION_JSON));
    StringResponse INTERNAL_SERVER_ERROR = new StringResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR,
            new Content<>("", ContentType.APPLICATION_JSON));
    StringResponse BAD_REQUEST = new StringResponse(HttpResponseStatus.BAD_REQUEST, new Content<>("",
            ContentType.APPLICATION_JSON));

    @Nullable
    CompletableFuture<Content<T>> getAsynchronousContent();

    Response<T> setAsynchronousContent(@Nullable CompletableFuture<Content<T>> content);

    @Nullable
    Content<T> getContent();

    @NotNull
    Response<String> thenAccept(@NotNull Consumer<? super Content<String>> action);

    @NotNull
    HttpResponseStatus getStatus();

    Response<T> setStatus(@NotNull HttpResponseStatus status);

    boolean isDone();

    boolean isCancelled();

    boolean cancel(boolean mayInterruptIfRunning);

}
