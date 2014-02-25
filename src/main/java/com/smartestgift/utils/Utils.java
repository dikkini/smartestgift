package com.smartestgift.utils;

import com.smartestgift.security.PasswordMd5Encoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
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
        PasswordMd5Encoder encoder = new PasswordMd5Encoder();
        return encoder.encode(word);
    }
}
