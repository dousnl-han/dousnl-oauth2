package com.dousnl.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/8/26 18:26
 */
public class BPwdEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder ();


    public static String BCryptPassword(String password){
        return encoder.encode(password);
    }

    public static boolean matches (CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
