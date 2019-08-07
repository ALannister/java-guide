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
C:\Users\HINOC>jps -l
7096
6732 com.lannister.java.demo.concurrent.DeadLockDemo
8828 sun.tools.jps.Jps
```

jstack 28521 找到死锁查看

```
C:\Users\HINOC>jstack 6732
2019-06-04 11:13:49
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):

"DestroyJavaVM" #12 prio=5 os_prio=0 tid=0x000000000030f000 nid=0x1ff0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"222" #11 prio=5 os_prio=0 tid=0x000000001d4b0000 nid=0x1a98 waiting for monitor entry [0x000000001e2af000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.lannister.java.demo.concurrent.DeadLockDemo.method(DeadLockDemo.java:25)
        - waiting to lock <0x000000076acb1b80> (a java.lang.String)
        - locked <0x000000076acb1bb8> (a java.lang.String)
        at com.lannister.java.demo.concurrent.DeadLockDemo.lambda$1(DeadLockDemo.java:41)
        at com.lannister.java.demo.concurrent.DeadLockDemo$$Lambda$2/303563356.run(Unknown Source)
        at java.lang.Thread.run(Unknown Source)

"111" #10 prio=5 os_prio=0 tid=0x000000001d4ab800 nid=0x9c4 waiting for monitor entry [0x000000001e14f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.lannister.java.demo.concurrent.DeadLockDemo.method(DeadLockDemo.java:25)
        - waiting to lock <0x000000076acb1bb8> (a java.lang.String)
        - locked <0x000000076acb1b80> (a java.lang.String)
        at com.lannister.java.demo.concurrent.DeadLockDemo.lambda$0(DeadLockDemo.java:37)
        at com.lannister.java.demo.concurrent.DeadLockDemo$$Lambda$1/531885035.run(Unknown Source)
        at java.lang.Thread.run(Unknown Source)

"Service Thread" #9 daemon prio=9 os_prio=0 tid=0x000000001d1c2800 nid=0x187c runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread2" #8 daemon prio=9 os_prio=2 tid=0x000000001d12f800 nid=0x1578 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #7 daemon prio=9 os_prio=2 tid=0x000000001d12d800 nid=0x2190 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #6 daemon prio=9 os_prio=2 tid=0x000000001d126000 nid=0xb6c waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001d119800 nid=0x12d4 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001d123000 nid=0x228c runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000001bddf000 nid=0x14f4 in Object.wait() [0x000000001d10f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac08ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(Unknown Source)
        - locked <0x000000076ac08ed0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(Unknown Source)
        at java.lang.ref.Finalizer$FinalizerThread.run(Unknown Source)

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x000000001bd94000 nid=0x10f8 in Object.wait() [0x000000001d00f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076ac06bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Unknown Source)
        at java.lang.ref.Reference.tryHandlePending(Unknown Source)
        - locked <0x000000076ac06bf8> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Unknown Source)

"VM Thread" os_prio=2 tid=0x000000001bd8e800 nid=0x137c runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x000000000206d000 nid=0x1d10 runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000206e800 nid=0x16e8 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002070000 nid=0x1f3c runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002072000 nid=0x1a00 runnable

"VM Periodic Task Thread" os_prio=2 tid=0x000000001d1c7800 nid=0xea0 waiting on condition

JNI global references: 311


Found one Java-level deadlock:
=============================
"222":
  waiting to lock monitor 0x000000001bd9aa28 (object 0x000000076acb1b80, a java.lang.String),
  which is held by "111"
"111":
  waiting to lock monitor 0x000000001bd980e8 (object 0x000000076acb1bb8, a java.lang.String),
  which is held by "222"

Java stack information for the threads listed above:
===================================================
"222":
        at com.lannister.java.demo.concurrent.DeadLockDemo.method(DeadLockDemo.java:25)
        - waiting to lock <0x000000076acb1b80> (a java.lang.String)
        - locked <0x000000076acb1bb8> (a java.lang.String)
        at com.lannister.java.demo.concurrent.DeadLockDemo.lambda$1(DeadLockDemo.java:41)
        at com.lannister.java.demo.concurrent.DeadLockDemo$$Lambda$2/303563356.run(Unknown Source)
        at java.lang.Thread.run(Unknown Source)
"111":
        at com.lannister.java.demo.concurrent.DeadLockDemo.method(DeadLockDemo.java:25)
        - waiting to lock <0x000000076acb1bb8> (a java.lang.String)
        - locked <0x000000076acb1b80> (a java.lang.String)
        at com.lannister.java.demo.concurrent.DeadLockDemo.lambda$0(DeadLockDemo.java:37)
        at com.lannister.java.demo.concurrent.DeadLockDemo$$Lambda$1/531885035.run(Unknown Source)
        at java.lang.Thread.run(Unknown Source)

Found 1 deadlock.
```

最后发现一个死锁。