package ua.training.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtil {

    public static String encryptString(String text) {
        return DigestUtils.md5Hex(text);
    }
}
