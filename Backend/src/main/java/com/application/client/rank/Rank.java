package com.application.client.rank;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.List;

@Entity(value = "rank_configuration", useDiscriminator = false)
public class Rank {

    @Id
    private final String id;
    private final String displayName;

    private String description;

    private int score;
    private List<String> permissions;

    public Rank(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Rank setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Rank setScore(int score) {
        this.score = score;
        return this;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public Rank setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Rank addPermission(String permission) {
        this.permissions.add(permission);
        return this;
    }
}
