package Thread_ex;

import com.sun.deploy.util.SyncFileAccess;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class P04 {

/*  有四个线程1、2、3、4。线程1的功能就是输出A，线程2的功能就是输出B，以此类推......... 现在有四个文件file1,file2,file3, file4。初始都为空。
    现要让四个文件呈如下格式：
    file1：A B C D A B....
    file2：B C D A B C....
    file3：C D A B C D....
    file4：D A B C D A....*/

    private static ReentrantLock lock = new ReentrantLock();
    private static int FileNumber=0; //第几个文件
    private static int count=1; //打印的次数
    private static int ThreadNumber=1;//决定轮到第几条线程开始
    public  static File[] files;
    public  static void PO4(){
        files = new File[4];
         for(int i=0;i<4;i++) {
             files[i] = new File("file" + (i + 1)+".txt");
             try {
                 files[i].createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }


        //创建线程
        myprint myprinta = new myprint('A');
        myprint myprintb = new myprint('B');
        myprint myprintc = new myprint('C');
        myprint myprintd = new myprint('D');
        Thread ta = new Thread(myprinta);
        Thread tb = new Thread(myprintb);
        Thread tc = new Thread(myprintc);
        Thread td = new Thread(myprintd);
        ta.setName("1");
        tb.setName("2");
        tc.setName("3");
        td.setName("4");
        ta.start();
        tb.start();
        tc.start();
        td.start();

        try {
            td.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //确定第几个线程可以输出
    //如果换了输出文本，那么就要限制第一个输出的线程
    public static void changeThread(){
        if (count % 8 == 0) {
            ThreadNumber =FileNumber +2;
            if(ThreadNumber>=4){
                ThreadNumber = 4;
            }
        }
    }

    //变更输出文本 当一个文本写入8个的时候就进行切换文本
    public static void changeFile() {
        if(count %8 ==0){
            FileNumber++;
            if(FileNumber>=4){
                FileNumber = 3;
            }
        }

    }

    public static void out(char a) {

        synchronized (lock) {

            Integer integer = Integer.valueOf(Thread.currentThread().getName());

            while (integer != ThreadNumber) {
                try {

                    lock.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            changeFile();
            try {
                FileWriter fileWriter = new FileWriter(files[FileNumber], true);
                System.out.println("将要写入字符 " + a);
                fileWriter.write(a);
                fileWriter.flush();
                fileWriter.close();
                count++;
                //改变线程
                if (integer % 4 == 0) {
                    ThreadNumber = 1;
                } else {
                    ThreadNumber++;
                }
                changeThread();
                lock.notifyAll();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}

class myprint implements Runnable{

    private char a;

    public myprint(char a) {
        this.a = a;
    }

    @Override
    public void run() {
        for(int i=0;i<8;i++) {
            System.out.println(a+"  "+i);
            P04.out(a);
        }
    }
}
