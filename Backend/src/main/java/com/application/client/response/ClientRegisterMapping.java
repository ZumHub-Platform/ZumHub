package com.application.client.response;

import com.application.client.ClientManager;
import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.server.mapping.MappingHandler;
import com.server.request.Request;
import com.server.request.RequestType;
import com.server.response.Content;
import com.server.response.ContentType;
import com.server.response.StringResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class ClientRegisterMapping extends MappingHandler<String> {

    @Override
    public StringResponse<String> handle(Request request) {
        if (request.getRequestType().equals(RequestType.HEAD)) {
            String credentials =
                    new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization").split("Basic ")[1]), Charsets.UTF_8);
            String mail = credentials.split(":")[0];
            String password = credentials.split(":")[1];

            StringResponse response = new StringResponse();
            response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

            response.setAsyncContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
                try {
                    response.setStatus(HttpResponseStatus.OK);

                    if (ClientManager.getInstance().getClientCredentialsAuthority().createClientCredentials(mail,
                            password)) {
                        response.setStatus(HttpResponseStatus.OK);
                    } else {
                        response.setStatus(HttpResponseStatus.PRECONDITION_FAILED);
                    }

                    return new Content<>(null, ContentType.APPLICATION_JSON);
                } catch (RuntimeException e) {
                    response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                    return new Content<>(e.getMessage(), ContentType.APPLICATION_JSON);
                }
            })));

            return response;
        }

        return StringResponse.EMPTY_RESPONSE;
    }
}
