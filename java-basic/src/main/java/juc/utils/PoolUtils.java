package juc.utils;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hike97
 * @Description 生成线程池工具类
 * @create 2020-09-24 10:40
 * @Modified By:
 **/
public class PoolUtils {
    private static ThreadFactoryBuilder builder = new ThreadFactoryBuilder ();
    private static final String POOLNAME = "pool-%d";

    public static ThreadPoolExecutor getPool (int coreSize, int maxSize, String poolName) {
        if (poolName == null) {
            poolName = POOLNAME;
        }
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor (coreSize, maxSize, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<> (1024), builder.setNameFormat (poolName).build (), new ThreadPoolExecutor.AbortPolicy ());
        return poolExecutor;
    }
}
