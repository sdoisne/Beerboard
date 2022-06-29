package fr.almeri.beerboard.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class UserController {
    public static String hashMD5withSalt(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(salt);

        byte[] bytes = md.digest(passwordToHash.getBytes());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)).substring(1);
        }

        generatedPassword = sb.toString();

        return generatedPassword;
    }

    public static byte[] getSalt() throws NoSuchFieldException, NoSuchFieldException, NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        byte[] salt = new byte[16];

        sr.nextBytes(salt);

        return salt;
    }
}
