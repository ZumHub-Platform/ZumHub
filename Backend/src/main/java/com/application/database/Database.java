package com.application.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class Database {

    private MongoClient client;

    public Database() {

    }

    public Database(String uri) throws UnknownHostException {
        client = new MongoClient(new MongoClientURI(uri));
    }

    public Database connect() throws UnknownHostException {
        client = new MongoClient("localhost", 27017);
        return this;
    }

    public Database connect(String url) throws UnknownHostException {
        client = new MongoClient(new MongoClientURI(url));
        return this;
    }

    public boolean isConnected() {
        return client != null && !client.isLocked();
    }

    public Database disconnect() {
        client.close();
        return this;
    }

    public MongoClient getClient() {
        return client;
    }
}
