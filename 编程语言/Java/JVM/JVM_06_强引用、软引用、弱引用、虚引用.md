# 强引用、软引用、弱引用和虚引用分别是什么？

在Java语言中，除了基本数据类型外，其他的都是指向各类对象的对象引用；Java中根据对象引用生命周期的长短，将它们分为4类。

### 强引用

- 特点：我们平常典型编码Object obj = new  Object()中的obj就是强引用。当JVM内存空间不足，JVM宁愿抛出OutOfMemoryError运行时错误（OOM），使程序异常终止，也不会靠随意回收具有强引用的**存活**对象来解决内存不足的问题。对于一个普通的对象，如果没有其他的引用关系，只要超过了引用的作用域或者显式地将相应（强）引用赋值为  null，就是可以被垃圾收集的了，具体回收时机还是要看垃圾收集策略。

- 代码验证

```
	@Test
	//添加VM参数: -Xms10m -Xmx10m -XX:+PrintGCDetails
	public void lackMem() {
		Object o1 = new Object();
		Object o2 = o1;
		System.out.println(o1);
		System.out.println(o2);
		
		o1 = null;
		try {
			byte[] bs = new byte[20 * 1024 * 1024];
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("==========GC==========");
			System.out.println(o1);
			System.out.println(o2);
		}
		
	}
```

输出：

```
[GC (Allocation Failure) [PSYoungGen: 2048K->504K(2560K)] 2048K->960K(9728K), 0.0019676 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2552K->504K(2560K)] 3008K->1555K(9728K), 0.0015930 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
java.lang.Object@28c97a5
java.lang.Object@28c97a5
[GC (Allocation Failure) [PSYoungGen: 1481K->504K(2560K)] 2533K->1756K(9728K), 0.0017490 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 504K->504K(2560K)] 1756K->1772K(9728K), 0.0016814 secs] [Times: user=0.05 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 504K->0K(2560K)] [ParOldGen: 1268K->1574K(7168K)] 1772K->1574K(9728K), [Metaspace: 6280K->6280K(1056768K)], 0.0141559 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 1574K->1574K(9728K), 0.0003931 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 1574K->1485K(7168K)] 1574K->1485K(9728K), [Metaspace: 6280K->6276K(1056768K)], 0.0145399 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
==========GC==========
null
java.lang.Object@28c97a5
Heap
 PSYoungGen      total 2560K, used 224K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 10% used [0x00000000ffd00000,0x00000000ffd38020,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 7168K, used 1485K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 20% used [0x00000000ff600000,0x00000000ff7736b0,0x00000000ffd00000)
 Metaspace       used 6325K, capacity 6478K, committed 6656K, reserved 1056768K
  class space    used 723K, capacity 754K, committed 768K, reserved 1048576K
```
​		即使oom也不会回收具有强引用的存活对象

### 软引用

- 特点：软引用通过SoftReference类实现。 软引用的生命周期比强引用短一些。只有当 JVM  认为内存不足时，才会去试图回收软引用指向的对象：即JVM 会确保在抛出 OutOfMemoryError  之前，清理软引用指向的对象。软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收器回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。后续，我们可以调用ReferenceQueue的poll()方法来检查是否有它所关心的对象被回收。如果队列为空，将返回一个null,否则该方法返回队列中前面的一个Reference对象。

- 应用场景：软引用通常用来实现内存敏感的缓存。如果还有空闲内存，就可以暂时保留缓存，当内存不足时清理掉，这样就保证了使用缓存的同时，不会耗尽内存。

- 代码验证

``` 
	@Test
	//添加VM参数: -Xms10m -Xmx10m -XX:+PrintGCDetails
	public void lackMemWithQueue() {
		Object o = new Object();
		ReferenceQueue<Object> queue = new ReferenceQueue<>();
		SoftReference<Object> soft = new SoftReference<Object>(o, queue);
		System.out.println(o);
		System.out.println(soft.get());
		System.out.println(queue.poll());
		
		o = null;
		try {
			byte[] bs = new byte[20 * 1024 * 1024];
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("==========GC==========");
			System.out.println(o);
			System.out.println(soft.get());
			System.out.println(queue.poll());
		}
	}
```

输出

```
[GC (Allocation Failure) [PSYoungGen: 2048K->488K(2560K)] 2048K->946K(9728K), 0.0019989 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 2536K->504K(2560K)] 2994K->1554K(9728K), 0.0026967 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
java.lang.Object@28c97a5
java.lang.Object@28c97a5
null
[GC (Allocation Failure) [PSYoungGen: 1470K->504K(2560K)] 2521K->1738K(9728K), 0.0014548 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 504K->504K(2560K)] 1738K->1770K(9728K), 0.0011067 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 504K->0K(2560K)] [ParOldGen: 1266K->1576K(7168K)] 1770K->1576K(9728K), [Metaspace: 6301K->6301K(1056768K)], 0.0162382 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 1576K->1576K(9728K), 0.0004082 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 1576K->1487K(7168K)] 1576K->1487K(9728K), [Metaspace: 6301K->6298K(1056768K)], 0.0138202 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
==========GC==========
null
null
java.lang.ref.SoftReference@6659c656
Heap
 PSYoungGen      total 2560K, used 223K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 10% used [0x00000000ffd00000,0x00000000ffd37fd8,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 7168K, used 1487K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 20% used [0x00000000ff600000,0x00000000ff773ed0,0x00000000ffd00000)
 Metaspace       used 6343K, capacity 6478K, committed 6656K, reserved 1056768K
  class space    used 723K, capacity 754K, committed 768K, reserved 1048576K

```

​		当内存不够的时候软引用指向的对象就会被回收。

### 弱引用

- 特点：弱引用通过WeakReference类实现。 弱引用的生命周期比软引用短。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。由于垃圾回收器是一个优先级很低的线程，因此不一定会很快回收弱引用的对象。弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。

- 应用场景：弱应用同样可用于内存敏感的缓存。

- 代码验证

```
	@Test
	public void enoughMemWithQueue() {
		Object o = new Object();
		ReferenceQueue<Object> queue = new ReferenceQueue<>();
		WeakReference<Object> weak = new WeakReference<Object>(o, queue);
		System.out.println(o);
		System.out.println(weak.get());
		System.out.println(queue.poll());
		
		o = null;
		System.gc();
		System.out.println("==========GC==========");
		//gc线程优先级较低，必须延时一会儿，让gc线程清理完对象后将引用放入队列
		try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
		
		System.out.println(o);
		System.out.println(weak.get());
		System.out.println(queue.poll());
	}
```

输出

```
java.lang.Object@28c97a5
java.lang.Object@28c97a5
null
==========GC==========
null
null
java.lang.ref.WeakReference@6659c656
```

		值得注意的是:
		- 将`Object o = new Object()`换为`Object o = "nihao"`,`"nihao"`会放入字符串常量池，然后GC也不会被回收。
		- 将`Object o = new Object()`换为`Object o = 1`,只要这个整数在[-128,127]范围内，就被会放入缓存，然后GC也不会被回收。
		- 将最后的`System.out.println(queue.poll())`换为`System.out.println(queue.poll().get())`,输出会变为null,因为引用指向的对象已经被清空了

### 虚引用

- 特点：虚引用也叫幻象引用，通过PhantomReference类来实现。无法通过虚引用访问对象的任何属性或函数。幻象引用仅仅是提供了一种确保对象被  finalize  以后，做某些事情的机制。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。虚引用必须和引用队列  （ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之后，把这个虚引用加入到与之关联的引用队列中。程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否已经被垃圾回收。

- 应用场景：可用来跟踪对象被垃圾回收器回收的活动，当一个虚引用关联的对象被垃圾收集器回收之后会收到一条系统通知。

- 代码验证

```
		@Test
		public void EnoughMemWithQueue() {
			Object o = new Object();
			ReferenceQueue<Object> queue = new ReferenceQueue<>();
			PhantomReference<Object> phantom = new PhantomReference<Object>(o, queue);
			System.out.println(o);
			System.out.println(phantom.get());
			System.out.println(queue.poll());
			
			o = null;
			System.gc();
			System.out.println("==========GC==========");

			System.out.println(o);
			System.out.println(phantom.get());
			System.out.println(queue.poll());
		}
```

输出

```
java.lang.Object@28c97a5
null
null
==========GC==========
null
null
java.lang.ref.PhantomReference@6659c656

```