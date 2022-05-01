package com.application.client;

import com.application.client.model.ClientProfile;
import com.google.common.base.Charsets;
import com.server.mapping.annotation.Controller;
import com.server.mapping.annotation.Mapping;
import com.server.mapping.annotation.SecurityPolicy;
import com.server.request.Request;
import com.server.response.Content;
import com.server.response.ContentType;
import com.server.response.Response;
import com.server.response.StringResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.bson.types.ObjectId;

import java.util.Base64;

@Controller
@SecurityPolicy(allowedOrigins = "localhost:3000", allowCredentials = true)
public class ClientProfileController {

    @Mapping(path = "/profile", parameters = {"identifier"})
    @SecurityPolicy(allowedMethods = "GET", allowedHeaders = "Authorization")
    public StringResponse getProfile(Request request) {
        String identifier = request.getParameter("identifier");
        if (!ObjectId.isValid(identifier)) {
            return Response.BAD_REQUEST;
        }
        
        ClientProfile profile =
                ClientManager.getInstance().getClientProfileAuthority().searchClientProfile(new ObjectId(identifier));
        boolean authenticated = false;

        if (request.hasHeader("Authorization")) {
            String token = new String(Base64.getDecoder().decode(request.getHeaders().get("Authorization").split(
                    "Bearer ")[1]), Charsets.UTF_8);
            ObjectId authorizedId = ClientManager.getInstance().getOnlineClients().get(token);

            if (authorizedId != null) {
                ClientProfile requestingProfile =
                        ClientManager.getInstance().getClientProfileAuthority().searchClientProfile(authorizedId);
                if (requestingProfile != null && profile != null && requestingProfile.getIdentifier().equals(profile.getIdentifier())) {
                    authenticated = true;
                }
            }
            if (ClientManager.getInstance().getOnlineClients().containsKey(token) && ClientManager.getInstance().getOnlineClients().get(token).toHexString().equals(identifier)) {
                authenticated = true;
            }
        }

        if (profile == null) {
            return Response.NOT_FOUND;
        }

        if (!authenticated) {
            ClientProfile.StrippedClientProfile strippedProfile = new ClientProfile.StrippedClientProfile(profile);
            return new StringResponse(HttpResponseStatus.OK, new Content<>(strippedProfile.toJson(),
                    ContentType.APPLICATION_JSON));
        }

        return new StringResponse(HttpResponseStatus.OK, new Content<>(profile.toJson(), ContentType.APPLICATION_JSON));
    }
}
