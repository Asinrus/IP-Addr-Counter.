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
- Read the file sequentially and put the result into 4 bloom filters to achieve the best accuracy.

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

My local results:
```bash
Benchmark                                                   (filePath)  Mode  Cnt       Score      Error  Units
IpSetUniqueCounterBenchmark.singleThreadUniqueCounter  ./ips_500MB.txt  avgt    5   13457.687 ±  548.704  ms/op
IpSetUniqueCounterBenchmark.singleThreadUniqueCounter    ./ips_2GB.txt  avgt    5   54638.901 ± 2018.370  ms/op
IpSetUniqueCounterBenchmark.singleThreadUniqueCounter    ./ips_5GB.txt  avgt    5  136165.394 ± 4525.458  ms/op
IpSetUniqueCounterBenchmark.singleThreadUniqueCounter   ./ips_40GB.txt  avgt    3 1175901.696 ± 51093.961  ms/op
```
