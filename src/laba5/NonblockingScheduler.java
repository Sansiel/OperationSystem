package laba5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NonblockingScheduler implements Scheduler {

    private ArrayDeque<Process> processes;
    private int counter;
    private int previousPrintCounter;
    public static int QUANTUM = 10;

    private Random rnd;

    public NonblockingScheduler(ArrayList<Process> processes) {
        processes.forEach((p) -> {
            p.setRemainingTime(p.getExecutingTime());
            p.setQuantum(QUANTUM);
        });
        this.processes = new ArrayDeque<>(processes);
        rnd = new Random();
        counter = 0;
        previousPrintCounter = 1;
    }

    public int getCounter() {
        return counter;
    }

    public void schedule() {
        Process curP = processes.removeFirst();
        IoOperation curIO;
        while (curP != null) {
            try {
                printLog("exec process " + curP.getId() + ", remaining time: " + curP.getRemainingTime() + " ms, quantum: " + curP.getQuantum() + " ms");
                curP.exec();

            } catch (IOInterrupt ioInterrupt) {
                printLog("process " + curP + " request IO operation");

                curIO = new IoOperation(rnd.nextInt(10) + 5, curP.getId());

                while(!curIO.isFinished())
                    printLog("IO operation for process " + curIO.getProcessId() + " takes " + curIO.getTimeLeft() + " ms");
                printLog("IO operation for process " + curIO.getProcessId() + " is finished");
            }
            if (curP.getQuantum() == 0 && curP.getRemainingTime() > 0) {
                curP.setQuantum(QUANTUM);
                processes.offerLast(curP);
                curP = processes.pollFirst();
            } else if (curP.getRemainingTime() == 0) {
                printLog("process " + curP + " is finished");
                curP = processes.pollFirst();
            }
            counter++;
        }
    }

    private void printLog(String s){
        if (previousPrintCounter != counter){
            System.out.printf("%4d: %s\n", counter, s);
            previousPrintCounter = counter;
        } else {
            System.out.printf("      %s\n", s);
        }
    }
}
