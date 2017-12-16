package Thread_ex;

import org.junit.Test;

import static org.junit.Assert.*;

public class P03Test {


    @Test
    public void testP03() throws InterruptedException {
        P03 p03 = new P03();

        //创建10个线程
        out[] outs = new out[100];
        park[] parks = new park[100];
        for(int i=0;i<100;i++) {
            outs[i] = new out(p03);
            parks[i] = new park(p03);
            Thread t = new Thread(outs[i]);
            t.setName(i+1+"");
            Thread tt = new Thread(parks[i]);
            tt.setName(i+1+"");
            t.start();
            tt.start();
            tt.join(); //在单元测试的时候主线程（test）退出，子进程也会跟着退出。所以加入join()，保证子进程运行

        }




    }

}