package Thread_ex;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class P03 {
    /*有三个车库，模拟多个用户停车、离开的效果*/
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private BlockingQueue<Thread> blockingQueue =new ArrayBlockingQueue<>(3);

    public void parking() throws InterruptedException {


        try {
            lock.lock();
            while (blockingQueue.size()==3) {
                condition.await();
            }
            blockingQueue.add(Thread.currentThread());
            System.out.println("第" + Thread.currentThread().getName() + "号车停车了");
            System.out.println("已停有"+blockingQueue.size()+"辆车");
            condition.signal();

        } finally {
            lock.unlock();
        }

    }

    public void outing() throws InterruptedException {
        try {
            lock.lock();
            while (blockingQueue.isEmpty()) {
                condition.await();
            }
            Thread peek = blockingQueue.remove();
            System.out.println("第" + peek.getName() + "号车离开了");
            System.out.println("已停有"+blockingQueue.size()+"辆车");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}

class park implements Runnable{

    private P03 p03;

    public park(P03 p03) {
        super();
        this.p03 = p03;
    }

    @Override
    public void run() {
        try {
            p03.parking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class out implements Runnable{
    private P03 p03;

    public out(P03 p03) {
        super();
        this.p03 = p03;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            p03.outing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}