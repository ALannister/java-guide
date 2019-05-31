JVM参数冲突会发生什么？



- -XX:+PrintGCDetails -Xms10m -Xmx10m -XX:SurvivorRatio=4 -XX:+PrintCommandLineFlags

```
-XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:SurvivorRatio=4 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
Hellol GC!
Heap
 PSYoungGen      total 2560K, used 818K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 39% used [0x00000000ffd00000,0x00000000ffdccba8,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
  to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 ParOldGen       total 7168K, used 0K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 0% used [0x00000000ff600000,0x00000000ff600000,0x00000000ffd00000)
 Metaspace       used 2576K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 282K, capacity 386K, committed 512K, reserved 1048576K

```

- -Xmn20m -XX:+PrintGCDetails -Xms10m -Xmx10m -XX:SurvivorRatio=4 -XX:+PrintCommandLineFlags

```
-XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=20971520 -XX:NewSize=20971520 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:SurvivorRatio=4 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
Hellol GC!
Heap
 PSYoungGen      total 8192K, used 1012K [0x00000000ff680000, 0x0000000100000000, 0x0000000100000000)
  eden space 6656K, 15% used [0x00000000ff680000,0x00000000ff77d3f8,0x00000000ffd00000)
  from space 1536K, 0% used [0x00000000ffe80000,0x00000000ffe80000,0x0000000100000000)
  to   space 1536K, 0% used [0x00000000ffd00000,0x00000000ffd00000,0x00000000ffe80000)
 ParOldGen       total 512K, used 0K [0x00000000ff600000, 0x00000000ff680000, 0x00000000ff680000)
  object space 512K, 0% used [0x00000000ff600000,0x00000000ff600000,0x00000000ff680000)
 Metaspace       used 2576K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 282K, capacity 386K, committed 512K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: MaxNewSize (20480k) is equal to or greater than the entire heap (10240k).  A new max generation size of 9728k will be used.
```

- 发生冲突后，JVM优先保证前面不冲突的参数，后面的参数尽量满足（注意：这里的顺序是指PrintCommandLineFlags打印出来的参数顺序）