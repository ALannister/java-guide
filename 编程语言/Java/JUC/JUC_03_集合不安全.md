## 我们知道 ArrayList 线程不安全，请编写一个不安全的案例并给出解决方案？

- 故障现象

```
public class UnsafeListDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(random.nextInt(10));
                System.out.println(list);
            }).start();
        }
    }
}
```

- 发现报 `java.util.ConcurrentModificationException` 

- 故障原因

  - 并发修改导致的异常

- 解决方案

```
	List<Integer> list = new Vector<Integer>();//synchronized方法
	List<Integer> list = Collections.synchronizedList(new ArrayList<>());//synchronized方法
	List<Integer> list = new CopyOnWriteArrayList<Integer>();//读写分离，写时复制,lock
```

- 优化建议

  - 在读多写少的时候推荐使用 CopyOnWriteArrayList 这个类

## 我们知道 HashSet 线程不安全，请编写一个不安全的案例并给出解决方案？

- 故障现象

```
public class UnsafeSetDemo {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		Random random = new Random();
		ExecutorService executorService = Executors.newFixedThreadPool(7);
		for(int i = 0; i < 20; i++) {
			executorService.execute(()->{
				set.add(random.nextInt(10));
				System.out.println(set);
			});
		}
	}
}
```

- 发现报 java.util.ConcurrentModificationException 

- 故障原因

  - 并发修改导致的异常

- 解决方案

```
Set<Integer> set = Collections.synchronizedSet(new HashSet<>());//synchronized方法
Set<Integer> set = new CopyOnWriteArraySet<Integer>();//读写分离，写时复制,lock
Set<Integer> set = new ConcurrentSkipListSet<Integer>();
```

- 优化建议

  - 在读多写少的时候推荐使用 CopyOnWriteArraySet 这个类

## 我们知道 HashMap 线程不安全，请编写一个不安全的案例并给出解决方案？

- 故障现象

```
public class UnsafeMapDemo {
	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<>();
		Random random = new Random();
		ExecutorService executorService = Executors.newFixedThreadPool(7);
		for(int i = 0; i < 20; i++) {
			executorService.execute(()->{
				map.put(random.nextInt(10),random.nextInt(10));
				System.out.println(map);
			});
		}
		
	}
}
```

- 发现报 java.util.ConcurrentModificationException 

- 故障原因

  - 并发修改导致的异常

- 解决方案

```
Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
Map<Integer, Integer> map = new ConcurrentSkipListMap<Integer, Integer>();
```

- 优化建议

  - 在高并发情况下推荐使用 ConcurrentHashMap 这个类