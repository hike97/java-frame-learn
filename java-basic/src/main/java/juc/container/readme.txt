1.blcokingqueue 天生支持生产者消费者模式 put 和 take 方法 体现阻塞
2.Vector Hashtable 自带锁 基本不用
3.Hashtable->cuncurrenthashmap
4.vector->queue->blockingqueue
    queue 和 list 的 区别
    对线程友好的API offer peek poll
    blocking queue put take  阻塞 完美的生产者消费者模型