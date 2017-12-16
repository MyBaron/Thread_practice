# Thread_practice
多线程练习题目代码

## PO1 创建两个线程，其中一个输出1-52，另外一个输出A-Z。输出格式要求：12A 34B 56C 78D
### 心得
- 利用synchronized锁住一个共同对象。然后利用wait(),notify()对线程进行控制。

## P02 使用三个线程使得ABC 循环输出十次

### 心得
- 利用Condition 来控制对哪个类型的锁进行释放。可以达到对3个线程的一个控制。
- 对于线程输出的控制顺序，可以加入一个判断顺序Int.来决定是否轮到该线程运行，不是则继续等待。

## P03 有三个车库，模拟多个用户停车、离开的效果 
### 心得
- 利用上消费者/生产者原理
- 用上BlockingQueue 阻塞队列来进行停车和离开消息的通讯。
- 在单元测试的时候，如果单元测试的线程结束了，子线程也会跟着结束。

## P04 有四个线程1、2、3、4。线程1的功能就是输出A，线程2的功能就是输出B，以此类推
现在有四个文件file1,file2,file3, file4。初始都为空。
    现要让四个文件呈如下格式：
    file1：A B C D A B....
    file2：B C D A B C....
    file3：C D A B C D....
    file4：D A B C D A....

### 心得
- 虽然是4个线程，但是还是不能做到对4个文件同时进行读写。感觉以后可以继续优化
- 在对同步局域用Lock 上锁的时候会出现``java.lang.IllegalMonitorStateException`` 的问题，网上查了一下。有一个说法是
> 在对某个对象上调用wait()方法进行线程等待（让其他竞争执行该代码的线程上锁）时，没有对该对象执行同步操作。
所以解决方法是：
```
synchronized (xxxx)  {
     xxxx.wait();
   }
```
## P05
> 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10,
>    然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....
>    以此类推, 直到打印到75

### 心得
- 没什么难度，注意线程切换
    


