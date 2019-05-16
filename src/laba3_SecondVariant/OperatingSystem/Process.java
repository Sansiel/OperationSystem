package laba3_SecondVariant.OperatingSystem;

import java.util.ArrayList;

public class Process {

    private ArrayList<Thread> threads;
    private ArrayList<Integer> pageNumbers = new ArrayList<>();

    private String name;

    private int priority;

    public Process() {
        int k = (int) (Math.random() * 4) + 1;
        threads = new ArrayList<Thread>();
        for (int i = 0; i < k; i++) {
            threads.add(new Thread());
            int n = i + 1;
            threads.get(i).setName(n + "");
        }
        k = (int) (Math.random() * 1000);
        if (k < 250) {
            priority = 1;
        } else if (k < 500) {
            priority = 2;
        } else if (k < 750) {
            priority = 3;
        } else {
            priority = 4;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> t) {
        threads = t;
    }

    public int getPriority() {
        return priority;
    }

    public ArrayList<Integer> getPageNumbers() {
        return pageNumbers;
    }
}