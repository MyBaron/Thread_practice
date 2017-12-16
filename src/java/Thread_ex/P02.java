package Thread_ex;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class P02 {
    private static ReentrantLock lock;
    public static int ThSum=1;


    /*使用三个线程使得ABC 循环输出十次*/
    public static void po2(){
        lock = new ReentrantLock(true);

        Thread t1 = new Thread(new t1(lock));
        Thread t2 = new Thread(new t2(lock));
        Thread t3 = new Thread(new t3(lock));
        t1.start();
        t2.start();
        t3.start();
    }


}

class t1 implements Runnable{

    private ReentrantLock lock;
    public static Condition conditionA;

    public t1(ReentrantLock lock) {
        this.lock = lock;
        conditionA = lock.newCondition();
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                lock.lock();
                while (P02.ThSum != 1) {    //如果没有该判断顺序，则线程1 可能会再次获得锁。不能确保顺序正确
                    conditionA.await();    //当还不是该线程输出的时候，释放锁
                }
                System.out.print("第"+i+"次");
                System.out.print('A');
                P02.ThSum = 2;
                t2.conditionB.signalAll(); //释放第二个线程
                lock.unlock();             //释放锁，是因为最后一次的时候，如果没有释放锁，那么2 3 线程并不能获取到锁。
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class t2 implements Runnable{

    private ReentrantLock lock;
    public static Condition conditionB;


    public t2(ReentrantLock lock) {
        this.lock = lock;
        conditionB = lock.newCondition();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                while (P02.ThSum != 2) {
                    conditionB.await();
                }
                System.out.print('B');
                P02.ThSum = 3;
                t3.conditionC.signalAll();
                lock.unlock();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class t3 implements Runnable{

    private ReentrantLock lock;
    public static Condition conditionC;

    public t3(ReentrantLock lock) {
        this.lock = lock;
        conditionC = lock.newCondition();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                while (P02.ThSum != 3) {
                    conditionC.await();
                }
                System.out.println('C');
                P02.ThSum = 1;
                t1.conditionA.signalAll();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}