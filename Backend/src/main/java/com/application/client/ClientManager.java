package com.application.client;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class ClientManager {

    private static ClientManager instance;
    public static String AVATAR = "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";

    //Temporary
    private Map<String, ClientCredentials> database = new HashMap<>();
    //TODO: Replace with database client id
    private Map<String, String> onlineClients = new HashMap<>();

    public ClientManager() {

    }

    public ClientToken authenticateClient(String mail, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ClientCredentials clientAuthorization = database.get(mail);
        if (clientAuthorization == null) {
            return null;
        }


        ClientCredentials authorization = new ClientCredentials(mail, password.getBytes(StandardCharsets.UTF_8));

        if (!verifyClient(authorization, clientAuthorization)) {
            return null;
        }

        //TODO: Generate token with auth0
        ClientToken token = new ClientToken(UUID.randomUUID().toString());
        onlineClients.put(token.getToken(), mail);

        return token;
    }

    public boolean verifyClient(ClientCredentials auth, ClientCredentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash = encryptPassword(new String(auth.getPassword(), CharsetUtil.UTF_8));
        return Arrays.equals(hash, credentials.getPassword());
    }

    public byte[] encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        //random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return factory.generateSecret(spec).getEncoded();
    }

    public ClientCredentials findOfflineClient(String clientName) {
        return database.get(clientName);
    }

    public ClientProfile getClientProfile(ClientToken token) {
        if (onlineClients.containsKey(token.getToken())) {
            ClientProfile profile = new ClientProfile();
            profile.setUsername("Test");
            profile.setEmail("test@admin.com");
            profile.setAvatar(AVATAR);
            profile.setRegistrationDate(0);
            profile.setLastLoginDate(System.currentTimeMillis());
            profile.setIdentifier(UUID.randomUUID());
            return profile;
        }

        return null;
    }

    public void addClient(String mail, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        database.put(mail, new ClientCredentials(mail, encryptPassword(password)));
    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }
}
