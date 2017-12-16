package Thread_ex;


import java.util.concurrent.locks.ReentrantLock;

public class P01 {
    /*创建两个线程，其中一个输出1-52，另外一个输出A-Z。输出格式要求：12A 34B 56C 78D*/
    public static void po1(){
        Object lock = new Object();
        Thread t1 = new Thread(new out1(lock));
        Thread t2 = new Thread(new out2(lock));
        t1.start();
        t2.start();

    }

}

class out1 implements Runnable{

    private Object lock;

    public out1(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        for (int i = 1; i < 52; i += 2) {
//            对Lock对象上锁
            synchronized (lock) {
                System.out.print(i);
                System.out.print(i + 1);
                try {
//                    先释放等待的线程 再进入等待状态
                    lock.notify();
                    lock.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

class out2 implements Runnable{

    private Object lock;

    public out2(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};



        for (int i=0;i<chars.length;i++) {

            synchronized (lock) {
                System.out.println(chars[i]);

                lock.notify();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
