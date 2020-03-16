package cau1;

public class MyThread extends Thread {

    private MyData data;

    public MyThread(MyData data) {
        this.data = data;
    }

    public void run() {
        for (int i = 0 ;i < 1000000 ; i++) {
            data.incr();
        }
    }
}