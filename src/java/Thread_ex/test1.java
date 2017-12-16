package Thread_ex;

public class test1 {

    public static void main(String[] ages) throws InterruptedException {
        System.out.println("开始练习");
        Thread thread = new Thread(()-> System.out.println("OK"));
        thread.start();
        Thread.sleep(100);
        System.out.println(thread.getName());
    }
}
