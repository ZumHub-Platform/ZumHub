package com.application.client.rank;

import com.application.Initializer;
import com.application.database.Database;
import com.application.lifecycle.Lifecycle;
import com.google.gson.Gson;
import com.utilities.DataStorage;
import dev.morphia.query.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RankLifecycle extends Lifecycle {

    private DataStorage storage = DataStorage.FILE;

    @Override
    public void start() {
        getLogger().info("Starting rank lifecycle...");

        storage = DataStorage.valueOf(Initializer.getDefaultServer()
                .getEnvironment().getPropertyOrDefault("environment.rank.storage", "FILE"));

        List<Rank> loadedRanks;

        if (storage.equals(DataStorage.DATABASE)) {
            loadedRanks = loadRanksFromDatabase();
        } else {
            loadedRanks = loadRanksFromFile();
        }

        if (loadedRanks == null) {
            loadedRanks = new ArrayList<>();
        }

        RankManager.getInstance().setRanks(loadedRanks);
        getLogger().info("Rank lifecycle started. {} ranks loaded.", loadedRanks.size());
    }

    private List<Rank> loadRanksFromDatabase() {
        getLogger().info("Initializing rank database...");
        Database databaseInstance = RankManager.getInstance().getConfigurationDatabase();

        Query<Rank> query = databaseInstance.getDatabase().find(Rank.class);
        return query.stream().collect(Collectors.toList());
    }

    private List<Rank> loadRanksFromFile() {
        getLogger().info("Loading ranks from file...");
        String path = Initializer.getDefaultServer().getEnvironment()
                .getPropertyOrDefault("environment.rank.path", "configuration/rank_configuration.json");

        File rankConfigurationFile = new File(path);
        if (!rankConfigurationFile.exists()) {
            getLogger().error("Rank configuration file not found!");
            return null;
        }

        Gson gson = new Gson();
        try {
            return Arrays.stream(gson.fromJson(new FileReader(rankConfigurationFile), Rank[].class)).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveRanks() {
        if (storage.equals(DataStorage.DATABASE)) {
            saveRanksToDatabase();
        } else {
            saveRanksToFile();
        }
    }

    public void saveRanksToDatabase() {
        getLogger().info("Saving ranks to database...");
        Database databaseInstance = RankManager.getInstance().getConfigurationDatabase();

        List<Rank> ranks = RankManager.getInstance().getRanks();
        ranks.forEach(rank -> databaseInstance.getDatabase().save(rank));
        getLogger().info("Ranks saved to database.");
    }

    public void saveRanksToFile() {
        getLogger().info("Saving ranks to file...");
        String path = Initializer.getDefaultServer().getEnvironment()
                .getPropertyOrDefault("environment.rank.path",
                        Initializer.getDefaultServer().getEnvironment().getHomeDirectory().getAbsolutePath() +
                                "/configuration/rank_configuration.json");

        File rankConfigurationFile = new File(path);
        if (!rankConfigurationFile.exists()) {
            try {
                rankConfigurationFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Gson gson = new Gson();
        try {
            gson.toJson(RankManager.getInstance().getRanks(), new FileWriter(rankConfigurationFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }
}
