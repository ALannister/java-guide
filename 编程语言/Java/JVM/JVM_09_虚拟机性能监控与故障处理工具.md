# 虚拟机性能监控与故障处理工具

### jps

- 作用：JVM Process Status Tool，显示指定系统内所有的HotSpot虚拟机进程

- 效果
```
[lxf@hadoop101 ~]$ jps -l
1377 Test
1387 sun.tools.jps.Jps
```

### jstat

- 作用：JVM Statistics Monitoring Tool，用于收集HotSpot虚拟机各方面的运行数据，主要是内存和GC信息

- 命令：`jstat [-命令选项] [vmid] [间隔时间/毫秒] [查询次数]`

- 类加载统计

  ```
  [lxf@hadoop101 ~]$ jstat -class 1377 1000 3
  Loaded  Bytes  Unloaded  Bytes     Time   
   384   802.3        0     0.0       0.04
   384   802.3        0     0.0       0.04
   384   802.3        0     0.0       0.04
  ```

  | Loaded          | Bytes          | Unloaded   | Bytes          | Time |
  | --------------- | -------------- | ---------- | -------------- | ---- |
  | 加载class的数量 | 所占用空间大小 | 未加载数量 | 未加载占用空间 | 时间 |

- 编译统计

  ```
  [lxf@hadoop101 ~]$ jstat -compiler 1377
  Compiled Failed Invalid   Time   FailedType FailedMethod
        95      0       0     0.24          0   
  ```

  | Compiled | Failed   | Invalid    | Time | FailedType | FailedMethod |
  | -------- | -------- | ---------- | ---- | ---------- | ------------ |
  | 编译数量 | 失败数量 | 不可用数量 | 时间 | 失败类型   | 失败的方法   |

- **垃圾回收统计**

  ```
  [lxf@hadoop101 ~]$ jstat -gcutil 1377
    S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
    0.01   0.00  36.00   1.25  51.41  51.58    922    0.219     0    0.000    0.219
  ```

  | S0                  | S1                  | E                | O              | M                | CCS          | YGC                | YGCT                    | FGC                | FGCT                   | GCT                |
  | ------------------- | ------------------- | ---------------- | -------------- | ---------------- | ------------ | ------------------ | ----------------------- | ------------------ | ---------------------- | ------------------ |
  | 幸存1区当前使用比例 | 幸存2区当前使用比例 | 伊甸园区使用比例 | 老年代使用比例 | 元数据区使用比例 | 压缩使用比例 | 年轻代垃圾回收次数 | n年轻代垃圾回收消耗时间 | 老年代垃圾回收次数 | 老年代垃圾回收消耗时间 | 垃圾回收消耗总时间 |

  

- **堆内存统计**

  ```
  [lxf@hadoop101 ~]$ jstat -gccapacity 1377
   NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC 
   10240.0 159040.0  10304.0 1024.0 1024.0   8256.0    20480.0   318144.0    20480.0    20480.0      0.0 1056768.0   4864.0      0.0 1048576.0    512.0   1278     0
  ```

  | NGCMN          | NGCMX          | NGC            | S0C                | S1C                | EC                 | OGCMN              | OGCMX          | OGC            |
  | -------------- | -------------- | -------------- | ------------------ | ------------------ | ------------------ | ------------------ | -------------- | -------------- |
  | 新生代最小容量 | 新生代最大容量 | 当前新生代容量 | 第一个幸存区大小   | 第二个幸存区的大小 | 伊甸园区的大小     | 老年代最小容量     | 老年代最大容量 | 当前老年代容量 |
  | OC             | MCMN           | MCMX           | MC                 | CCSMN              | CCSMX              | CCSC               | YGC            | FGC            |
  | 当前老年代大小 | 最小元数据容量 | 最大元数据容量 | 当前元数据空间大小 | 最小压缩类空间大小 | 最大压缩类空间大小 | 当前压缩类空间大小 | 年轻代GC次数   | 老年代GC次数   |

### jinfo

- 作用：Configuratoin Info for Java，显示虚拟机配置信息

- 查询一个参数的配置情况

  ```
  [lxf@hadoop101 ~]$ jinfo -flag MaxHeapSize 1377
  -XX:MaxHeapSize=488636416
  ```

- 查询进程的非默认参数

  ```
  [lxf@hadoop101 ~]$ jinfo -flags 1377
  Attaching to process ID 1377, please wait...
  Debugger attached successfully.
  Server compiler detected.
  JVM version is 25.201-b09
  Non-default VM flags: -XX:CICompilerCount=2 -XX:InitialHeapSize=31457280 -XX:MaxHeapSize=488636416 -XX:MaxNewSize=162856960 -XX:MinHeapDeltaBytes=196608 -XX:NewSize=10485760 -XX:OldSize=20971520 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps 
  Command line:
  ```


### jstack 

- 作用：Stack Trace for Java，显示虚拟机的线程快照，通过这些快照可以定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待

- 发现线程间死锁

参考：JUC_08_死锁编码以及定位分析.md

- 死循环导致CPU占用过高

参考：linuxCPU占用过高的定位分析思路.md

### jmap

- 作用：Memory Map for Java，生成虚拟机的内存转储快照（heapdump文件）

- jmap -heap 进程号 

  显示Java堆详细信息，如使用哪种垃圾收集器，参数配置，分代状况等。

```
[lxf@hadoop101 ~]$ jps -l
1571 sun.tools.jps.Jps
1561 Test
[lxf@hadoop101 ~]$ jmap -heap 1561
Attaching to process ID 1561, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.201-b09

using thread-local object allocation.
Mark Sweep Compact GC

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 488636416 (466.0MB)
   NewSize                  = 10485760 (10.0MB)
   MaxNewSize               = 162856960 (155.3125MB)
   OldSize                  = 20971520 (20.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
New Generation (Eden + 1 Survivor Space):
   capacity = 9502720 (9.0625MB)
   used     = 3212704 (3.063873291015625MB)
   free     = 6290016 (5.998626708984375MB)
   33.80825700431034% used
Eden Space:
   capacity = 8454144 (8.0625MB)
   used     = 3212672 (3.0638427734375MB)
   free     = 5241472 (4.9986572265625MB)
   38.00115067829457% used
From Space:
   capacity = 1048576 (1.0MB)
   used     = 32 (3.0517578125E-5MB)
   free     = 1048544 (0.999969482421875MB)
   0.0030517578125% used
To Space:
   capacity = 1048576 (1.0MB)
   used     = 0 (0.0MB)
   free     = 1048576 (1.0MB)
   0.0% used
tenured generation:
   capacity = 20971520 (20.0MB)
   used     = 261408 (0.249298095703125MB)
   free     = 20710112 (19.750701904296875MB)
   1.246490478515625% used

725 interned Strings occupying 48464 bytes.
```
- jmap -histo 进程号

  显示堆中对象统计信息，包括类、实例数量、合计容量，用于分析内存泄漏

  ```
  [lxf@hadoop101 ~]$ jps -l
  1571 sun.tools.jps.Jps
  1561 Test
  [lxf@hadoop101 ~]$ jmap -histo 1561
  
   num     #instances         #bytes  class name
  ----------------------------------------------
     1:          1674         111288  [C
     2:          1384          66432  java.nio.HeapCharBuffer
     3:           116          62464  [I
     4:           449          51280  java.lang.Class
     5:          1663          39912  java.lang.String
     6:             8          24984  [B
     7:           498          24832  [Ljava.lang.Object;
     8:            75           5400  java.lang.reflect.Field
     9:           256           4096  java.lang.Integer
    10:            89           3560  java.lang.ref.SoftReference
    11:           109           3488  java.util.Hashtable$Entry
    12:             6           2256  java.lang.Thread
    13:            59           2176  [Ljava.lang.String;
    14:            38           1824  sun.util.locale.LocaleObjectCache$CacheEntry
    15:            49           1568  java.util.concurrent.ConcurrentHashMap$Node
    16:            21           1344  java.net.URL
    17:             2           1064  [Ljava.lang.invoke.MethodHandle;
    18:             1           1040  [Ljava.lang.Integer;
    19:            26           1040  java.io.ObjectStreamField
    20:             6            992  [Ljava.util.Hashtable$Entry;
    21:            30            960  java.util.HashMap$Node
    22:            11            944  [Ljava.util.HashMap$Node;
    23:            19            760  sun.util.locale.BaseLocale$Key
    24:            17            680  java.util.LinkedHashMap$Entry
    25:            14            672  java.util.HashMap
    26:             8            640  [S
    27:            11            616  java.lang.Class$ReflectionData
    28:            19            608  java.util.Locale
    29:            19            608  sun.util.locale.BaseLocale
    30:            10            560  sun.misc.URLClassPath$JarLoader
    31:             5            528  [Ljava.util.concurrent.ConcurrentHashMap$Node;
    32:            30            480  java.lang.Object
    33:             6            480  java.lang.reflect.Constructor
    34:            19            456  java.util.Locale$LocaleKey
    35:             7            424  [Ljava.lang.reflect.Field;
    36:            17            408  java.io.ExpiringCache$Entry
    37:            10            400  java.security.AccessControlContext
    38:             1            384  java.lang.ref.Finalizer$FinalizerThread
    39:             6            384  java.nio.DirectByteBuffer
    40:             6            384  java.util.concurrent.ConcurrentHashMap
    41:             1            376  java.lang.ref.Reference$ReferenceHandler
    42:            11            352  java.io.File
    43:             6            336  java.nio.DirectLongBufferU
    44:            10            320  java.lang.OutOfMemoryError
    45:            10            288  [Ljava.io.ObjectStreamField;
    46:             3            240  [Ljava.util.WeakHashMap$Entry;
    47:            10            240  sun.misc.MetaIndex
    48:             7            224  java.lang.ref.ReferenceQueue
    49:             5            200  java.util.WeakHashMap$Entry
    50:             4            192  java.util.Hashtable
    51:             6            192  java.util.Vector
    52:             3            168  sun.nio.cs.UTF_8$Encoder
    53:             4            160  java.lang.ref.Finalizer
    54:             9            144  java.lang.ref.ReferenceQueue$Lock
    55:             6            144  java.util.ArrayList
    56:             3            144  java.util.WeakHashMap
    57:             6            144  sun.misc.PerfCounter
    58:             2            128  java.io.ExpiringCache$1
    59:             3            120  java.security.ProtectionDomain
    60:             3             96  java.io.FileDescriptor
    61:             2             96  java.lang.ThreadGroup
    62:             2             96  java.nio.HeapByteBuffer
    63:             3             96  java.security.CodeSource
    64:             2             96  java.util.Properties
    65:             3             96  java.util.Stack
    66:             1             96  sun.misc.Launcher$AppClassLoader
    67:             2             96  sun.misc.URLClassPath
    68:             2             96  sun.nio.cs.StreamEncoder
    69:             5             88  [Ljava.lang.Class;
    70:             1             88  java.lang.reflect.Method
    71:             1             88  sun.misc.Launcher$ExtClassLoader
    72:             1             80  [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;
    73:             2             80  java.io.BufferedWriter
    74:             2             80  java.io.ExpiringCache
    75:             3             72  [Ljava.lang.reflect.Constructor;
    76:             3             72  java.lang.RuntimePermission
    77:             3             72  java.util.Collections$SynchronizedSet
    78:             3             72  java.util.concurrent.atomic.AtomicLong
    79:             3             72  sun.misc.Signal
    80:             3             72  sun.reflect.NativeConstructorAccessorImpl
    81:             2             64  [Ljava.lang.Thread;
    82:             2             64  java.io.FileOutputStream
    83:             2             64  java.io.PrintStream
    84:             2             64  java.lang.ClassValue$Entry
    85:             2             64  java.lang.ThreadLocal$ThreadLocalMap$Entry
    86:             2             64  java.lang.VirtualMachineError
    87:             2             64  java.lang.ref.ReferenceQueue$Null
    88:             1             48  [J
    89:             3             48  [Ljava.security.Principal;
    90:             2             48  java.io.BufferedOutputStream
    91:             2             48  java.io.File$PathStatus
    92:             2             48  java.io.OutputStreamWriter
    93:             3             48  java.lang.ThreadLocal
    94:             2             48  java.nio.charset.CoderResult
    95:             3             48  java.nio.charset.CodingErrorAction
    96:             3             48  java.security.ProtectionDomain$Key
    97:             2             48  sun.misc.NativeSignalHandler
    98:             3             48  sun.reflect.DelegatingConstructorAccessorImpl
    99:             1             40  java.io.BufferedInputStream
   100:             1             40  java.lang.ClassLoader$NativeLibrary
   101:             1             40  sun.nio.cs.StandardCharsets$Aliases
   102:             1             40  sun.nio.cs.StandardCharsets$Cache
   103:             1             40  sun.nio.cs.StandardCharsets$Classes
   104:             1             40  sun.nio.cs.UTF_8$Decoder
   105:             1             32  [Ljava.lang.OutOfMemoryError;
   106:             2             32  [Ljava.lang.StackTraceElement;
   107:             1             32  [Ljava.lang.ThreadGroup;
   108:             1             32  java.io.FileInputStream
   109:             1             32  java.io.FilePermission
   110:             1             32  java.io.UnixFileSystem
   111:             1             32  java.lang.ArithmeticException
   112:             2             32  java.lang.Boolean
   113:             1             32  java.lang.NullPointerException
   114:             1             32  java.lang.StringCoding$StringDecoder
   115:             1             32  java.lang.StringCoding$StringEncoder
   116:             2             32  java.nio.ByteOrder
   117:             1             32  java.security.BasicPermissionCollection
   118:             1             32  java.security.Permissions
   119:             2             32  java.util.HashSet
   120:             2             32  java.util.concurrent.atomic.AtomicInteger
   121:             1             32  java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl
   122:             2             32  sun.net.www.protocol.jar.Handler
   123:             1             32  sun.nio.cs.StandardCharsets
   124:             1             24  [Ljava.io.File$PathStatus;
   125:             1             24  [Ljava.lang.ClassValue$Entry;
   126:             1             24  [Ljava.lang.reflect.Method;
   127:             1             24  [Lsun.launcher.LauncherHelper;
   128:             1             24  java.io.FilePermissionCollection
   129:             1             24  java.lang.ClassValue$Version
   130:             1             24  java.lang.StringBuilder
   131:             1             24  java.lang.ThreadLocal$ThreadLocalMap
   132:             1             24  java.lang.invoke.MethodHandleImpl$4
   133:             1             24  java.lang.reflect.ReflectPermission
   134:             1             24  java.util.BitSet
   135:             1             24  java.util.Collections$EmptyMap
   136:             1             24  java.util.Collections$SetFromMap
   137:             1             24  java.util.Collections$UnmodifiableRandomAccessList
   138:             1             24  java.util.Locale$Cache
   139:             1             24  sun.launcher.LauncherHelper
   140:             1             24  sun.misc.URLClassPath$FileLoader
   141:             1             24  sun.nio.cs.UTF_8
   142:             1             24  sun.util.locale.BaseLocale$Cache
   143:             1             16  [Ljava.lang.Throwable;
   144:             1             16  [Ljava.security.cert.Certificate;
   145:             1             16  java.io.FileDescriptor$1
   146:             1             16  java.lang.CharacterDataLatin1
   147:             1             16  java.lang.ClassValue$Identity
   148:             1             16  java.lang.Runtime
   149:             1             16  java.lang.String$CaseInsensitiveComparator
   150:             1             16  java.lang.System$2
   151:             1             16  java.lang.Terminator$1
   152:             1             16  java.lang.invoke.MemberName$Factory
   153:             1             16  java.lang.invoke.MethodHandleImpl$2
   154:             1             16  java.lang.invoke.MethodHandleImpl$3
   155:             1             16  java.lang.ref.Reference$1
   156:             1             16  java.lang.ref.Reference$Lock
   157:             1             16  java.lang.reflect.ReflectAccess
   158:             1             16  java.net.URLClassLoader$7
   159:             1             16  java.nio.Bits$1
   160:             1             16  java.nio.charset.CoderResult$1
   161:             1             16  java.nio.charset.CoderResult$2
   162:             1             16  java.security.ProtectionDomain$2
   163:             1             16  java.security.ProtectionDomain$JavaSecurityAccessImpl
   164:             1             16  java.util.Collections$EmptyList
   165:             1             16  java.util.Collections$EmptySet
   166:             1             16  java.util.Hashtable$EntrySet
   167:             1             16  java.util.WeakHashMap$KeySet
   168:             1             16  java.util.zip.ZipFile$1
   169:             1             16  sun.misc.Launcher
   170:             1             16  sun.misc.Launcher$Factory
   171:             1             16  sun.misc.Perf
   172:             1             16  sun.misc.Unsafe
   173:             1             16  sun.net.www.protocol.file.Handler
   174:             1             16  sun.reflect.ReflectionFactory
  Total          7075         431120
  ```

### jhat

- 作用：JVM Heap Dump Browser，用于分析heapdump文件，它会建立一个HTTP/HTML服务器，让用户可以在浏览器上查看分析结果

- 步骤：

  - 通过jmap生成某个java进程dump快照文件

    ```
    [lxf@hadoop101 ~]$ jps -l
    1571 sun.tools.jps.Jps
    1561 Test
    [lxf@hadoop101 ~]$ jmap -dump:live,format=b,file=heap.dmp 1561
    Dumping heap to /home/lxf/heap.dmp ...
    Heap dump file created
    ```

  - 通过jhat分析dump快照文件，Jhat启动一个允许堆中的对象在web浏览器中进行分析的web服务器

    ```
    [lxf@hadoop101 ~]$ jhat heap.dmp
    Reading from heap.dmp...
    Dump file created Mon Jun 03 23:00:15 CST 2019
    Snapshot read, resolving...
    Resolving 4305 objects...
    Chasing references, expect 0 dots
    Eliminating duplicate references
    Snapshot resolved.
    Started HTTP server on port 7000
    Server is ready.
    ```

  - 用户通过浏览器就可以看到分析结果

    ![jhat](assets/jhat.png)
    
  - 分析结果默认是以包为单位进行分组显示，分析内存泄漏问题主要会使用到其中的“Heap Histogram“（与jmp -histo功能一样）与OQL页签的功能，前者可以找到内存中总容量最大的对象，后者是标准的对象查询语言，使用类似SQL的语法对内存中的对象进行查询统计。