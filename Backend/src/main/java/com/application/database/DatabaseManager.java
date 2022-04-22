package com.application.database;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private static DatabaseManager instance;

    private Map<String, Database> databaseMap = new HashMap<>();

    private DatabaseManager() {
    }

    public Database createDatabase(String name, String uri) {
        if (databaseMap.containsKey(name)) {
            return databaseMap.get(name);
        }

        try {
            return databaseMap.put(name, new Database(uri));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeDatabase(String name) {
        if (databaseMap.containsKey(name)) {
            databaseMap.get(name).disconnect();
            databaseMap.remove(name);
        }
    }

    public Database getDatabase(String name) {
        return databaseMap.get(name);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
}
