package Thread_ex;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class P05 {
    /*启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10,
    然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....
    以此类推, 直到打印到75.*/

    private static int count=0;
    private static int ThreadNumber=1;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();


    public static void p05() {

        Runnable r=() -> {
            for(int i=0;i<4;i++) {
                print();
            }
        };
        Thread t = new Thread(r);
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t.setName("1");
        t1.setName("2");
        t2.setName("3");

        t.start();
        t1.start();
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void print() {
        lock.lock();
        Integer integer = Integer.valueOf(Thread.currentThread().getName());
        while (integer != ThreadNumber) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程"+integer+"的输出");
        for(int i =count+1;i<=count+5;i++) {
            System.out.print(i +" ");

        }
        System.out.println("");
        count+=5;
        if (count % 5 == 0) {
            ThreadNumber++;
            if (ThreadNumber > 3) {
                ThreadNumber = 1;
            }
        }
        condition.signal();
        lock.unlock();
    }
}


