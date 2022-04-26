package com.application.security;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.application.Initializer;

public class PasswordHandler {

    public static String hashPassword(String password) {
        return Hash.password(password.toCharArray())
                .pepper(Initializer.getDefaultServer()
                        .getEnvironment().getPropertyOrDefault("security.password.pepper", "pepper").toCharArray())
                .algorithm(Type.PBKDF2_SHA512).saltLength(32).create();
    }

    public static boolean checkPassword(String password, String hashedPassword) throws InvalidHashException {
        return Hash.password(password.toCharArray())
                .pepper(Initializer.getDefaultServer()
                        .getEnvironment().getPropertyOrDefault("security.password.pepper", "pepper").toCharArray())
                .algorithm(Type.PBKDF2_SHA512).saltLength(32).verify(hashedPassword);
    }
}
