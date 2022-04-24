package com.application.client.response;

import com.application.client.ClientManager;
import com.application.client.ClientProfile;
import com.application.client.ClientToken;
import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.gson.Gson;
import com.server.mapping.Mapping;
import com.server.request.Request;
import com.server.request.RequestType;
import com.server.response.Content;
import com.server.response.ContentType;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class ClientProfileMapping extends Mapping<String> {

    @Override
    public Response<String> handle(Request request) {
        if (request.getRequestType().equals(RequestType.GET)) {
            String credentials = new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization").split("Bearer ")[1]), Charsets.UTF_8);
            ClientToken token = new ClientToken(credentials);

            StringResponse response = new StringResponse();
            response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

            response.setAsyncContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
                if (ClientManager.getInstance().getClientProfile(token) == null) {
                    response.setStatus(HttpResponseStatus.UNAUTHORIZED);

                    Content<String> content = new Content<>("", ContentType.APPLICATION_JSON);
                    content.setHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

                    return content;
                }

                response.setStatus(HttpResponseStatus.OK);

                ClientProfile profile = ClientManager.getInstance().getClientProfile(token);
                Content<String> content = new Content<>(new Gson().toJson(profile), ContentType.APPLICATION_JSON);
                content.setHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

                return content;
            })));

            return response;
        }

        return Response.EMPTY_RESPONSE;
    }
}