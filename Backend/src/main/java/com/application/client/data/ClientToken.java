package com.application.client.data;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class ClientToken {

    private final ObjectId id;
    private final String token;

    public ClientToken(@NotNull ObjectId id, @NotNull String token) {
        this.id = id;
        this.token = token;
    }

    public @NotNull ObjectId getId() {
        return id;
    }

    public @NotNull String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ClientToken) {
            ClientToken other = (ClientToken) object;
            return this.token.equals(other.token);
        }
        return false;
    }
}
