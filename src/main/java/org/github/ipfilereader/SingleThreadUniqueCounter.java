package org.github.ipfilereader;

import org.github.ipfilereader.exception.InterruptedReadOperation;
import org.github.ipfilereader.set.BloomFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.github.ipfilereader.IpHash.HASH_FUNCTIONS;

public class SingleThreadUniqueCounter implements UniqueCounter {

    @Override
    public long numOfUniqueLinesIn(File file) {
        int size = 4;
        List<BloomFilter> filters = IntStream.range(0, size)
                .mapToObj(i -> new BloomFilter(new BitSet(1 << 30),HASH_FUNCTIONS))
                .toList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                insert(line, filters);
            }
        } catch (IOException e) {
            throw new InterruptedReadOperation(e);
        }
        return filters.parallelStream()
                .map(filter -> (long) filter.size())
                .reduce(Long::sum)
                .orElse(0L);
    }

    private void insert(String line, List<BloomFilter> filters) {
        IpHash ipHash = IpHash.form(line);
        int idx = Math.abs(line.hashCode()) % filters.size();
        filters.get(idx).put(ipHash);
    }
}

