package com.application;

import com.application.client.ClientManager;
import com.application.client.response.ClientAuthorizationMapping;
import com.application.client.response.ClientProfileMapping;
import com.application.client.response.ClientRegisterMapping;
import com.application.database.DatabaseManager;
import com.server.Environment;
import com.server.HimariServer;
import com.server.mapping.MappingService;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Initializer {

    private static Logger logger = LoggerFactory.getLogger(Initializer.class);
    private static HimariServer defaultServer;

    public static void main(String[] args) {
        logger.info("Starting application...");

        defaultServer = new HimariServer(new Environment());

        if (args.length > 0) {
            if (args[0].equals("-config")) {
                try {
                    File file = new File(args[1]);
                    if (file.exists()) {
                        logger.info("Loading configuration file: " + file.getAbsolutePath());
                        FileInputStream configurationStream = new FileInputStream(args[1]);
                        defaultServer.getEnvironment().initialize(configurationStream);
                        logger.info("Configuration file loaded.");
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ClientManager.getInstance().initialize();

        defaultServer.getEnvironment().setDefaultHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        MappingService.getService().findMappings("com.application.client");
        
        //MappingService.getService().registerMapping("/login", new ClientAuthorizationMapping().addRequiredHeader("Authorization"));
        MappingService.getService().registerMapping("/client", new ClientProfileMapping().addRequiredHeader("Authorization").addRequiredParameter("username"));
        MappingService.getService().registerMapping("/register", new ClientRegisterMapping().addRequiredHeader("Authorization"));

        ClientManager.getInstance().getClientCredentialsAuthority().createClientCredentials("admin@test.com", "admin");

        logger.info("Application started.");
        defaultServer.start();
    }

    public static HimariServer getDefaultServer() {
        return defaultServer;
    }
}
