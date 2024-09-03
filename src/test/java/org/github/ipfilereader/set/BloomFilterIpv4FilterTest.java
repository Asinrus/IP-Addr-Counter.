package org.github.ipfilereader.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloomFilterIpv4FilterTest {

    @Test
    void successTest() {
        var filter = new BloomFilterIpv4Filter();
        filter.put("1.2.3.4");
        filter.put("1.2.3.5");

        assertTrue(filter.contains("1.2.3.4"));
        assertTrue(filter.contains("1.2.3.5"));
        assertEquals(2, filter.size());
        assertFalse(filter.contains("1.2.3.7"));
        assertFalse(filter.contains("bla-bla"));
    }

}