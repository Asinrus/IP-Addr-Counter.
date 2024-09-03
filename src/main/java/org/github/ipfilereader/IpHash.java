package org.github.ipfilereader;

import org.github.ipfilereader.hash.HashFunctions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public record IpHash(
        int hashcode,
        int md5hash,
        int ipInInt
        ) {

    public static IpHash form(String ip) {
        return new IpHash(
                ip.hashCode(),
                HashFunctions.md5(ip),
                Long.valueOf(HashFunctions.ipToLongBitwise(ip)).intValue());
    }

    public final static List<Function<IpHash, Integer>> HASH_FUNCTIONS = Arrays.asList(
            IpHash::md5hash,
            IpHash::ipInInt,
            IpHash::hashcode);
}
