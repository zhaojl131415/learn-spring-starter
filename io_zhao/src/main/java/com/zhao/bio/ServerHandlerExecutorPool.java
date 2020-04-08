package com.zhao.bio;

import java.util.concurrent.*;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-04-04 14:34
 */
public class ServerHandlerExecutorPool implements Executor {

    private ExecutorService executorService;

    public ServerHandlerExecutorPool(int maxPoolSize, int queueSize) {
        this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public ServerHandlerExecutorPool(int corePoolSize, int maxPoolSize, int queueSize) {
        this.executorService = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }
}
