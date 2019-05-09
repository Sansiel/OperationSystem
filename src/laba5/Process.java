package laba5;

import java.util.Random;

public class Process {
    private int id;
    private int executingTime;
    private int remainingTime;
    private Random rnd;
    private int quantum;

    public Process(int id, int executingTime, Random rnd) {
        this.id = id;
        this.executingTime = executingTime;
        this.rnd = rnd;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getId() {
        return id;
    }

    public int getExecutingTime() {
        return executingTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void exec() throws IOInterrupt {
        if (rnd.nextInt(15) < 1) throw new IOInterrupt();
        remainingTime -= 1;
        quantum -= 1;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
