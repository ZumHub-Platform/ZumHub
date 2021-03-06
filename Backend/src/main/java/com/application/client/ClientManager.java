package com.application.client;

import com.application.client.authority.ClientCredentialsAuthority;
import com.application.client.authority.ClientProfileAuthority;
import com.application.client.data.ClientToken;
import com.application.database.Database;
import com.application.database.DatabaseManager;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {

    public static String AVATAR =
            "iVBORw0KGgoAAAANSUhEUgAAAaQAAAGkCAIAAADxLsZiAAAF6ElEQVR4nOzXwdGbMAAG0ThDFVRFRRxcEVVxVgk5pIM" +
                    "/sWR732tA3wxmLbYxxi+Ab" +
                    "/d79QCAGcQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5I2KaddD3vaWdNc5z76gm0eI9+zM0OSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5I2FYP4I1cz3v1hP/vOPfVE3gL82LnNwf/znv0Yz5jgQSxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsg4THGWL3hU13Pe/UEio5zXz3hI7nZAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCY8xxpyTruc95yDgsxznPuGUbcIZX2zOQ5rmK/+QPCP+8hkLJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckiB2QIHZAgtgBCWIHJIgdkCB2QILYAQliBySIHZAgdkCC2AEJYgckbKsH8EaOc189AV7FzQ5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkh4jDFWbwB4OTc7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsgQeyABLEDEsQOSBA7IEHsgASxAxLEDkgQOyBB7IAEsQMSxA5IEDsg4U8AAAD//xQVJkpDC/p3AAAAAElFTkSuQmCC";
    private static ClientManager instance;
    private final Logger logger = LoggerFactory.getLogger(ClientManager.class);
    //TODO: Replace with database client id
    private final Map<String, ObjectId> onlineClients = new HashMap<>();
    private ClientCredentialsAuthority clientCredentialsAuthority;
    private ClientProfileAuthority clientProfileAuthority;

    public ClientManager() {

    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void initialize() {
        logger.info("Initializing client manager...");

        Database client = DatabaseManager.getInstance().createDatabase("client");
        client.mapPackage("com.application.client.model");
        client.ensureIndexes();

        clientCredentialsAuthority = new ClientCredentialsAuthority(client);
        clientProfileAuthority = new ClientProfileAuthority(client);

        logger.info("Client manager initialized.");
    }

    public Map<String, ObjectId> getOnlineClients() {
        return onlineClients;
    }

    public void addOnlineClient(ClientToken token, ObjectId clientId) {
        onlineClients.put(token.getToken(), clientId);
    }

    public ClientCredentialsAuthority getClientCredentialsAuthority() {
        return clientCredentialsAuthority;
    }

    public ClientProfileAuthority getClientProfileAuthority() {
        return clientProfileAuthority;
    }
}
