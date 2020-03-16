package cau1;

public class MyData {
    public int counter;

    public MyData() {
        this.counter = 0;
    }

    public synchronized void incr() {
        this.counter++;
    }
}