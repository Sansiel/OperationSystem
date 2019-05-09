package laba5;

import java.util.*;

public class BlockingScheduler implements Scheduler {

    private ArrayDeque<Process> processList;
    private ArrayDeque<IoOperation> ioOperationsList;
    private HashMap<Integer, Process> blocked;
    private int counter;
    private int previousPrintCounter;
    public static int QUANTUM = 10;

    private Random rnd;

    public BlockingScheduler(ArrayList<Process> processList) {
        processList.forEach((p) -> {
            p.setRemainingTime(p.getExecutingTime());
            p.setQuantum(QUANTUM);
        });
        this.processList = new ArrayDeque<>(processList);
        ioOperationsList = new ArrayDeque<>();
        blocked = new HashMap<>();
        rnd = new Random();
        counter = 0;
        previousPrintCounter = 1;
    }

    public int getCounter() {
        return counter;
    }

    public void schedule() {
        Process curP = processList.removeFirst();
        IoOperation curIO = null;
        while (!processList.isEmpty() || !blocked.isEmpty() || curP != null) {
            if (curP != null) {
                try {
                    printLog("exec process " + curP.getId() + ", remaining time: " + curP.getRemainingTime() + " ms, quantum: " + curP.getQuantum() + " ms");
                    curP.exec();

                    if (curP.getQuantum() == 0 && curP.getRemainingTime() > 0) {
                        curP.setQuantum(QUANTUM);
                        processList.offerLast(curP);
                        curP = processList.pollFirst();
                    } else if (curP.getRemainingTime() == 0) {
                        printLog("process " + curP + " is finished");
                        curP = processList.pollFirst();
                    }

                } catch (IOInterrupt ioInterrupt) {
                    printLog("process " + curP + " request IO operation");

                    ioOperationsList.offerLast(new IoOperation(rnd.nextInt(10) + 5, curP.getId()));

                    blocked.put(curP.getId(), curP);
                    printLog("process " + curP + " is blocked");

                    curP = processList.pollFirst();
                    printLog("change current process to " + curP);
                }


            }

            if (curIO == null && !ioOperationsList.isEmpty()) {
                curIO = ioOperationsList.pollFirst();
            }

            if (curIO != null) {
                printLog("IO operation for process " + curIO.getProcessId() + " takes " + curIO.getTimeLeft() + " ms");
                if (curIO.isFinished()) {
                    printLog("IO operation for process " + curIO.getProcessId() + " is finished");
                    processList.offerFirst(blocked.remove(curIO.getProcessId()));
                    if (curP == null) curP = processList.pollFirst();
                    curIO = ioOperationsList.pollFirst();
                }
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
