## Task 
You have a simple text file with IPv4 addresses. One line is one address, line by line:

```
145.67.23.4
8.34.5.23
89.54.3.124
89.54.3.124
3.45.71.5
...
```

The file is unlimited in size and can occupy tens and hundreds of gigabytes.

You should calculate the number of __unique addresses__ in this file using as little memory and time as possible.
There is a "naive" algorithm for solving this problem (read line by line, put lines into HashSet).
It's better if your implementation is more complicated and faster than this naive algorithm.


### Main ideas:
1. Create your own collection to store all IP addresses in a single large integer array, with the ability to update it atomically without the need for locks.
2. Develop your own ByteBuffer iterator to read data in large batches, ensuring that it checks boundaries to retrieve complete lines.
3. Process each ByteBuffer by parsing bytes into an integer variable and storing the IP addresses in the collection.
4. To enhance processing speed, establish workers to handle ByteBuffers in separate threads and add the processed IP addresses to the collection.

### Local env 
MacBook M1 pro with 16gb 14inch

### Run 
Example of a run command
```bash
java -Xms700m -Xmx700m -jar build/libs/multithreadIPReader-1.0.jar ./ips_2GB.txt
```

### Test
run script to generate ips. It takes a while
```bash
python3 generate_ips.py 2
python3 generate_ips.py 5
```

And run a benchmark
```
## to run benchmark
./gradlew jmh
```

My local results with memory limitations in 700mb.
```bash
Benchmark                                                   (filePath)  Mode  Cnt       Score      Error  Units
FileReaderUniqueCounterBenchmark.fileReaderUniqueCounterB  ./ips_500MB.txt  avgt    5    1327.586 ±    88.087  ms/op
FileReaderUniqueCounterBenchmark.fileReaderUniqueCounterB    ./ips_2GB.txt  avgt    5    4996.530 ±   693.006  ms/op
FileReaderUniqueCounterBenchmark.fileReaderUniqueCounterB    ./ips_5GB.txt  avgt    5   19187.520 ±  2492.848  ms/op
FileReaderUniqueCounterBenchmark.fileReaderUniqueCounterB   ./ips_40GB.txt  avgt    5  149983.407 ± 14705.654  ms/op
```
