package com.application;

import com.application.client.ClientManager;
import com.application.client.response.ClientAuthorizationMapping;
import com.server.HimariServer;
import com.server.mapping.MappingService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Initializer {

    public static void main(String[] args) {
        HimariServer server = new HimariServer(1048);
        MappingService.getService().registerMapping("/login", new ClientAuthorizationMapping());
        try {
            ClientManager.getInstance().addClient("admin@test.com", "admin");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        server.start();
    }
}