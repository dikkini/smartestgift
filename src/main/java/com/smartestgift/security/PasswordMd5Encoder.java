package com.smartestgift.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dikkini on 29.01.14.
 * Email: dikkini@gmail.com
 */
public class PasswordMd5Encoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(charSequence.toString().getBytes(), 0, charSequence.length());
            String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
            if (hashedPass.length() < 32) {
                hashedPass = "0" + hashedPass;
                return hashedPass;
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
