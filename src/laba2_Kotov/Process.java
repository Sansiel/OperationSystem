package laba2_Kotov;

import java.util.*;

public class Process implements Comparable<Process> {
    private ArrayList<Thread> threads;
    private int name;
    private int priority;

    public Process(int name, int priority, int operations) {
        this.name = name;
        this.priority = priority;
        this.threads = new ArrayList<>();
        Random random = new Random();
        for (int operation = 0; operation < operations; operation++) {
            this.threads.add(new Thread(operation, Math.abs(random.nextInt()) % 10 + 5));
        }
    }

    public boolean isFinished() {
        for (Thread thread : this.threads) {
            if (!thread.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public int getTime() {
        int time = 0;
        for (Thread thread : this.threads) {
            time += thread.getTime();
        }
        return time;
    }

    public void exec() {
        int quant = OperationSystem.QUANT * this.priority;
        int aliveThreadsCount = 0;
        for (Thread thread : this.threads) {
            if (!thread.isFinished())
                aliveThreadsCount++;
        }

        if (aliveThreadsCount == 0) {
            return;
        }

        int oneThreadTime = quant / aliveThreadsCount;
        for (Thread thread : this.threads) {
            for (int t = 0; t < oneThreadTime; t++) {
                if (thread.isFinished()) break;
                thread.exec();
                quant--;
                this.printDebug(thread);
            }
        }

        while (quant > 0 && !this.isFinished()) {
            for (Thread thread : this.threads) {
                if (thread.isFinished() || quant == 0) continue;
                thread.exec();
                quant--;
                this.printDebug(thread);
            }
        }
    }

    private void printDebug(Thread thread) {
        if (!thread.isFinished()) {
            System.out.println(
                    "Process: " + this.name +
                            "    Priority: " + this.priority +
                            "    Thread: " + thread.getId() +
                            "    Time: " + thread.getTime());
        } else {
            System.out.println(
                    "Process: " + this.name +
                            "    Priority: " + this.priority +
                            "    Thread: " + thread.getId() +
                            "    Finished");
        }
    }

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.getTime(), other.getTime());
    }
}

