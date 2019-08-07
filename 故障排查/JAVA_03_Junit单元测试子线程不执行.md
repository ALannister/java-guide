# Junit单元测试子线程不执行

### 在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程) 

- 用个比较通俗的比如，任何一个守护线程都是整个JVM中所有非守护线程的保姆。
	- 只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；
	- 只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。

- Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者。

- User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果 User  Thread已经全部退出运行了，只剩下Daemon Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。

- 值得一提的是，守护线程并非只有虚拟机内部提供，用户在编写程序时也可以自己设置守护线程。下面的方法就是用来设置守护线程的。

```
Thread daemonTread = new Thread();  
    
// 设定 daemonThread 为 守护线程，default false(非守护线程)  daemonThread.setDaemon(true);

// 验证当前线程是否为守护线程，返回 true 则为守护线程  
daemonThread.isDaemon();  
```

- 这里有几点需要注意： 

  - thread.setDaemon(true)必须在thread.start()之前设置，否则会抛出一个IllegalThreadStateException异常。你不能把正在运行的线程设置为守护线程。
  - 在Daemon线程中产生的新线程也是Daemon的。 
  - 不要认为所有的应用都可以分配给Daemon来进行服务，比如读写操作或者计算逻辑。

 	因为你不可能知道在所有的User Thread完成之前，Daemon Thread是否已经完成了预期的服务任务。一旦User退出了，可能大量数据还没有来得及读入或写出，计算任务也可能多次运行结果不一样。这对程序是毁灭性的。造成这个结果理由已经说过了：一旦所有User  Thread离开了，虚拟机也就退出运行了。 

### 线程的退出

- java主线程main结束后，是否程序就结束了？

	- C中的（windows，linux）main函数执行完，整个进程结束，其子线程也被强制结束退出。这个是C/C++与java很大的不同。C/C++，通常需要主线程主动等待子线程完成再退出。
	- java不需要在main函数中显式等待子线程（非Daemon线程）退出，而是自动创建DestroyJavaVM线程来管理。
	- Junit测试方法中，当主线程退出，子线程也会被强制退出

- java虚拟机启动程序步骤：

	1. main是启动时候的主线程，即程序入口
	2. 在main函数结束后，虚拟机会自动启动一个DestroyJavaVM线程，该线程会等待所有User Thread 线程结束后退出（即，只剩下daemon  线程和DestroyJavaVM线程自己，整个虚拟机就退出，此时daemon线程被终止），因此，如果不希望程序退出，只要创建一个非daemon的子线程，让线程不停的sleep即可。

### 线程退出代码验证

- 代码

```
package com.lannister.java.demo.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadPoolExecutorDemo02 {
	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2));
		threadPoolExecutor.execute(()->{
			System.out.println(Thread.currentThread().getName() + " start");
			Thread[] tarray; 
			while(true) {
				tarray = new Thread[Thread.activeCount()];
				Thread.enumerate(tarray);
				System.out.println("------------------");
				for (Thread thread : tarray) {
					
					System.out.println(thread.getName() + "\t" +Thread.currentThread().isDaemon());
				}
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println(Thread.currentThread().getName() + "\t" +Thread.currentThread().isDaemon());
		threadPoolExecutor.shutdown();
	}
	
	@Test
	public void test() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2));
		threadPoolExecutor.execute(()->{
			System.out.println(Thread.currentThread().getName() + " start");
			Thread[] tarray; 
			while(true) {
				tarray = new Thread[Thread.activeCount()];
				Thread.enumerate(tarray);
				System.out.println("------------------");
				for (Thread thread : tarray) {
					
					System.out.println(thread.getName() + "\t" +Thread.currentThread().isDaemon());
				}
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println(Thread.currentThread().getName() + "\t" +Thread.currentThread().isDaemon());
		threadPoolExecutor.shutdown();
	}
}
```

- 执行main方法的输出

```
pool-1-thread-1 start
------------------
main	false
pool-1-thread-1	false
main	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false
------------------
pool-1-thread-1	false
DestroyJavaVM	false

持续不断输出
```

在main函数结束后，虚拟机会自动启动一个DestroyJavaVM线程，该线程会等待所有User Thread 结束后退出

- 执行test()方法输出

```
main	false
pool-1-thread-1 start
------------------
main	false
ReaderThread	false
pool-1-thread-1	false
```
test函数结束后，所有User Thread 被强制退出