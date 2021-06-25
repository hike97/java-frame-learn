1.blcokingqueue 天生支持生产者消费者模式 put 和 take 方法 体现阻塞
2.Vector Hashtable 自带锁 基本不用
3.Hashtable->cuncurrenthashmap
4.vector->queue->blockingqueue
    queue 和 list 的 区别
    对线程友好的API offer peek poll
    blocking queue put take  阻塞 完美的生产者消费者模型

/**
 * 抛异常
 * 	add/remove
 * 非阻塞
 * 	offer/poll
 * 阻塞
 * 	put/take
 * @param argv
 */
 1.说说SynchronousQueue和LinkedTransferQueue的区别？
 原理： SynchronousQueue 基于DualQueue DualStack 支持不公平的
       LinkedTransferQueue 基于DualQueue  offer->LinkedBlockingQueue tryTransfer->DualQueue 只支持公平
       两者都是基于双向匹配最后removed的机制 实现生产者和消费者模型