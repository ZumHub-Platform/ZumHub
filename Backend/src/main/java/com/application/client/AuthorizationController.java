package com.application.client;

import com.application.client.data.ClientToken;
import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.server.mapping.annotation.Controller;
import com.server.mapping.annotation.Mapping;
import com.server.mapping.annotation.SecurityPolicy;
import com.server.request.Request;
import com.server.request.RequestMethod;
import com.server.response.Content;
import com.server.response.ContentType;
import com.server.response.StringResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Controller
@SecurityPolicy(allowedOrigins = "localhost:3000", allowCredentials = true)
public class AuthorizationController {

    @Mapping(path = "/login")
    @SecurityPolicy(allowedMethods = "GET", allowedHeaders = "Authorization", exposedHeaders = "Authorization")
    public StringResponse clientAuthorizationRequest(Request request) {
        String credentials =
                new String(Base64.getDecoder().decode(request.getHeader(HttpHeaderNames.AUTHORIZATION).split(
                        "Basic ")[1]), Charsets.UTF_8);
        String mail = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        StringResponse response = new StringResponse(HttpResponseStatus.REQUEST_TIMEOUT,
                (CompletableFuture<Content<String>>) null);

        response.setAsynchronousContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
            if (!ClientManager.getInstance().getClientCredentialsAuthority().isClient(mail)) {
                response.setStatus(HttpResponseStatus.NOT_FOUND);

                return new Content<>("", ContentType.APPLICATION_JSON);
            }

            try {
                response.setStatus(HttpResponseStatus.OK);

                ClientToken token =
                        ClientManager.getInstance().getClientCredentialsAuthority().authorizeClientCredentials(mail,
                                password);
                if (token == null) {
                    response.setStatus(HttpResponseStatus.UNAUTHORIZED);
                    return new Content<>(null, ContentType.APPLICATION_JSON);
                }

                Content<String> content = new Content<>(null, ContentType.APPLICATION_JSON);

                content.setHeader(HttpHeaderNames.AUTHORIZATION,
                        "Bearer " + Base64.getEncoder().encodeToString((token.getToken()).getBytes(Charsets.UTF_8)));

                return content;
            } catch (RuntimeException e) {
                response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);

                return new Content<>(e.getMessage(), ContentType.APPLICATION_JSON);
            }
        })));

        return response;
    }

    @Mapping(path = "/register", method = RequestMethod.HEAD)
    @SecurityPolicy(allowedMethods = "HEAD", allowedHeaders = "Authorization", exposedHeaders = "Authorization")
    public StringResponse clientRegisterRequest(Request request) {
        String credentials =
                new String(Base64.getDecoder().decode(request.getHeader(HttpHeaderNames.AUTHORIZATION).split(
                        "Basic ")[1]), Charsets.UTF_8);
        String mail = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        StringResponse response = new StringResponse();
        response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

        response.setAsynchronousContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
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
                e.printStackTrace();
                response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                return new Content<>(e.getMessage(), ContentType.APPLICATION_JSON);
            }
        })));

        return response;
    }
}
