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
- Break the main task into separate steps: read from file,
  compute a hash for every string and put it to a specific topic based on hashcode, read from a topic, and push to local set to work out the number of unique elements.
- In the end, compute a sum
- Every topic reader contains its own set to remove synchronization problems.
- The hashcode plays a crucial role in defining the topic and sending similar IPs to the same topic.

Thank you for your attention, but I must warn you that I didn't have enough time to polish my code until the end. \
I think that "done is better than perfect". \
However, I left a place to criticize myself:
1. Messy code in the place where the read, shuffle, and inserter tasks are defined
2. A simple architecture decision to communicate between different parts of tasks through common memory. The event
   communication approach will decouple code between parts, now code contains a dependency on the value set in the
   previous step
3. Code uses a ready hash implementation
4. There is no one place to configure the application runtime behavior.

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
