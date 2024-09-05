package org.github.ipfilereader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderUniqueCounterTest {

    @TempDir
    Path tempDir;

    final UniqueCounter sut = new FileReaderUniqueCounter();

    @Test
    void readEmptyFile() throws IOException {
        Path emptyFilePath = tempDir.resolve("emptyFilePath.txt");
        Files.write(emptyFilePath, "".getBytes());
        assertEquals(0, sut.numOfUniqueLinesIn(emptyFilePath.toFile()));
    }

    @Test
    void readIps() throws IOException {
        Path fileWithIpsPath = tempDir.resolve("fileWithIps.txt");
        List<String> ips = List.of("1.2.3.5", "1.2.3.5", "1.2.3.6", "1.2.3.6");
        Files.write(fileWithIpsPath, ips);
        assertEquals(2, sut.numOfUniqueLinesIn(fileWithIpsPath.toFile()));
    }
}