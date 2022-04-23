package com.application.database;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.DatastoreImpl;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;

public class Database {

    private final DatabaseClient client;
    private Datastore database;

    public Database(DatabaseClient client) {
        this.client = client;
        this.database = null;
    }

    public Database(DatabaseClient client, String databaseName) {
        this.client = client;

        initialize(databaseName);
    }

    public void initialize(String name) {
        database = Morphia.createDatastore(client.getClient(), name);
    }

    public void mapPackage(String packageName) {
        database.getMapper().mapPackage(packageName);
    }

    public void ensureIndexes() {
        database.ensureIndexes();
    }

    public DatabaseClient getClient() {
        return client;
    }

    public Datastore getDatabase() {
        return database;
    }
}
