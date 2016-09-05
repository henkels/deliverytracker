package br.com.deliverytracker.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class Base64Utils {

    private Base64Utils() {
    }

    public static String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    public static String encode(String s) {
        return Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
    }

}
