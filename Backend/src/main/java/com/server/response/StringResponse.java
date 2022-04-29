/*
 * Copyright (c)  2022 Daniel Fiala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.server.response;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class StringResponse implements Response<String> {

    public static Response<String> EMPTY_RESPONSE = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.BAD_REQUEST);
    public static Response<String> OK_RESPONSE = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.OK);
    public static Response<String> UNAUTHORIZED = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.UNAUTHORIZED);
    public static Response<String> FORBIDDEN = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.FORBIDDEN);
    public static Response<String> NOT_FOUND = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.NOT_FOUND);
    public static Response<String> INTERNAL_SERVER_ERROR = new StringResponse(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);

    private final CompletableFuture<Content<String>> content;
    private HttpResponseStatus status = HttpResponseStatus.NO_CONTENT;

    public StringResponse() {
        this.content = CompletableFuture.completedFuture(null);
    }

    public StringResponse(@NotNull Content<String> content) {
        this(CompletableFuture.completedFuture(content));
    }

    public StringResponse(@NotNull CompletableFuture<Content<String>> content) {
        this.content = content;
    }

    @NotNull
    @Override
    public CompletableFuture<Content<String>> getAsynchronousContent() {
        return this.content;
    }

    @Nullable
    @Override
    public Content<String> getContent() {
        return this.content.getNow(null);
    }

    @NotNull
    @Override
    public Response<String> thenApply(@NotNull Consumer<? super Content<String>> action) {
        return this.thenApply(action);
    }

    @NotNull
    @Override
    public HttpResponseStatus getStatus() {
        return status;
    }

    @Override
    public Response<String> setStatus(@NotNull HttpResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean isDone() {
        return this.content.isDone();
    }

    @Override
    public boolean isCancelled() {
        return this.content.isCancelled();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.content.cancel(mayInterruptIfRunning);
    }
}
