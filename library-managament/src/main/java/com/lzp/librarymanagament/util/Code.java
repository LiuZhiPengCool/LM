package com.lzp.librarymanagament.util;

import org.springframework.util.DigestUtils;

public class Code {

    public static String CODE;

    public static int createCode() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int randow = (int) (Math.random() * 10);
            sb.append(randow);
        }
        CODE = sb.toString();
        return Integer.parseInt(String.valueOf(sb));
    }

    public static String MD5Lock(String pw) {
        String hsPwd = DigestUtils.md5DigestAsHex((pw).getBytes());
        return hsPwd;
    }

}
