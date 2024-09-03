package org.github.ipfilereader.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunctions {

    static final MessageDigest digestFunction;
    static { // The digest method is reused between instances
        MessageDigest tmp;
        try {
            tmp = java.security.MessageDigest.getInstance("MD5");
            byte b = 0;
            tmp.update(b);
        } catch (NoSuchAlgorithmException e) {
            tmp = null;
        }
        digestFunction = tmp;
    }

    public static int md5(String s) {
        byte[] digest;
        int h = 0;
        digest = digestFunction.digest(s.getBytes());
        for (int i = 0; i < digest.length/4; i++) {
            for (int j = (i * 4); j < (i * 4) + 4; j++) {
                h <<= 8;
                h |= ((int) digest[j]) & 0xFF;
            }
        }
        return h;
    }

    public static long ipToLongBitwise(String ip) {
        char[] c = ip.toCharArray();
        byte[] b = { 0, 0, 0, 0 };
        for (int i = 0, j = 0; i < c.length;) {
            int d = (byte) (c[i] - '0');
            switch (c[i++]) {
                case '.':
                    ++j;//from  ww w  .  j av  a  2  s .  c o  m
                    break;
                default:
                    if ((d < 0) || (d > 9))
                        return 0;
                    if ((b[j] & 0xff) * 10 + d > 255)
                        return 0;
                    b[j] = (byte) (b[j] * 10 + d);
            }
        }
        return 0x00000000ffffffffl & (b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff));
    }
}
