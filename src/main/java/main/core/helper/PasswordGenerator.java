package main.core.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.domain.User;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by martijn on 10-5-2016.
 */
public class PasswordGenerator {
    public static String generateRandomPassword(int length) {
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        char[] pass = new char[length];
        for (int i = 0; i < length; i++) {
            pass[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(pass);
    }

    public static String encryptPassword(String username, String password) {
        String saltedPassword = saltPassword(username, password);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = md.digest(saltedPassword.getBytes());

            return Base64.encodeBase64String(data);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return password;
    }

    private static String saltPassword(String username, String password) {
        return "nB*R#*&^TG&B^*FGVwskjdfUV@"
                + username
                + password;
    }
}
