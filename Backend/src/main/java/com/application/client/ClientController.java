package com.application.client;

import com.application.client.data.ClientToken;
import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.server.mapping.annotation.Controller;
import com.server.mapping.annotation.Mapping;
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

@Controller
public class ClientController {

    @Mapping(value = "/login", method = RequestType.HEAD)
    public Response<String> clientAuthorizationRequest(Request request) {
        String credentials = new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization").split("Basic ")[1]), Charsets.UTF_8);
        String mail = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        StringResponse response = new StringResponse();
        response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

        response.setAsyncContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
            if (!ClientManager.getInstance().getClientCredentialsAuthority().isClient(mail)) {
                response.setStatus(HttpResponseStatus.NOT_FOUND);

                return new Content<>("", ContentType.APPLICATION_JSON);
            }

            try {
                response.setStatus(HttpResponseStatus.OK);

                ClientToken token = ClientManager.getInstance().getClientCredentialsAuthority().authorizeClientCredentials(mail, password);
                Content<String> content = new Content<>(null, ContentType.APPLICATION_JSON);

                if (token == null) {
                    response.setStatus(HttpResponseStatus.UNAUTHORIZED);
                } else {
                    content.setHeader(HttpHeaderNames.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((token.getToken()).getBytes(Charsets.UTF_8)));
                }

                return content;
            } catch (RuntimeException e) {
                response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);

                return new Content<>(e.getMessage(), ContentType.APPLICATION_JSON);
            }
        })));

        return response;
    }
}
