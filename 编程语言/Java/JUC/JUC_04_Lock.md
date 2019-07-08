## java 中锁你知道哪些？请手写一个自旋锁？

> [参考]:http://blog.cuzz.site/2019/04/16/Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B/

### 目录

[TOC]

### 1 公平和非公平锁

#### 1.1 是什么

- **公平锁：**是指多个线程按照申请的顺序来获取锁
- **非公平锁：**是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发的情况下，可能会造成优先级翻转或者饥饿现象

#### 1.2 两者区别

- **公平锁：**在并发环境中，每一个线程在获取锁时会先查看此锁维护的等待队列，如果为空，或者当前线程是等待队列的第一个就占有锁，否则就会加入到等待队列中，以后会按照 FIFO 的规则获取锁
- **非公平锁：**一上来就尝试占有锁，如果失败再进行排队

### 2 可重入锁和不可重入锁

#### 2.1 是什么

- **可重入锁：**同一个线程在外层方法获取锁之后，进入内层方法会自动获得该锁       
- **不可重入锁：** 所谓不可重入锁，即若当前线程执行某个方法已经获取了该锁，那么在方法中尝试再次获取锁时，就会因获取不到而被阻塞 

#### 2.2 代码实现可重入锁

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
                lockedBy = null;
                notify();
            }
        }
    }
}
```
- 测试

```

class Data{
	ReentrantLock lock = new ReentrantLock();
	
	public void method1() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "\t method1 in");
		method2();
		System.out.println(Thread.currentThread().getName() + "\t method1 out");
		lock.unlock();
	}
	public void method2() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "\t method2 in");
		System.out.println(Thread.currentThread().getName() + "\t method2 out");
		lock.unlock();
	}
}
public class ReentrantLockDemo {
	public static void main(String[] args) {
		Data data = new Data();
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for(int i = 0; i < 2; i++) {
			executorService.execute(()->{
				data.method1();
			});
		}
	}
}
```

​	我们设计两个线程调用 method1() 方法，第一个线程调用 method1() 方法获取锁，进入  lock() 方法，由于初始 lockedBy 是 null，所以不会进入 while 而挂起当前线程，而是增加 lockedCount  并记录 lockBy 为第一个线程。接着第一个线程进入 method2() 方法，由于同一进程，所以不会进入 while 而挂起，接着增加  lockedCount，当第二个线程尝试lock，由于 isLocked=true，所以他不会获取该锁，直到第一个线程调用两次 unlock()  将 lockCount 递减为0，才将标记为 isLocked 设置为 false。

#### 2.3 代码实现不可重入锁

```
class NotReentrantLock{
	boolean locked = false;
	Thread lockedBy = null;
	public synchronized void lock() {
		Thread thread = Thread.currentThread();
		while(locked == true) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		locked = true;
		lockedBy = thread;
	}
	public synchronized void unlock() {
		Thread thread = Thread.currentThread();
		if(lockedBy == thread) {
			locked = false;
			lockedBy = null;
			this.notify();
		}
	}
}
```

- 测试

 ```
class Data{
	NotReentrantLock lock = new NotReentrantLock();
	
	public void method1() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "\t method1 in");
		method2();
		System.out.println(Thread.currentThread().getName() + "\t method1 out");
		lock.unlock();
	}
	public void method2() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "\t method2 in");
		System.out.println(Thread.currentThread().getName() + "\t method2 out");
		lock.unlock();
	}
}
public class NotReentrantLockDemo {
	public static void main(String[] args) {
		Data data = new Data();
		data.method1();
	}
}
 ```

​	当前线程执行method1方法首先获取lock，接下来执行method2()方法就无法获取锁。这个例子很好的说明了不可重入锁。

#### 2.4 synchronized 和 ReentrantLock 都是可重入锁

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

​	上面可以说明 synchronized 是可重入锁。

- ReentrantLock

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

​	上面例子可以说明 ReentrantLock 是可重入锁，而且在 doAdd 方法中加两次锁和解两次锁也可以。

#### 2.5 synchronized 和 ReentrantLock 有什么区别？

- 原始结构

	- `synchronized`是关键字属于`JVM`层面，反映在字节码上是`monitorenter`和`monitorexit`，其底层是通过`monitor`对象来完成，其实`wait/notify`等方法也是依赖`monitor`对象，只有在同步块或方法中才能调用`wait/notify`等方法。
	
	- `ReentrantLock`是具体类（`java.util.concurrent.locks.ReentrantLock`），是`api`层面的锁。
	
- 使用方法

	- `synchronized`不需要用户手动去释放锁，当`synchronized`代码执行完后系统会自动让线程释放对锁的占用。
	
	- `ReentrantLock`则需要用户手动的释放锁，若没有主动释放锁，可能导致出现死锁的现象，`lock()`和`unlock()`方法需要配合`try/finally`语句来完成。

- 等待是否可中断

	- `synchronized`不可中断，除非抛出异常或者正常运行完成。
	
	- `ReentrantLock`可中断，设置超时方法`tryLock(long timeout, TimeUnit unit)，lockInterruptibly()`放代码块中，调用`interrupt()`方法可中断。

- 加锁是否公平

	- `synchronized`非公平锁
	
	- `ReentrantLock`默认非公平锁，构造方法中可以传入`boolean`值，`true`为公平锁，`false`为非公平锁。

- 锁可以绑定多个`Condition`
	- `synchronized`没有`Condition`。
	- `ReentrantLock`用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像 `synchronized`要么随机唤醒一个线程要么唤醒全部线程。

#### 2.6 ReentrantLock的lock()、tryLock()、lockInterruptibly()有什么区别？

Lock接口的线程请求锁的几个方法：

- lock()忽视interrupt(), 拿不到锁就一直阻塞：

```
	@Test
	public void test1() {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " get lock");
		
		Thread t = new Thread(()->{
			lock.lock();
			System.out.println(Thread.currentThread().getName() + " get lock");
			lock.unlock();
		},"t");
		
		t.start();
		System.out.println("t start");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t.interrupt();
		System.out.println("t was interrupted");
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```

输出：

```
main get lock
t start
t was interrupted
之后一直阻塞
```

- lockInterruptibly()是对lock()的改进，和lock()一样拿不到锁会一直阻塞，但是，阻塞期间如果别的进程调用此进程的interrupt()方法，此线程会被唤醒并被要求处理InterruptedException

```
  	@Test
  	public void test2() {
  		ReentrantLock lock = new ReentrantLock();
  		lock.lock();
  		System.out.println(Thread.currentThread().getName() + " get lock");
  		
		Thread t = new Thread(()->{
			try {
				lock.lockInterruptibly();
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " handle the interrupt");
			}
			System.out.println(Thread.currentThread().getName() + (lock.isHeldByCurrentThread() ? " get lock" : " don't get lock"));
			try {
				lock.unlock();
			} catch (IllegalMonitorStateException e) {
			}
	
		},"t");
		
		t.start();
		System.out.println("t start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();
		System.out.println("t was interrupted");
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```

输出：

```
main get lock
t start
t was interrupted
t handle the interrupte
t don't get lock
```

- tryLock()，马上返回，拿到lock就返回true，不然返回false。

```
	@Test
	public void test3() {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " get lock");
		
		Thread t = new Thread(()->{
			lock.tryLock();
			System.out.println(Thread.currentThread().getName() + (lock.isHeldByCurrentThread() ? " get lock" : " don't get lock"));
			try {
				lock.unlock();
			} catch (IllegalMonitorStateException e) {
			}
		},"t");
		t.start();
		System.out.println("t start");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```

输出：

```
main get lock
t start
t don't get lock
```

- 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。等待期间如果别的进程调用此进程的interrupt()方法，此线程会被唤醒并被要求处理InterruptedException

```
	@Test
	public void test4() {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " get lock");
		
		Thread t = new Thread(()->{
			try {
				lock.tryLock(5,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " handle the interrupte");
			}
			
			System.out.println(Thread.currentThread().getName() + (lock.isHeldByCurrentThread() ? " get lock" : " don't get lock"));
			try {
				lock.unlock();
			} catch (IllegalMonitorStateException e) {
			}
		},"t");
		t.start();
		System.out.println("t start");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t.interrupt();
		System.out.println("t was interrupted");
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```

输出：

```
main get lock
t start
t was interrupted
t handle the interrupte
t don't get lock
```

### 3 自旋锁

#### 3.1 是什么

- 是指尝试获取锁的线程不会立即堵塞，而是**采用循环的方式去尝试获取锁**，这样的好处是减少线程上下文切换的消耗，缺点就是循环会消耗 CPU。

#### 3.2 手动实现自旋锁

```
public class SpinLock {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();
    private void lock () {
        
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
            // loop
        }
        System.out.println(Thread.currentThread() + " get lock...");
    }

    private void unlock() {
        while (!atomicReference.compareAndSet(Thread.currentThread(), null)) {
            // loop
        }
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

- 输出：


```
  Thread[Thread-0,5,main] get lock...
  hahaha
  Thread[Thread-0,5,main] unlock...
  Thread[Thread-1,5,main] get lock...
  hehehe
  Thread[Thread-1,5,main] unlock...
```

​	获取锁的时候，如果原子引用为空就获取锁，不为空表示有人获取了锁，就循环等待。

### 4 独占锁（写锁）/共享锁（读锁）

#### 4.1 是什么

- **独占锁：**指该锁一次只能被一个线程持有
- **共享锁：**该锁可以被多个线程持有
	
- ReentrantLock 和 synchronized 都是独占锁
- 对于ReentrantReadWriteLock，其读锁是共享锁而写锁是独占锁。读锁的共享可保证并发读是非常高效的，读写、写读和写写的过程是互斥的。

#### 4.2 读写锁例子

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

- 测试

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

- 输出结果

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

​	能保证 **读写** 、 **写读** 和 **写写** 的过程是独享的， **读读** 的时候是共享的。