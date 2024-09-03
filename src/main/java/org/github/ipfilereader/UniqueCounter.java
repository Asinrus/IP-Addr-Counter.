package org.github.ipfilereader;

import java.io.File;

public interface UniqueCounter {

    long numOfUniqueLinesIn(File file);
}
