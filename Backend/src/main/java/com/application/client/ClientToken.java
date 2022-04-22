package com.application.client;

public class ClientToken {

    private final String token;

    public ClientToken(String token) {
        this.token = token;
    }

    public String getToken() {
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
