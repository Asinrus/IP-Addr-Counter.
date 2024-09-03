package org.github.ipfilereader.set;


import java.util.BitSet;

import static org.github.ipfilereader.hash.HashFunctions.ipToLongBitwise;

//for storing Integer.MAX_VALUE ipv4 addreses
public class BloomFilterIpv4Filter {

    private final BitSet bitSet;
    private int size = 0;

    public BloomFilterIpv4Filter() {
        this.bitSet = new BitSet(1 << 30);
    }

    public boolean put(String ip) {
        if (contains(ip)) {
            return false;
        }
        putInternal(ip);
        size++;
        return true;
    }

    public boolean contains(String ip) {
        return mightContain(ip);
    }

    private void putInternal(String ip) {
        long hash = ipToLongBitwise(ip);
        int pos = Long.valueOf(hash % bitSet.size()).intValue();
        bitSet.set(pos, true);
    }

    public boolean mightContain(String ip) {
        long hash = ipToLongBitwise(ip);
        int pos = Long.valueOf(hash % bitSet.size()).intValue();
        return bitSet.get(pos);
    }

    public int size() {
        return size;
    }
}

