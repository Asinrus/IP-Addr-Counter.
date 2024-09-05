package org.github.ipfilereader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filePathStr = args[0];
        Path filePath = Path.of(filePathStr);
        var fileReaderUniqueCounter = new FileReaderUniqueCounter();
        long uniqueNumOfElementsInFile = fileReaderUniqueCounter.numOfUniqueLinesIn(filePath.toFile());

        log.debug("Unique numbers of elements in file {} is {}",filePathStr, uniqueNumOfElementsInFile);
    }
}