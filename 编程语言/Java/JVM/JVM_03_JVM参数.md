## 你说你做过 JVM 调优和参数配置，请问如果盘点查看 JVM 系统默认值？

### JVM 的参数类型

- 标配参数

  - -version
  - -help 

- X 参数（了解）

  - -Xint：解释执行
  - -Xcomp：第一次使用就编译成本地代码
  - -Xmixed：混合模式

- XX 参数

  - Boolean 类型：-XX：+ / - 某个属性（+ 表示开启，- 表示关闭）

    - -XX:+PrintGCDetails：打印 GC 收集细节
    - -XX:-PrintGCDetails：不打印 GC 收集细节
    - -XX:+UseSerialGC：使用了串行收集器
    - -XX:-UseSerialGC：不使用串行收集器

  - KV 设置类型：-XX:key=value

    - -XX:MetaspaceSize=128m
    - -XX:MaxTenuringThreshold=15

## jinfo 举例，如何查看当前运行程序的配置

```
public class HelloGC {
    public static void main(String[] args) {
        System.out.println("hello GC...");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

我们可以使用 `jps -l` 命令，查出进程 id

```
1923 org.jetbrains.jps.cmdline.Launcher
1988 sun.tools.jps.Jps
1173 org.jetbrains.kotlin.daemon.KotlinCompileDaemon
32077 com.intellij.idea.Main
1933 com.cuzz.jvm.HelloGC
32382 org.jetbrains.idea.maven.server.RemoteMavenServer
```

在使用 `jinfo -flag PrintGCDetails 1933` 命令查看

```
-XX:-PrintGCDetails
```

可以看出默认是不打印 GC 收集细节

也可以使用`jinfo -flags 1933` 查看所有的参数

两个经典参数：-Xms 和 - Xmx（如 -Xms1024m）
- -Xms 等价于 -XX:InitialHeapSize
- -Xmx 等价于 -XX:MaxHeapSize

### 盘点家底查看 JVM 默认值

- 查看初始默认值：-XX:+PrintFlagsInitial

```
cuzz@cuzz-pc:~/Project/demo$ java -XX:+PrintFlagsInitial
[Global flags]
     intx ActiveProcessorCount                    = -1                 {product}
    uintx AdaptiveSizeDecrementScaleFactor        = 4                  {product}
    uintx AdaptiveSizeMajorGCDecayTimeScale       = 10                 {product}
    uintx AdaptiveSizePausePolicy                 = 0                  {product}
    uintx AdaptiveSizePolicyCollectionCostMargin  = 50                 {product}
    uintx AdaptiveSizePolicyInitializingSteps     = 20                 {product}
    uintx AdaptiveSizePolicyOutputInterval        = 0                  {product}
    uintx AdaptiveSizePolicyWeight                = 10                 {product}
    ...
```


- 查看修改更新：-XX:+PrintFlagsFinal

```
cuzz@cuzz-pc:~/Project/demo$ java -XX:+PrintCommandLineFlags -version
...
bool UsePSAdaptiveSurvivorSizePolicy     = true            {product}
bool UseParNewGC                         = false           {product}
bool UseParallelGC                      := true            {product}
bool UseParallelOldGC                    = true            {product}
bool UsePerfData                         = true            {product}
bool UsePopCountInstruction              = true            {product}
bool UseRDPCForConstantTableBase         = false           {C2 product}
...
```

= 与 := 的区别是，一个是默认，一个是人为改变或者 jvm 加载时改变的参数

- 打印命令行参数(可以看默认垃圾回收器)：-XX:+PrintCommandLineFlags

```
cuzz@cuzz-pc:~/Project/demo$ java -XX:+PrintCommandLineFlags -version
-XX:InitialHeapSize=30504896 -XX:MaxHeapSize=488078336 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops 
java version "1.8.0_201"
Java(TM) SE Runtime Environment (build 1.8.0_201-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.201-b09, mixed mode)
```

## 你平时工作用过的 JVM 常用的基本配置参数有哪些？

- -Xms
  - 初始内存大小（新生代+老年代），默认为物理内存 1/64
  - 等价于 -XX:InitialHeapSize

- -Xmx

  - 最大分配内存，默认为物理内存的 1/4
  - 等价于 -XX:MaxHeapSize
  - 一般而言，生产环境的jvm会把Xms和Xmx配置为相等。

- -Xss
  - 设置单个线程栈的大小，一般默认为 512-1024k
  - 等价于 -XX:ThreadStackSize
- -Xss参数的设置是需要非常小心的，太大，则可能会无法创建足够的线程，出现`Error occurred during initialization of VM java.lang.OutOfMemoryError: unable to create new native thread`，太小，则可能无法进行足够深层次的递归，出现`Exception in thread "main" java.lang.StackOverflowError`。
  
- -Xmn
  - 设置年轻代的大小
  - **整个JVM内存大小=年轻代大小 + 老年代大小 + 持久代大小**，持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
  - 这个参数则是对 -XX:NewSize、-XX:MaxNewSize两个参数的同时配置，也就是说如果通过-Xmn来配置新生代的内存大小，那么-XX:NewSize = -XX:MaxNewSize=-Xmn

- -XX:MetaspaceSize
  - 设置元空间大小
  - 在Java8中，永久代已经被移除，被一个称为元空间的区域所取代，元空间的本质和永久代类似，都是对 JVM 规范中的方法区的实现。
  - 元空间与永久代之间最大区别在于，永久代使用的是JVM的堆内存，而**元空间并不在虚拟机中，而是使用本机物理内存**，因此默认情况下，元空间的大小仅受本地内存限制）
  - 如果使用元空间，类的元数据放入native memory，字符串池和类的静态变量放入Java堆中，这样可以加载多少类的元数据就不再由MaxPermSize控制，而由系统的实际可用空间来控制
  - 元空间默认比较小，大约21M，我们可以调大一点，防止出现错误：java.lang.OutOfMemoryError:Metaspace

- -XX:+PrintGCDetails
  - 输出详细 GC 收集日志信息

    - 设置 JVM 参数为： -Xms10m -Xmx10m -XX:+PrintGCDetails

    - 代码

```
public class HelloGC {
    public static void main(String[] args) {
        byte[] bytes = new byte[20 * 1024 * 1024];
    }
}
```

打印结果

```
    [GC (Allocation Failure) [PSYoungGen: 1231K->448K(2560K)] 1231K->456K(9728K), 0.0015616 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    [GC (Allocation Failure) [PSYoungGen: 448K->384K(2560K)] 456K->392K(9728K), 0.0016999 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    [Full GC (Allocation Failure) [PSYoungGen: 384K->0K(2560K)] [ParOldGen: 8K->358K(7168K)] 392K->358K(9728K), [Metaspace: 3028K->3028K(1056768K)], 0.0066696 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
    [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 358K->358K(9728K), 0.0005321 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
    [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 358K->340K(7168K)] 358K->340K(9728K), [Metaspace: 3028K->3028K(1056768K)], 0.0051543 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
    Heap
     PSYoungGen      total 2560K, used 81K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
      eden space 2048K, 3% used [0x00000000ffd00000,0x00000000ffd14668,0x00000000fff00000)
      from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
      to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
     ParOldGen       total 7168K, used 340K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
      object space 7168K, 4% used [0x00000000ff600000,0x00000000ff655188,0x00000000ffd00000)
     Metaspace       used 3060K, capacity 4496K, committed 4864K, reserved 1056768K
      class space    used 336K, capacity 388K, committed 512K, reserved 1048576K
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    	at com.cuzz.jvm.HelloGC.main(HelloGC.java:12)
```

- -XX:SurvivorRatio
  - 设置新生代中 eden 和 S0（或S1） 的空间比例
  - 默认 -XX:SurvivorRatio=8，Eden : S0 : S1 = 8 : 1 : 1

- -XX:NewRatio
  - 设置置年轻代和老年代在堆结构的占比
  - 默认 -XX:NewRatio=2， 老年代：年轻代 = 2：1，年轻代占整个堆的 1/3

- -XX:MaxTenuringThreshold
  - 设置对象晋升老年代的年龄阈值

- 典型设置案例

```
-Xms128m -Xmx4096m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
```