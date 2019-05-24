# CAS

>[参考]：http://blog.cuzz.site/2019/04/16/Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B/

```
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(666);
        // 获取真实值，并替换为相应的值
        boolean b = atomicInteger.compareAndSet(666, 2019);
        System.out.println(b); // true
        boolean b1 = atomicInteger.compareAndSet(666, 2020);
        System.out.println(b1); // false
        atomicInteger.getAndIncrement();
    }
}
```

### CAS 底层原理？谈谈对 UnSafe 的理解？

#### getAndIncrement();

```
/**
 * Atomically increments by one the current value.
 *
 * @return the previous value
 */
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
```

引出一个问题：UnSafe 类是什么？

#### UnSafe 类

```
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            // 获取下面 value 的地址偏移量
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;
	// ...
}
```

- Unsafe 是 CAS 的核心类，由于 Java 方法无法直接访问底层系统，而需要通过本地（native）方法来访问， Unsafe  类相当一个后门，基于该类可以直接操作特定内存的数据。Unsafe 类存在于 sun.misc 包中，其内部方法操作可以像 C  指针一样直接操作内存，因为 Java 中 CAS 操作执行依赖于 Unsafe 类。 
- 变量 vauleOffset，表示该变量值在内存中的偏移量，因为 Unsafe 就是根据内存偏移量来获取数据的。
- 变量 value 用 volatile 修饰，保证了多线程之间的内存可见性。

#### CAS 是什么

- CAS    的全称 Compare-And-Swap，它是一条 CPU 并发。

- 它的功能是判断内存某一个位置的值是否为预期，如果是则更改这个值，这个过程就是原子的。

- CAS 并发原语体现在 JAVA 语言中就是 sun.misc.Unsafe 类中的各个方法。调用 UnSafe 类中的 CAS  方法，JVM 会帮我们实现出 CAS 汇编指令。这是一种完全依赖硬件的功能，通过它实现了原子操作。由于 CAS  是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成，用于完成某一个功能的过程，并且原语的执行必须是连续的，在执行的过程中不允许被中断，也就是说  CAS 是一条原子指令，不会造成所谓的数据不一致的问题。 

- 分析一下 getAndAddInt 这个方法

```
// unsafe.getAndAddInt
/**
   * Atomically adds the given value to the current value of a field
   * or array element within the given object <code>o</code>
   * at the given <code>offset</code>.
   *
   * @param o object/array to update the field/element in
   * @param offset field/element offset
   * @param delta the value to add
   * @return the previous value
   * @since 1.8
   */ 
   public final int getAndAddInt(Object o, long offset, int delta) {
	   	int v; 
   		do { 
   			v = getIntVolatile(o, offset); 
   		} while (!compareAndSwapInt(o, offset, v, v + delta)); 
   		return v; 
   }
```

### CAS 的缺点？

- 循环时间长开销很大
  - 如果 CAS 失败，会一直尝试，如果 CAS 长时间一直不成功，可能会给 CPU 带来很大的开销（比如线程数很多，每次比较都是失败，就会一直循环），所以希望是线程数比较小的场景。
- 只能保证一个共享变量的原子操作
  - 对于多个共享变量操作时，循环 CAS 就无法保证操作的原子性。
- 引出 ABA 问题

## 原子类 AtomicInteger 的 ABA 问题谈一谈？原子更新引用知道吗？

- 原子引用

```
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User cuzz = new User("cuzz", 18);
        User faker = new User("faker", 20);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(cuzz);
        System.out.println(atomicReference.compareAndSet(cuzz, faker)); // true
        System.out.println(atomicReference.get()); // User(userName=faker, age=20)
    }
}
```

ABA 问题是怎么产生的

```

/**
 * @program: learn-demo
 * @description: ABA
 * @author: cuzz
 * @create: 2019-04-21 23:31
 **/
public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    public static void main(String[] args) {
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }).start();

        new Thread(() -> {
            // 保证上面线程先执行
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicReference.compareAndSet(100, 2019);
            System.out.println(atomicReference.get()); // 2019
        }).start();
    }
}
```

当有一个值从 A 改为 B 又改为 A，这就是 ABA 问题。

时间戳原子引用

```

package com.cuzz.thread;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: learn-demo
 * @description: ABA
 * @author: cuzz
 * @create: 2019-04-21 23:31
 **/

public class ABADemo2 {
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 的版本号为：" + stamp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1 );
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1 );
        }).start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 的版本号为：" + stamp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(b); // false
            System.out.println(atomicStampedReference.getReference()); // 100
        }).start();
    }
}
```

我们先保证两个线程的初始版本为一致，后面修改是由于版本不一样就会修改失败。