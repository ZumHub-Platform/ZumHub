package com.application.database;

import com.application.Initializer;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseClient defaultClient;

    private Map<String, Database> databaseMap = new HashMap<>();

    private DatabaseManager() throws UnknownHostException {
        this.defaultClient = new DatabaseClient();
        this.defaultClient.connect((String)
                Initializer.getDefaultServer().getEnvironment().getPropertyOrDefault("database.production.url",
                        "localhost:27017"));
    }

    public Database createDatabase(String name) {
        if (databaseMap.containsKey(name)) {
            return databaseMap.get(name);
        }

        Database database = new Database(defaultClient, name);
        databaseMap.put(name, database);
        return database;
    }

    public void closeDatabase(String name) {
        databaseMap.remove(name);
    }

    public Database getDatabase(String name) {
        return databaseMap.get(name);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            try {
                instance = new DatabaseManager();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
