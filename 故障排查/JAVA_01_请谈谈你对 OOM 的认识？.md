# 请谈谈你对 OOM 的认识？

### java.lang.StackOverflowError

- 递归函数递归过深就会产生这个错误

- 代码验证

```
public class StackOverflowErrorDemo {
	static int cnt = 0;
	public static void main(String[] args) {
		method();
	}

	private static void method() {
		method();
		System.out.println(cnt++);
		
	}
}
```

输出：

```
Exception in thread "main" java.lang.StackOverflowError
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	at com.lannister.java.demo.oom.StackOverflowErrorDemo.method(StackOverflowErrorDemo.java:10)
	··· ···
```

### java.lang.OutOfMemoryError : Java heap space

- new 一个很大对象

- 代码验证

```
import java.util.UUID;

//添加VM参数: -Xms10m -Xmx10m -XX:+PrintGCDetails
public class JavaHeapSpaceDemo {
	public static void main(String[] args) {
		String str = new String("");
		while(true) {
			str += str + UUID.randomUUID().toString();
			System.out.println("**********" + str.length());
		}
	}
}
```

输出：

```
**********36
**********108
**********252
**********540
**********1116
**********2268
**********4572
**********9180
**********18396
[GC (Allocation Failure) [PSYoungGen: 2003K->499K(2560K)] 2003K->815K(9728K), 0.0229596 secs] [Times: user=0.00 sys=0.00, real=0.03 secs] 
**********36828
**********73692
[GC (Allocation Failure) [PSYoungGen: 2411K->472K(2560K)] 2727K->1507K(9728K), 0.0009466 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
**********147420
**********294876
[GC (Allocation Failure) [PSYoungGen: 2240K->408K(2560K)] 4427K->3179K(9728K), 0.0008265 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
**********589788
[GC (Allocation Failure) [PSYoungGen: 2176K->480K(2560K)] 8404K->6731K(9728K), 0.0006883 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 480K->0K(2560K)] [ParOldGen: 6251K->1829K(7168K)] 6731K->1829K(9728K), [Metaspace: 3356K->3356K(1056768K)], 0.0057373 secs] [Times: user=0.06 sys=0.00, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 1192K->64K(2560K)] 5326K->4197K(9728K), 0.0003674 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 64K->32K(1536K)] 4197K->4165K(8704K), 0.0002345 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 32K->0K(1536K)] [ParOldGen: 4133K->4132K(7168K)] 4165K->4132K(8704K), [Metaspace: 3357K->3357K(1056768K)], 0.0049727 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 0K->0K(2048K)] 4132K->4132K(9216K), 0.0003326 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2048K)] [ParOldGen: 4132K->4114K(7168K)] 4132K->4114K(9216K), [Metaspace: 3357K->3357K(1056768K)], 0.0070162 secs] [Times: user=0.05 sys=0.00, real=0.02 secs] 
Exception in thread "main" Heap
 PSYoungGen      total 2048K, used 41K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 1024K, 4% used [0x00000000ffd00000,0x00000000ffd0a508,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 7168K, used 4114K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 57% used [0x00000000ff600000,0x00000000ffa049d0,0x00000000ffd00000)
 Metaspace       used 3387K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 367K, capacity 386K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3332)
	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:448)
	at java.lang.StringBuilder.append(StringBuilder.java:136)
	at com.lannister.java.demo.oom.JavaHeapSpaceDemo.main(JavaHeapSpaceDemo.java:10)
```

### java.lang.OutOfMemoryError : GC overhead limit exceeded

- 执行垃圾收集的时间比例太大， 有效的运算量太小，默认情况下,，如果GC花费的时间超过 **98%**， 并且GC回收的内存少于 **2%**， JVM就会抛出这个错误。

- 代码验证

```
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

//添加VM参数: -Xms10m -Xmx10m -XX:+PrintGCDetails
public class GCOverheadLimitExceededDemo {
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		
		while(true) {
			list.add(UUID.randomUUID().toString());
			System.out.println("*************" + list.size());
		}
	}
}
```

输出：

```
[Full GC (Ergonomics) [PSYoungGen: 2048K->2045K(2560K)] [ParOldGen: 7104K->7104K(7168K)] 9152K->9149K(9728K), [Metaspace: 3438K->3438K(1056768K)], 0.0209153 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
*************63901
*************63902
*************63903
[Full GC (Ergonomics) [PSYoungGen: 2048K->2045K(2560K)] [ParOldGen: 7104K->7104K(7168K)] 9152K->9150K(9728K), [Metaspace: 3438K->3438K(1056768K)], 0.0186831 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
*************63904
*************63905
[Full GC (Ergonomics) [PSYoungGen: 2048K->2045K(2560K)] [ParOldGen: 7104K->7104K(7168K)] 9152K->9150K(9728K), [Metaspace: 3438K->3438K(1056768K)], 0.0170833 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
*************63906
*************63907
[Full GC (Ergonomics) [PSYoungGen: 2048K->2045K(2560K)] [ParOldGen: 7104K->7104K(7168K)] 9152K->9150K(9728K), [Metaspace: 3438K->3438K(1056768K)], 0.0173121 secs] [Times: user=0.09 sys=0.00, real=0.03 secs] 
Exception in thread "main" [Full GC (Ergonomics) [PSYoungGen: 2048K->0K(2560K)] [ParOldGen: 7104K->664K(7168K)] 9152K->664K(9728K), [Metaspace: 3462K->3462K(1056768K)], 0.0074965 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
java.lang.OutOfMemoryError: GC overhead limit exceeded
	at java.lang.StringBuilder.toString(StringBuilder.java:407)
	at com.lannister.java.demo.oom.GCOverheadLimitExceededDemo.main(GCOverheadLimitExceededDemo.java:14)
Heap
 PSYoungGen      total 2560K, used 45K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 2% used [0x00000000ffd00000,0x00000000ffd0b648,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 7168K, used 664K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 9% used [0x00000000ff600000,0x00000000ff6a60f8,0x00000000ffd00000)
 Metaspace       used 3469K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 371K, capacity 386K, committed 512K, reserved 1048576K
```

### java.lang.OutOfMemoryError : Direct buffer memory

- 写NIO程序经常使用ByteBuffer读取或者写入数据，这是一种基于通道（Channel）与缓冲区（Buffer）的I/O方式， 它可以使用Native函数库直接分配堆外内存，然后通过一个存储在Java堆里面的DirectByteBuffer对象 作为这块内存的引用进行操作，这样能在一些场景中显著提高性能，因而避免了在Java堆和Native堆中来回复制数据。
	- ByteBuffer.allocate(capability) 这种方式是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度较慢
	- ByteBuffer.allocteDirect(capability) 这种方式是分配OS本地内存，不属于GC管辖范围，由于不需要内存拷贝所以速度较快。
- 但如果不断分配本地内存，堆内存很少使用，那么JVM就不需要执行GC，DirectByteBuffer对象们就不会被回收，这时候堆内存充足，但本地内存可能已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError，那程序就崩溃了。
	
- 代码验证

```
import java.nio.ByteBuffer;

//添加VM参数: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
public class DirectBufferMemoryDemo {
	public static void main(String[] args) {
		System.out.println("Max Direct Memory: " + sun.misc.VM.maxDirectMemory() / 1024.0 / 1024.0 + " MB");
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
		
	}
}
```

输出：

```
Max Direct Memory: 5.0 MB
[GC (System.gc()) [PSYoungGen: 834K->488K(2560K)] 834K->608K(9728K), 0.0009927 secs] [Times: user=0.06 sys=0.00, real=0.00 secs] 
[Full GC (System.gc()) [PSYoungGen: 488K->0K(2560K)] [ParOldGen: 120K->578K(7168K)] 608K->578K(9728K), [Metaspace: 2699K->2699K(1056768K)], 0.0067447 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
	at java.nio.Bits.reserveMemory(Bits.java:694)
	at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
	at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
	at com.lannister.java.demo.oom.DirectBufferMemoryDemo.main(DirectBufferMemoryDemo.java:10)
Heap
 PSYoungGen      total 2560K, used 61K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 3% used [0x00000000ffd00000,0x00000000ffd0f750,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 7168K, used 578K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 8% used [0x00000000ff600000,0x00000000ff690a00,0x00000000ffd00000)
 Metaspace       used 2730K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 293K, capacity 386K, committed 512K, reserved 1048576K
```

### java.lang.OutOfMemoryError : unable to create new native thread

- 创建线程数太多了
  
- 代码验证

```
import java.util.concurrent.CountDownLatch;

//添加VM参数: -Xss100m -Xms7168m -Xmx7168m -XX:+PrintGCDetails
public class UnableToCreateNewNativeThread {
	public static void main(String[] args) {
		int i = 0;
		
		while(true) {
			try {
				new Thread(new HoldThread()).start();
				i++;
			} catch (Throwable e) {
				
				e.printStackTrace();
				System.out.println("************已经创建线程数：" + i);
			}
		}
	}
}

class HoldThread extends Thread{
	CountDownLatch cdl = new CountDownLatch(1);
	
	public HoldThread() {
		this.setDaemon(true);
	}
	
	public void run() {
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 }
```

输出：

```
[GC (Allocation Failure) [PSYoungGen: 1835008K->1288K(2140672K)] 1835008K->1296K(7034368K), 0.0312116 secs] [Times: user=0.00 sys=0.00, real=0.03 secs] 
[GC (Allocation Failure) [PSYoungGen: 1836296K->2664K(2140672K)] 1836304K->2672K(7034368K), 0.0049221 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 1837672K->27448K(2140672K)] 1837680K->27464K(7034368K), 0.0482231 secs] [Times: user=0.09 sys=0.02, real=0.06 secs] 
java.lang.OutOfMemoryError: unable to create new native thread
************已经创建线程数：80018
************已经创建线程数：80037
************已经创建线程数：80040
************已经创建线程数：80065
************已经创建线程数：80065
************已经创建线程数：80072
************已经创建线程数：80072
************已经创建线程数：80074
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.lannister.java.demo.oom.UnableToCreateNewNativeThread.main(UnableToCreateNewNativeThread.java:12)
```
### java.lang.OutOfMemoryError : Metaspace

- Java 8 之后的版本使用元空间（Metaspace）代替了永久代，元空间是方法区在 HotSpot 中的实现，它与持久代最大的区别是：元空间并不在虚拟机中的内存中而是使用本地内存。

- 元空间存放的信息：
  - 虚拟机加载的类信息
  - 常量池
  - 静态变量
  - 即时编译后的代码
  
- 代码验证

```
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

//添加VM参数: -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
public class MetaspaceDemo {
	static class OOMTest{}
	public static void main(String[] args) {
		int i = 0;
		
		try {
			while(true) {
				i++;
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(OOMTest.class);
				enhancer.setUseCache(false);
				enhancer.setCallback(new MethodInterceptor() {
					
					public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
						// TODO Auto-generated method stub
						return proxy.invokeSuper(obj, args);
					}
				});
				enhancer.create();
			}
		} catch (Throwable e) {
			// TODO: handle exception
			System.out.println("************多少次后发生了错误： " + i);
			e.printStackTrace();
		}
	}
}
```

输出：

```
************多少次后发生了错误： 319
java.lang.OutOfMemoryError: Metaspace
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:348)
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:467)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:339)
	at net.sf.cglib.proxy.Enhancer.generate(Enhancer.java:492)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:117)
	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:294)
	at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:480)
	at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:305)
	at com.lannister.java.demo.oom.MetaspaceDemo.main(MetaspaceDemo.java:29)

```

