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

    private CompletableFuture<Content<String>> content;
    private HttpResponseStatus status = HttpResponseStatus.NO_CONTENT;

    public StringResponse() {
        this.content = CompletableFuture.completedFuture(null);
    }

    public StringResponse(@Nullable Content<String> content) {
        this(CompletableFuture.completedFuture(content));
    }

    public StringResponse(@Nullable CompletableFuture<Content<String>> content) {
        this.content = content;
    }

    public StringResponse(@NotNull HttpResponseStatus status, @Nullable CompletableFuture<Content<String>> content) {
        this.content = content;
        this.status = status;
    }

    public StringResponse(@NotNull HttpResponseStatus status, @Nullable Content<String> content) {
        this(status, CompletableFuture.completedFuture(content));
    }

    @NotNull
    @Override
    public CompletableFuture<Content<String>> getAsynchronousContent() {
        return this.content;
    }

    @Override
    public Response<String> setAsynchronousContent(@Nullable CompletableFuture<Content<String>> content) {
        this.content = content;
        return this;
    }

    @Nullable
    @Override
    public Content<String> getContent() {
        return this.content.getNow(null);
    }

    @NotNull
    @Override
    public Response<String> thenAccept(@NotNull Consumer<? super Content<String>> action) {
        this.content.thenAccept(action);
        return this;
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
