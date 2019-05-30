## 死锁编码以及定位分析

- 产生死锁的原因

  - 死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种相互等待的现象，如果无外力的干涉那它们都将无法推进下去，如果系统的资源充足，进程的资源请求都能够得到满足，死锁出现的可能性就很低，否则就会因争夺有限的资源而陷入死锁。

- 代码

```
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        DeadLockDemo deadLockDemo = new DeadLockDemo();
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(() -> deadLockDemo.method(lockA, lockB));
        executor.execute(() -> deadLockDemo.method(lockB, lockA));

    }

    public void method(String lock1, String lock2) {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + "--获取到：" + lock1 + "; 尝试获取：" + lock2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("获取到两把锁!");
            }
        }
    }
}
```

解决

- jps -l 命令查定位进程号

```
28519 org.jetbrains.jps.cmdline.Launcher
32376 com.intellij.idea.Main
28521 com.cuzz.thread.DeadLockDemo
27836 org.jetbrains.kotlin.daemon.KotlinCompileDaemon
28591 sun.tools.jps.Jps
```

jstack 28521 找到死锁查看

```
2019-05-07 00:04:15
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.191-b12 mixed mode):

"Attach Listener" #13 daemon prio=9 os_prio=0 tid=0x00007f7acc001000 nid=0x702a waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
// ...
Found one Java-level deadlock:
=============================
"pool-1-thread-2":
  waiting to lock monitor 0x00007f7ad4006478 (object 0x00000000d71f60b0, a java.lang.String),
  which is held by "pool-1-thread-1"
"pool-1-thread-1":
  waiting to lock monitor 0x00007f7ad4003be8 (object 0x00000000d71f60e8, a java.lang.String),
  which is held by "pool-1-thread-2"

Java stack information for the threads listed above:
===================================================
"pool-1-thread-2":
        at com.cuzz.thread.DeadLockDemo.method(DeadLockDemo.java:34)
        - waiting to lock <0x00000000d71f60b0> (a java.lang.String)
        - locked <0x00000000d71f60e8> (a java.lang.String)
        at com.cuzz.thread.DeadLockDemo.lambda$main$1(DeadLockDemo.java:21)
        at com.cuzz.thread.DeadLockDemo$$Lambda$2/2074407503.run(Unknown Source)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)
"pool-1-thread-1":
        at com.cuzz.thread.DeadLockDemo.method(DeadLockDemo.java:34)
        - waiting to lock <0x00000000d71f60e8> (a java.lang.String)
        - locked <0x00000000d71f60b0> (a java.lang.String)
        at com.cuzz.thread.DeadLockDemo.lambda$main$0(DeadLockDemo.java:20)
        at com.cuzz.thread.DeadLockDemo$$Lambda$1/558638686.run(Unknown Source)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

Found 1 deadlock.
```

最后发现一个死锁。