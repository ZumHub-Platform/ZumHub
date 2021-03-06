package com.application.client.model;

import com.application.client.data.Credentials;
import com.google.gson.Gson;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Base64;

@Entity(value = "credentials", useDiscriminator = false)
public class ClientCredentials implements Serializable {

    public static Gson CREDENTIALS_GSON = new Gson();

    @Indexed(options = @IndexOptions(unique = true))
    private final String mail;
    private final String password;
    @Id
    private ObjectId id;

    public ClientCredentials(Credentials credentials) {
        this.mail = credentials.getMail();
        this.password = Base64.getEncoder().encodeToString(credentials.getPassword());
    }

    public ClientCredentials(ObjectId id, Credentials credentials) {
        this.id = id;
        this.mail = credentials.getMail();
        this.password = Base64.getEncoder().encodeToString(credentials.getPassword());
    }

    public ClientCredentials(ObjectId id, String mail, byte[] password) {
        this.id = id;
        this.mail = mail;
        this.password = Base64.getEncoder().encodeToString(password);
    }

    public ClientCredentials(ObjectId id, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public Credentials getAsCredentials() {
        return new Credentials(mail, Base64.getDecoder().decode(password));
    }

    public String toJson() {
        return CREDENTIALS_GSON.toJson(this);
    }
}
