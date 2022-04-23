package com.application.client.authority;

import com.amdelamar.jhash.exception.InvalidHashException;
import com.application.client.ClientManager;
import com.application.client.data.ClientToken;
import com.application.client.data.Credentials;
import com.application.client.model.ClientCredentials;
import com.application.database.Database;
import com.application.security.PasswordHandler;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ClientCredentialsAuthority {

    private final Database database;

    public ClientCredentialsAuthority(Database database) {
        this.database = database;
    }

    @Nullable
    public ClientToken authorizeClientCredentials(@NotNull String mail, @NotNull String password) {
        ClientCredentials clientAuthorization = searchClientCredentials(mail);
        if (clientAuthorization == null) {
            throw new IllegalArgumentException("Client not found.");
        }

        Credentials authorization = new Credentials(mail, password.getBytes(StandardCharsets.UTF_8));

        try {
            if (!verifyCredentials(authorization, clientAuthorization.getAsCredentials())) {
                return null;
            }
        } catch (InvalidHashException e) {
            throw new RuntimeException(e);
        }

        //TODO: Generate token with auth0
        ClientToken token = new ClientToken(UUID.randomUUID().toString());
        ClientManager.getInstance().addOnlineClient(token, clientAuthorization.getId());

        return token;
    }

    public boolean createClientCredentials(@NotNull String mail, @NotNull String password) {
        if (isClient(mail)) {
            return false;
        }

        Credentials clientAuthorization = new Credentials(mail,
                PasswordHandler.hashPassword(password).getBytes(Charset.defaultCharset()));
        ClientCredentials clientCredentials = new ClientCredentials(clientAuthorization);

        database.getDatabase().save(clientCredentials);
        return true;
    }

    public @Nullable ClientCredentials searchClientCredentials(@NotNull String mail) {
        Query<ClientCredentials> query = database.getDatabase().find(ClientCredentials.class).filter(Filters.eq("mail", mail));
        return query.first();
    }

    public boolean isClient(@Nullable String mail) {
        if (mail == null) {
            return false;
        }

        return searchClientCredentials(mail) != null;
    }

    public boolean verifyCredentials(@Nullable Credentials auth, @Nullable Credentials credentials) throws InvalidHashException {
        if (auth == null || credentials == null) {
            return false;
        }

        return PasswordHandler.checkPassword(new String(auth.getPassword()), new String(credentials.getPassword()));
    }

    public @NotNull Database getDatabase() {
        return database;
    }
}
