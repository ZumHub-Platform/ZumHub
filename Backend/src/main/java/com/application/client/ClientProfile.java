package com.application.client;

import java.util.UUID;

public class ClientProfile {

    private UUID identifier;
    private String username;
    private String email;

    private long registrationDate;
    private long lastLoginDate;

    private String avatar;

    public UUID getIdentifier() {
        return identifier;
    }

    public ClientProfile setIdentifier(UUID identifier) {
        this.identifier = identifier;
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
