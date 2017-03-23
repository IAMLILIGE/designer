package designer.server.timer;

/**
 * Created by lilige on 17/3/23.
 */
public class TimerImpl extends AbstractTimer {
    static Integer index = 0;

    //实例化线程池
    public TimerImpl() {
        super("AUTO-INCREASE-INDEX", 2, 3);//队列2个,线程池3个 rejectSize = 2 + 3 = 5
    }

    @Override
    void executor() {
        System.out.println(System.currentTimeMillis());
        int rejectSize = 6 ;
        //具体任务 入队列
        //main线程:入队列 + reject
        //和 线程池线程 抢占cpu
        for (int i = 0 ; i < rejectSize ; i ++ ){
            executor.execute(new Runnable() {
                public void run() {
                    //具体线程业务内容
                    try {
                        synchronized (index){//???为什么加了锁还能有一样的数出现
                            index++;
                            System.out.println(Thread.currentThread().getName() + "-----" + index);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println(System.currentTimeMillis());
    }


    public static void main(String[] args) {
        TimerImpl timer = new TimerImpl();
        timer.executor();
    }
}
