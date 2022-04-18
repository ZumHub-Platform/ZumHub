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
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {

    private static ClientManager instance;

    //Temporary
    private Map<String, ClientCredentials> database = new HashMap<>();

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

        return new ClientToken(new String(Base64.getEncoder().encode(mail.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
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

    public ClientCredentials findClient(String clientName) {
        return database.get(clientName);
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
