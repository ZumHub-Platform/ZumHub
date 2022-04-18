package com.application.client;

public class ClientCredentials {

    private final String mail;
    private final byte[] password;

    public ClientCredentials(String mail, byte[] password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public byte[] getPassword() {
        return password;
    }
}
