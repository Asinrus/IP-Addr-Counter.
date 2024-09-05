package org.github.ipfilereader;

import org.github.ipfilereader.exception.InterruptedOperation;
import org.github.ipfilereader.set.AllIpAddressesSet;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileReaderUniqueCounter implements UniqueCounter {

    @Override
    public long numOfUniqueLinesIn(File file) {
        int workers = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(workers);
        AllIpAddressesSet allIpAddressesSet = new AllIpAddressesSet();

        IpProcessor ipProcessor = new IpProcessor(allIpAddressesSet::add);
        try (FileChannel fc = (FileChannel) Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {
            var chunkIterator = new IpByteBufferIterator(fc);
            for(int i = 0; i < workers; i++) {
                executorService.submit(() ->  {
                    for(;;) {
                        ByteBuffer bb = null;
                        synchronized (chunkIterator) {
                            if (!chunkIterator.hasNext()) {
                                break;
                            }
                            bb = chunkIterator.next();
                        }
                        ipProcessor.process(bb);
                    }
                    return 0;
                });
            }

            executorService.shutdown();
            if(!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                throw new InterruptedOperation("Operations are not finished in 10 minutes");
            }

        } catch (IOException | InterruptedException e) {
            throw new InterruptedOperation(e);
        }
        return allIpAddressesSet.size();
    }
}

