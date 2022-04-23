package com.application.client.data;

public class Credentials {

    private final String mail;
    private final byte[] password;

    public Credentials(String mail, byte[] password) {
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
