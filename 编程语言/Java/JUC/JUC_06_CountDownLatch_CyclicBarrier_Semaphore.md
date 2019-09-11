## CountDownLatch/CyclicBarrier/Semaphore 使用过吗？

### CountDownLatch

​	CountDownLatch是一个同步工具类。它允许一个或多个线程一直等待,直到其他线程执行完后再执行。CountDownLatch  主要有两个方法，当一个或多个线程调用 await 方法时，调用线程会被堵塞，其他线程调用 countDown 方法会将计数减一（调用  countDown 方法的线程不会堵塞），当计数其值变为零时，因调用 await 方法被堵塞的线程会被唤醒，继续执行。

​	假设我们有这么一个场景，教室里有班长和其他6个人在教室上自习，怎么保证班长等其他6个人都走出教室再把教室门给关掉。

```

public class CountDownLanchDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 离开了教室...");
            }, String.valueOf(i)).start();
        }
        System.out.println("班长把门给关了，离开了教室...");
    }
}
```

此时输出

```

0 离开了教室...
1 离开了教室...
2 离开了教室...
3 离开了教室...
班长把门给关了，离开了教室...
5 离开了教室...
4 离开了教室...
```

​	发现班长都没有等其他人理他教室就把门给关了，此时我们就可以使用 CountDownLatch 来控制

```

public class CountDownLanchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " 离开了教室...");
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("班长把门给关了，离开了教室...");
    }
}
```

此时输出

```

0 离开了教室...
1 离开了教室...
2 离开了教室...
3 离开了教室...
4 离开了教室...
5 离开了教室...
班长把门给关了，离开了教室...
```

### CyclicBarrier

我们假设有这么一个场景，每辆车只能坐4个人，当车满了，就发车。

```

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("车满了，开始出发...");
        });
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 开始上车...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```



输出结果

```

Thread-0 开始上车...
Thread-1 开始上车...
Thread-3 开始上车...
Thread-4 开始上车...
车满了，开始出发...
Thread-5 开始上车...
Thread-7 开始上车...
Thread-2 开始上车...
Thread-6 开始上车...
车满了，开始出发...
```

### Semaphore

​	假设我们有 3 个停车位，6 辆车去抢

```
public class SemaphoreDemo {
  public static void main(String[] args) {
      Semaphore semaphore = new Semaphore(3);
      for (int i = 0; i < 6; i++) {
          new Thread(() -> {
              try {
                  semaphore.acquire(); // 获取一个许可
                  System.out.println(Thread.currentThread().getName() + " 抢到车位...");
                  Thread.sleep(3000);
                  System.out.println(Thread.currentThread().getName() + " 离开车位");
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } finally {
                  semaphore.release(); // 释放一个许可
              }
          }).start();
      }
  }
}
```

输出

```
Thread-1 抢到车位...
Thread-2 抢到车位...
Thread-0 抢到车位...
Thread-2 离开车位
Thread-0 离开车位
Thread-3 抢到车位...
Thread-1 离开车位
Thread-4 抢到车位...
Thread-5 抢到车位...
Thread-3 离开车位
Thread-5 离开车位
Thread-4 离开车位
```