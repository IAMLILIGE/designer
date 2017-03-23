package designer.server.timer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lilige on 17/3/23.
 */
public abstract class AbstractTimer {

    //线程池满时 线程池拒绝任务时的 处理策略
    protected RejectedExecutionHandler handler;

    //线程池
    protected ThreadPoolExecutor executor;

    //线程任务 的 队列(阻塞)
    protected BlockingQueue<Runnable> taskQueue;

    public AbstractTimer(final String jobName, int blockingQueueSize , int threadSize){
        //初始化队列
        taskQueue = new ArrayBlockingQueue<Runnable>(blockingQueueSize);

        //队列满时的失败策略
        handler = new RejectedExecutionHandler() {

            //失败手动重新入队,尝试两次
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("blocking queue is full..." + jobName);
                try {
                    Thread.currentThread().sleep(5000l);
                    taskQueue.add(r);
                    System.out.println("put in queue again..." + jobName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        executor = new ThreadPoolExecutor(threadSize , threadSize, 2L , TimeUnit.SECONDS , taskQueue ,handler);
    }

    //具体任务
    abstract void executor();


}
