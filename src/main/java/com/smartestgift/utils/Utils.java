package com.smartestgift.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dikkini on 14.02.14.
 */
public class Utils {
    public static Locale getDefaultLocale(HttpServletRequest request) {
        return RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
    }

    public static boolean isEmail(String var) {
        Pattern p = Pattern.compile("^/.+@.+\\..+/i$");
        Matcher m = p.matcher(var);
        return m.matches();
    }

    public static String getHashFacebookAuth(String word) {
        PasswordEncoder encoder = new StandardPasswordEncoder();
        return encoder.encode(word);
    }

    public static boolean isUUID(String uuid) {
        try {
            return UUID.fromString(uuid) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
