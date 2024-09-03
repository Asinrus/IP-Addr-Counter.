package org.github.ipfilereader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class IpSetUniqueCounterBenchmark {

    //@Param({"./ips_500MB.txt", "./ips_2GB.txt", "./ips_5GB.txt", "./ips_40GB"})
    @Param({"./ips_500MB.txt", "./ips_2GB.txt", "./ips_5GB.txt"})
    private String filePath;
    private SingleThreadUniqueCounter singleThreadUniqueCounter;

    @Setup
    public void setup() {
        singleThreadUniqueCounter = new SingleThreadUniqueCounter();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1,  jvmArgs = {"-Xms700m", "-Xmx700m"})
    public void singleThreadUniqueCounter(Blackhole blackhole) {
        long num = singleThreadUniqueCounter.numOfUniqueLinesIn(Paths.get(filePath).toFile());
        blackhole.consume(num);
    }

}
