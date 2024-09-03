package org.github.ipfilereader.set;

import org.github.ipfilereader.IpHash;

import java.util.BitSet;
import java.util.List;
import java.util.function.Function;

public class BloomFilter {
    private final BitSet bitSet;
    private final List<Function<IpHash, Integer>> hashFunctions;
    private int size = 0;

    public BloomFilter(BitSet bitSet,
                       List<Function<IpHash, Integer>> hashFunctions) {
        this.bitSet = bitSet;
        this.hashFunctions = hashFunctions;
    }

    public boolean put(IpHash ip) {
        if (contains(ip)) {
            return false;
        }
        putInternal(ip);
        size++;
        return true;
    }

    public boolean contains(IpHash ip) {
        return mightContain(ip);
    }

    private void putInternal(IpHash ip) {
        for (Function<IpHash, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(ip);
            int pos = Math.abs(hash) % bitSet.size();
            bitSet.set(pos, true);
        }
    }

    public boolean mightContain(IpHash ip) {
        for (Function<IpHash, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(ip);
            int pos = Math.abs(hash) % bitSet.size();
            if (!bitSet.get(pos)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return size;
    }
}
