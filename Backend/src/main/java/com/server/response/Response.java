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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Response<T> {

    public static Response<String> EMPTY_RESPONSE = new Response<>(new Content<>("", ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.BAD_REQUEST);
    public static Response<String> OK_RESPONSE = new Response<>(new Content<>("", ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.OK);
    public static Response<String> UNAUTHORIZED = new Response<>(new Content<>("", ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.UNAUTHORIZED);
    public static Response<String> FORBIDDEN = new Response<>(new Content<>("", ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.FORBIDDEN);
    public static Response<String> NOT_FOUND = new Response<>(new Content<>("", ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.NOT_FOUND);
    public static Response<String> INTERNAL_SERVER_ERROR = new Response<>(new Content<>("",
            ContentType.APPLICATION_JSON)).
            setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);

    private CompletableFuture<Content<T>> content;
    private HttpResponseStatus status = HttpResponseStatus.NO_CONTENT;

    public Response() {
        this.content = CompletableFuture.completedFuture(null);
    }

    public Response(@NotNull CompletableFuture<Content<T>> content) {
        this.content = content;
    }

    public Response(@NotNull Content<T> content) {
        this.content = CompletableFuture.completedFuture(content);
    }

    public Content<T> getContent() {
        try {
            return content.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response<T> setContent(@NotNull Content<T> content) {
        this.content = CompletableFuture.completedFuture(content);
        return this;
    }

    public CompletableFuture<Content<T>> getAsyncContent() {
        return content;
    }

    public Response<T> setAsyncContent(@NotNull CompletableFuture<Content<T>> content) {
        this.content = content;
        return this;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public Response<T> setStatus(HttpResponseStatus status) {
        this.status = status;
        return this;
    }

    public boolean isDone() {
        return content.isDone();
    }

    public boolean isCancelled() {
        return content.isCancelled();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return content.cancel(mayInterruptIfRunning);
    }
}
