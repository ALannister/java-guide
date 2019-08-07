# producer消息发送机制

> kafka有同步（sync）、异步（async）以及oneway这三种发送方式，某些概念上区分也可以分为同步和异步两种，同步和异步的发送方式通过“producer.type”参数指定，而oneway由“request.require.acks”参数指定。

## 1. sync vs async

在官方文档Producer Configs中有如下表述：

| Property      | Default | Description                                                  |
| ------------- | ------- | ------------------------------------------------------------ |
| producer.type | sync    | This parameter specifies whether the messages are sent asynchronously in a background thread. Valid values are (1) async for asynchronous send and (2) sync for synchronous send. By setting the producer to async we allow batching together of requests (which is great for throughput) but open the possibility of a failure of the client machine dropping unsent data. |

翻译过来就是：
producer.type的默认值是sync，即同步的方式。这个参数指定了在后台线程中消息的发送方式是同步的还是异步的。如果设置成异步的模式，可以运行生产者以batch的形式push数据，这样会极大的提高broker的性能，但是这样会增加丢失数据的风险。

对于异步模式，还有4个配套的参数，如下：

| Property                     | Default | Description                                                  |
| ---------------------------- | ------- | ------------------------------------------------------------ |
| queue.buffering.max.ms       | 5000    | 启用异步模式时，producer缓存消息的时间。比如我们设置成1000时，它会缓存1s的数据再一次发送出去，这样可以极大的增加broker吞吐量，但也会造成时效性的降低。 |
| queue.buffering.max.messages | 10000   | 启用异步模式时，producer缓存队列里最大缓存的消息数量，如果超过这个值，producer就会阻塞或者丢掉消息。 |
| queue.enqueue.timeout.ms     | -1      | 当达到上面参数时producer会阻塞等待的时间。如果设置为0，buffer队列满时producer不会阻塞，消息直接被丢掉；若设置为-1，producer会被阻塞，不会丢消息。 |
| batch.num.messages           | 200     | 启用异步模式时，一个batch缓存的消息数量。达到这个数值时，producer才会发送消息。（每次批量发送的数量） |

> 以batch的方式推送数据可以极大的提高处理效率，kafka producer可以将消息在内存中累计到一定数量后作为一个batch发送请求。batch的数量大小可以通过producer的参数（batch.num.messages）控制。通过增加batch的大小，可以减少网络请求和磁盘IO的次数，当然具体参数设置需要在效率和时效性方面做一个权衡。在比较新的版本中还有batch.size这个参数。

## 2. 题外话：
producers可以一步的并行向kafka发送消息，但是通常producer在发送完消息之后会得到一个响应，返回的是offset值或者发送过程中遇到的错误。这其中有个非常重要的参数“request.required.acks"，这个参数决定了producer要求leader partition收到确认的副本个数:

- 如果acks设置为0，表示producer不会等待broker的相应，所以，producer无法知道消息是否发生成功，这样有可能导致数据丢失，但同时，acks值为0会得到最大的系统吞吐量。
- 若acks设置为1，表示producer会在leader partition收到消息时得到broker的一个确认，这样会有更好的可靠性，因为客户端会等待知道broker确认收到消息。
- 若设置为-1，producer会在所有备份的partition收到消息时得到broker的确认，这个设置可以得到最高的可靠性保证。

## 3. oneway
前面只提到了sync和async，那么oneway是什么呢？
oneway是只顾消息发出去而不管死活，消息可靠性最低，但是低延迟、高吞吐，这种对于某些完全对可靠性没有要求的场景还是适用的，即request.required.acks设置为0。

## 4. 一般配置
- 对于sync的发送方式：
```
  producer.type=sync
  request.required.acks=1
```
- 对于async的发送方式：
```
  producer.type=async
  request.required.acks=1
  queue.buffering.max.ms=5000
  queue.buffering.max.messages=10000
  queue.enqueue.timeout.ms = -1
  batch.num.messages=200
```
- 对于oneway的发送发送：
```
  producer.type=async
  request.required.acks=0
```