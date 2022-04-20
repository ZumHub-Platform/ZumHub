package com.application.client.response;

import com.application.client.ClientManager;
import com.application.client.ClientToken;
import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.mapping.Mapping;
import com.server.request.Request;
import com.server.request.RequestType;
import com.server.response.Content;
import com.server.response.ContentType;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class ClientAuthorizationMapping extends Mapping<String> {

    @Override
    public Response<String> handle(Request request) {
        if (request.getRequestType().equals(RequestType.HEAD)) {
            String credentials = new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization").split("Basic ")[1]), Charsets.UTF_8);
            String mail = credentials.split(":")[0];
            String password = credentials.split(":")[1];

            StringResponse response = new StringResponse();
            response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

            response.setAsyncContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
                if (ClientManager.getInstance().findOfflineClient(mail) == null) {
                    response.setStatus(HttpResponseStatus.UNAUTHORIZED);

                    Content<String> content = new Content<>("", ContentType.APPLICATION_JSON);
                    content.setHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

                    return content;
                }

                try {
                    response.setStatus(HttpResponseStatus.OK);

                    ClientToken token = ClientManager.getInstance().authenticateClient(mail, password);
                    Content<String> content = new Content<>(null, ContentType.APPLICATION_JSON);
                    content.setHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                    content.setHeader(HttpHeaderNames.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((token.getToken()).getBytes(Charsets.UTF_8)));

                    return content;
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);

                    Content<String> content = new Content<>(e.getMessage(), ContentType.APPLICATION_JSON);
                    content.setHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

                    return content;
                }
            })));

            return response;
        }

        return Response.EMPTY_RESPONSE;
    }
}
