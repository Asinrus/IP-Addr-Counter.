package org.github.ipfilereader;

import org.github.ipfilereader.exception.InterruptedReadOperation;
import org.github.ipfilereader.set.AllIpAddressesSet;
import org.github.ipfilereader.set.BloomFilter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.github.ipfilereader.IpHash.HASH_FUNCTIONS;

public class SingleThreadUniqueCounter implements UniqueCounter {

    @Override
    public long numOfUniqueLinesIn(File file) {
        AllIpAddressesSet allIpAddressesSet = new AllIpAddressesSet();

        try (FileChannel fc = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
            var chunkIterator = new ChunkIterator(fc);
            var ipProccessor = new IpProcessor(allIpAddressesSet::add);
            while (chunkIterator.hasNext()) {
                ipProccessor.process(chunkIterator.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allIpAddressesSet.size();
    }
}

