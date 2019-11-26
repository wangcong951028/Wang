package com.kgc.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static String encoderPassword(String password){
       return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        String password ="123";
        String encoderPassword = encoderPassword(password);
        System.out.println(encoderPassword);//$2a$10$DpthhK21bU88YI77cGorGu/rMNlzXdnGBtREzqGTvmjTCrw6b0jsi
    }
}
