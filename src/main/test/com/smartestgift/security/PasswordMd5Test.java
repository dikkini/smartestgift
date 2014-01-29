package com.smartestgift.security;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static junit.framework.Assert.assertEquals;

/**
 * Created by dikkini on 29.01.14.
 */
public class PasswordMd5Test {
    @Test
    public void testPasswordMd5Encoder() {
        PasswordEncoder encoder = new PasswordMd5Encoder();
        String hashedPass = encoder.encode("koala");
        assertEquals("a564de63c2d0da68cf47586ee05984d7", hashedPass);
    }
}
