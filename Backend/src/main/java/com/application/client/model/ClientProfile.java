package com.application.client.model;

import com.application.client.rank.Rank;
import com.application.client.serialization.ObjectIdSerialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity(value = "profiles", useDiscriminator = false)
public class ClientProfile implements Serializable {

    public static Gson PROFILE_GSON = new GsonBuilder().registerTypeAdapter(ObjectId.class,
            new ObjectIdSerialization()).create();

    @Id
    private ObjectId id;
    private String username;
    private String email;

    private long registrationDate;
    private long lastLoginDate;

    private String avatar;

    @Reference
    private List<Rank> ranks = new LinkedList<>();
    @Reference
    private List<ClientProfile> friendList = new LinkedList<>();
    @Reference
    private List<ClientProfile> friendRequestList = new LinkedList<>();

    public ObjectId getIdentifier() {
        return id;
    }

    public ClientProfile setIdentifier(ObjectId identifier) {
        this.id = identifier;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ClientProfile setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientProfile setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public ClientProfile setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public ClientProfile setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public ClientProfile setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public ClientProfile setRanks(List<Rank> ranks) {
        this.ranks = ranks;
        return this;
    }

    public ClientProfile addRank(Rank rank) {
        ranks.add(rank);
        return this;
    }

    public ClientProfile removeRank(Rank rank) {
        ranks.remove(rank);
        return this;
    }

    public List<ClientProfile> getFriendList() {
        return friendList;
    }

    public ClientProfile setFriendList(List<ClientProfile> friendList) {
        this.friendList = friendList;
        return this;
    }

    public ClientProfile addFriend(ClientProfile friend) {
        this.friendList.add(friend);
        return this;
    }

    public ClientProfile removeFriend(ClientProfile friend) {
        this.friendList.remove(friend);
        return this;
    }

    public boolean isFriend(ClientProfile friend) {
        return this.friendList.contains(friend);
    }

    public List<ClientProfile> getFriendRequestList() {
        return friendRequestList;
    }

    public ClientProfile setFriendRequestList(List<ClientProfile> friendRequestList) {
        this.friendRequestList = friendRequestList;
        return this;
    }

    public ClientProfile addFriendRequest(ClientProfile friend) {
        this.friendRequestList.add(friend);
        return this;
    }

    public ClientProfile removeFriendRequest(ClientProfile friend) {
        this.friendRequestList.remove(friend);
        return this;
    }

    public boolean hasFriendRequest(ClientProfile friend) {
        return this.friendRequestList.contains(friend);
    }

    public String toJson() {
        return PROFILE_GSON.toJson(this);
    }

    public static class StrippedClientProfile {

        private final ObjectId identifier;
        private final String username;
        private final String avatar;
        private final List<Rank> ranks;

        private final boolean online;

        public StrippedClientProfile(ClientProfile clientProfile) {
            this.identifier = clientProfile.getIdentifier();
            this.username = clientProfile.getUsername();
            this.avatar = clientProfile.getAvatar();
            this.ranks = clientProfile.getRanks();
            this.online = false;
        }

        public StrippedClientProfile(ObjectId identifier, String username, String avatar, List<Rank> ranks,
                                     boolean online) {
            this.identifier = identifier;
            this.username = username;
            this.avatar = avatar;
            this.ranks = ranks;
            this.online = online;
        }

        public ObjectId getIdentifier() {
            return identifier;
        }

        public String getUsername() {
            return username;
        }

        public String getAvatar() {
            return avatar;
        }

        public List<Rank> getRanks() {
            return ranks;
        }

        public boolean isOnline() {
            return online;
        }

        public String toJson() {
            return PROFILE_GSON.toJson(this);
        }
    }
}
