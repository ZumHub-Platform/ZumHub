package com.application.client.authority;

import com.application.client.ClientManager;
import com.application.client.model.ClientProfile;
import com.application.database.Database;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientProfileAuthority {

    private final Database database;

    public ClientProfileAuthority(Database database) {
        this.database = database;
    }

    public ClientProfile createClientProfile(ObjectId id, String username) {
        if (isProfile(id)) {
            return null;
        }

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setIdentifier(id);
        clientProfile.setUsername(username);
        clientProfile.setEmail("test@test.cy");
        clientProfile.setAvatar(ClientManager.AVATAR);
        clientProfile.setRegistrationDate(0);
        clientProfile.setLastLoginDate(System.currentTimeMillis());

        database.getDatabase().save(clientProfile);
        return clientProfile;
    }

    public @Nullable ClientProfile searchClientProfile(@NotNull ObjectId id) {
        Query<ClientProfile> query = database.getDatabase().find(ClientProfile.class).filter(Filters.eq("_id", id));
        return query.first();
    }

    public boolean isProfile(ObjectId id) {
        if (id == null) {
            return false;
        }

        return searchClientProfile(id) != null;
    }
}
