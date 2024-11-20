package com.dohdonglog.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;


public class PasswordEncoder {

    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);

    public String encrypt(String rawpassword) {
        return encoder.encode(rawpassword);
    }

    public boolean matches(String rawpassword, String encryptedPassword) {
        return encoder.matches(rawpassword, encryptedPassword);
    }
}
