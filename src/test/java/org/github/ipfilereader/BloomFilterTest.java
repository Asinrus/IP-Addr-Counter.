package org.github.ipfilereader;

import org.github.ipfilereader.set.BloomFilter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    @Test
    @Disabled
    void successTest() {
        var filter = new BloomFilter(new BitSet(1 << 30), IpHash.HASH_FUNCTIONS);
        filter.put(ipHash("1.2.3.0"));
        filter.put(ipHash("1.2.3.5"));

        assertTrue(filter.contains(ipHash("1.2.3.0")));
        assertTrue(filter.contains(ipHash("1.2.3.5")));
        assertEquals(2, filter.size());
        assertFalse(filter.contains(ipHash("1.2.3.7")));
        assertFalse(filter.contains(ipHash("bla-bla")));
    }

    private IpHash ipHash(String ip) {
        return IpHash.form(ip);
    }

}