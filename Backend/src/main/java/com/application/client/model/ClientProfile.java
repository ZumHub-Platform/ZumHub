package com.application.client.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

@Entity("profiles")
public class ClientProfile {

    @Id
    private ObjectId id;
    private String username;
    private String email;

    private long registrationDate;
    private long lastLoginDate;

    private String avatar;
    @Reference
    private List<ClientProfile> friendList = new LinkedList<>();

    public ObjectId getIdentifier() {
        return id;
    }

    public ClientProfile setIdentifier(ObjectId identifier) {
        this.id = identifier;
        return this;
    }

    public List<ClientProfile> getFriendList() {
        return friendList;
    }

    public ClientProfile setFriendList(List<ClientProfile> friendList) {
        this.friendList = friendList;
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
}
