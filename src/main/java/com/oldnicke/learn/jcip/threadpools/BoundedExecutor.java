package com.oldnicke.learn.jcip.threadpools;

import java.util.concurrent.*;

import com.oldnicke.learn.jcip.annotations.ThreadSafe;

/**
 * BoundedExecutor
 * <p/>
 * Using a Semaphore to throttle task submission
 * 使用信号量来控制任务队列的数量，因为线程池使用的默认是无界队列
 * 也可以使用一个计数，或者限制任务队列的长度
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            /**
             * Semaphore 用来限制任务提交的频率
             * 然而还是有可能饱和，导致任务被拒绝！
             */
            semaphore.release();
        }
    }
}
