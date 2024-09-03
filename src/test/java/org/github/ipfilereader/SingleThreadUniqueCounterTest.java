package org.github.ipfilereader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SingleThreadUniqueCounterTest {

    @Test
    //@Disabled
    void realLoadTest() {
        UniqueCounter sut = new SingleThreadUniqueCounter();
        assertEquals(145406976, sut.numOfUniqueLinesIn(Path.of("./ips_500MB.txt").toFile()));
    }

}