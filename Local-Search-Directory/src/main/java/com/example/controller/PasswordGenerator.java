package com.example.controller;

import java.security.SecureRandom;
import java.util.UUID;

public class PasswordGenerator {
	private static SecureRandom random = new SecureRandom();

    /** different dictionaries used */
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";
   
    public String generatePassword() {
    String result = "";
    int len = 10;
    String dic="ALPHA_CAPS+ALPHA";
    for (int i = 0; i < len; i++) {
        int index = random.nextInt(dic.length());
        result += dic.charAt(index);
    }
    return UUID.randomUUID().toString();
    }
}
