package com.application.client.rank;

import com.application.database.Database;
import com.application.database.DatabaseManager;

import java.util.List;

public class RankManager {

    private static RankManager instance;

    private Database configurationDatabase;
    private RankLifecycle rankLifecycle;
    private List<Rank> ranks;

    private RankManager() {

    }

    public static RankManager getInstance() {
        if (instance == null) {
            instance = new RankManager();
        }
        return instance;
    }

    public void initialize() {
        configurationDatabase = DatabaseManager.getInstance().createDatabase("configuration");
        configurationDatabase.mapPackage("com.application.client.rank");
        configurationDatabase.ensureIndexes();

        rankLifecycle = new RankLifecycle();
        rankLifecycle.start();
    }

    public Rank createRank(String name, String username, String description, int score) {
        Rank rank = new Rank(name, username).setDescription(description).setScore(score);
        ranks.add(rank);
        
        rankLifecycle.saveRanks();

        return rank;
    }

    public void addRank(Rank rank) {
        ranks.add(rank);
        rankLifecycle.saveRanks();
    }

    public Database getConfigurationDatabase() {
        return configurationDatabase;
    }

    public RankLifecycle getRankLifecycle() {
        return rankLifecycle;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }
}
