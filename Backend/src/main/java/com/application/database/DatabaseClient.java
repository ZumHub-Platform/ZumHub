package com.application.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.net.UnknownHostException;
import java.sql.Connection;

public class DatabaseClient {

    private MongoClient client;

    public DatabaseClient() {

    }

    public DatabaseClient(String uri) {
        connect(uri);
    }

    public DatabaseClient connect() {
        client = MongoClients.create("mongodb://localhost:27017");
        return this;
    }

    public DatabaseClient connect(String url) {
        ConnectionString connectionString = new ConnectionString(url);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        client = MongoClients.create(settings);
        return this;
    }

    public boolean isConnected() {
        return client != null;
    }

    public DatabaseClient disconnect() {
        client.close();
        return this;
    }

    public MongoClient getClient() {
        return client;
    }
}
