package com.application.client.response;

import com.server.mapping.MappingHandler;
import com.server.request.Request;
import com.server.response.StringResponse;

public class ClientProfileMapping extends MappingHandler<String> {

    @Override
    public StringResponse<String> handle(Request request) {
        /*if (request.getRequestType().equals(RequestType.GET)) {
            String credentials = new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization")
            .split("Bearer ")[1]), Charsets.UTF_8);
            ClientToken token = new ClientToken(credentials);

            StringResponse response = new StringResponse();
            response.setStatus(HttpResponseStatus.REQUEST_TIMEOUT);

            response.setAsyncContent(CompletableFuture.supplyAsync(((Supplier<Content<String>>) () -> {
                if (ClientManager.getInstance().getClientProfile(token) == null) {
                    response.setStatus(HttpResponseStatus.UNAUTHORIZED);

                    return new Content<>("", ContentType.APPLICATION_JSON);
                }

                response.setStatus(HttpResponseStatus.OK);

                ClientProfile profile = ClientManager.getInstance().getClientProfile(token);

                return new Content<>(new Gson().toJson(profile), ContentType.APPLICATION_JSON);
            })));

            return response;
        }*/

        return StringResponse.EMPTY_RESPONSE;
    }
}
