## java 中锁你知道哪些？请手写一个自旋锁？
> [参考]:http://blog.cuzz.site/2019/04/16/Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B/

### 公平和非公平锁

#### 是什么
  - **公平锁：**是指多个线程按照申请的顺序来获取锁
  - **非公平锁：**是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发的情况下，可能会造成优先级翻转或者饥饿现象
#### 两者区别
  - **公平锁：**在并发环境中，每一个线程在获取锁时会先查看此锁维护的等待队列，如果为空，或者当前线程是等待队列的第一个就占有锁，否则就会加入到等待队列中，以后会按照 FIFO 的规则获取锁
  - **非公平锁：**一上来就尝试占有锁，如果失败再进行排队

### 可重入锁和不可重入锁

- 是什么

  - **可重入锁：**同一个线程在外层方法获取锁之后，进入内层方法会自动获得该锁       
  - **不可重入锁：** 所谓不可重入锁，即若当前线程执行某个方法已经获取了该锁，那么在方法中尝试再次获取锁时，就会因获取不到而被阻塞 

- 代码实现

  - 可重入锁

```
public class ReentrantLock {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;
    public synchronized void lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        while (isLocked && lockedBy != thread) {
            wait();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }
    
    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }
}
```

   - 测试

```

public class Count {
//    NotReentrantLock lock = new NotReentrantLock();
    ReentrantLock lock = new ReentrantLock();
    public void print() throws InterruptedException{
        lock.lock();
        doAdd();
        lock.unlock();
    }

    private void doAdd() throws InterruptedException {
        lock.lock();
        // do something
        System.out.println("ReentrantLock");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        count.print();
    }
}
```

​	发现可以输出 ReentrantLock，我们设计两个线程调用 print() 方法，第一个线程调用 print() 方法获取锁，进入  lock() 方法，由于初始 lockedBy 是 null，所以不会进入 while 而挂起当前线程，而是是增量 lockedCount  并记录 lockBy 为第一个线程。接着第一个线程进入 doAdd() 方法，由于同一进程，所以不会进入 while 而挂起，接着增量  lockedCount，当第二个线程尝试lock，由于 isLocked=true，所以他不会获取该锁，直到第一个线程调用两次 unlock()  将 lockCount 递减为0，才将标记为 isLocked 设置为 false。

  - 不可重入锁

```

public class NotReentrantLock {
    private boolean isLocked = false;
    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }
    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
```

  - 测试


 ```
  public class Count {
      NotReentrantLock lock = new NotReentrantLock();
      public void print() throws InterruptedException{
          lock.lock();
          doAdd();
          lock.unlock();
      }
  
      private void doAdd() throws InterruptedException {
          lock.lock();
          // do something
          lock.unlock();
      }
  
      public static void main(String[] args) throws InterruptedException {
          Count count = new Count();
          count.print();
      }
  }
 ```

  - 当前线程执行print()方法首先获取lock，接下来执行doAdd()方法就无法执行doAdd()中的逻辑，必须先释放锁。这个例子很好的说明了不可重入锁。

  - synchronized 和 ReentrantLock 都是可重入锁

  - synchronzied 

```
public class SynchronziedDemo {

    private synchronized void print() {
        doAdd();
    }
    private synchronized void doAdd() {
        System.out.println("doAdd...");
    }

    public static void main(String[] args) {
        SynchronziedDemo synchronziedDemo = new SynchronziedDemo();
        synchronziedDemo.print(); // doAdd...
    }
}
```

上面可以说明 synchronized 是可重入锁。

ReentrantLock

 ```
    public class ReentrantLockDemo {
        private Lock lock = new ReentrantLock();
    
        private void print() {
            lock.lock();
            doAdd();
            lock.unlock();
        }
    
        private void doAdd() {
            lock.lock();
            lock.lock();
            System.out.println("doAdd...");
            lock.unlock();
            lock.unlock();
        }
    
        public static void main(String[] args) {
            ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
            reentrantLockDemo.print();
        }
    }
 ```

    上面例子可以说明 ReentrantLock 是可重入锁，而且在 #doAdd 方法中加两次锁和解两次锁也可以。

### 自旋锁

- 是指定尝试获取锁的线程不会立即堵塞，而是**采用循环的方式去尝试获取锁**，这样的好处是减少线程上线文切换的消耗，缺点就是循环会消耗 CPU。

- 手动实现自旋锁

  ```
  
  ```

```
public class SpinLock {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();
    private void lock () {
        System.out.println(Thread.currentThread() + " coming...");
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
            // loop
        }
    }

    private void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread + " unlock...");
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        new Thread(() -> {
            spinLock.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hahaha");
            spinLock.unlock();

        }).start();

        Thread.sleep(1);

        new Thread(() -> {
            spinLock.lock();
            System.out.println("hehehe");
            spinLock.unlock();
        }).start();
    }
}
```

输出：


- ```
  Thread[Thread-0,5,main] coming...
  Thread[Thread-1,5,main] coming...
  hahaha
  Thread[Thread-0,5,main] unlock...
  hehehe
  Thread[Thread-1,5,main] unlock...
  ```

  获取锁的时候，如果原子引用为空就获取锁，不为空表示有人获取了锁，就循环等待。

### 独占锁（写锁）/共享锁（读锁）

- 是什么

  - **独占锁：**指该锁一次只能被一个线程持有
  - **共享锁：**该锁可以被多个线程持有

- 对于 ReentrantLock 和 synchronized 都是独占锁；对与 ReentrantReadWriteLock 其读锁是共享锁而写锁是独占锁。读锁的共享可保证并发读是非常高效的，读写、写读和写写的过程是互斥的。

- 读写锁例子

  ```
  
  ```

```
public class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    WriteLock writeLock = lock.writeLock();
    ReadLock readLock = lock.readLock();

    public void put(String key, Object value) {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在写入...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入完成，写入结果是 " + value);
        } finally {
            writeLock.unlock();
        }
    }

    public void get(String key) {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在读...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object res = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完成，读取结果是 " + res);
        } finally {
            readLock.unlock();
        }
    }
}
```

测试

```

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                cache.put(temp + "", temp + "");
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                cache.get(temp + "");
            }).start();
        }
    }
}
```

输出结果

```

Thread-0 正在写入...
Thread-0 写入完成，写入结果是 0
Thread-1 正在写入...
Thread-1 写入完成，写入结果是 1
Thread-2 正在写入...
Thread-2 写入完成，写入结果是 2
Thread-3 正在写入...
Thread-3 写入完成，写入结果是 3
Thread-4 正在写入...
Thread-4 写入完成，写入结果是 4
Thread-5 正在读...
Thread-7 正在读...
Thread-8 正在读...
Thread-6 正在读...
Thread-9 正在读...
Thread-5 读取完成，读取结果是 0
Thread-7 读取完成，读取结果是 2
Thread-8 读取完成，读取结果是 3
Thread-6 读取完成，读取结果是 1
Thread-9 读取完成，读取结果是 4
```



能保证 **读写** 、 **写读** 和 **写写** 的过程是互斥的时候是独享的， **读读** 的时候是共享的。