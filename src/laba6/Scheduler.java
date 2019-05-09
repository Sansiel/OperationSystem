package laba6;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Scheduler {

    private ArrayDeque<Process> processList;
    private ArrayList<Process> allProcessList;
    private ArrayList<Resource> resourcesList;
    private ArrayList<ArrayDeque<Process>> blocked;
    private int counter;
    private int previousPrintCounter;
    public static int QUANTUM = 10;

    private Random rnd;

    public Scheduler(ArrayList<Resource> resourcesList, ArrayList<Process> processList) {
        processList.forEach((p) -> {
            p.setRemainingTime(p.getExecutingTime());
            p.setQuantum(QUANTUM);
        });
        this.processList = new ArrayDeque<>(processList);
        this.allProcessList = processList;
        this.resourcesList = resourcesList;
        blocked = new ArrayList<>();
        for (int i = 0; i < resourcesList.size(); i++) {
            blocked.add(new ArrayDeque<>());
        }
        rnd = new Random();
        counter = 0;
        previousPrintCounter = 1;
    }

    public int getCounter() {
        return counter;
    }

    public void schedule() {
        Process curP = processList.removeFirst();
        while (!processList.isEmpty() || blocked.stream().anyMatch(e -> !e.isEmpty()) || curP != null) {

            if (curP == null) break; // Something went wrong

            try {
                printLog("Process " + curP +
                        ", time left: " + curP.getRemainingTime() +
                        " ms, quantum: " + curP.getQuantum() + " ms");
                curP.exec();

                FreeResourcesCheck();

                if (curP.getQuantum() == 0 && curP.getRemainingTime() > 0) {

                    curP.setQuantum(QUANTUM);
                    processList.offerLast(curP);
                    curP = processList.pollFirst();
                    printLog("Process changed to " + curP);

                } else if (curP.getRemainingTime() == 0) {

                    printLog("Process " + curP + " is finished");
                    curP = processList.pollFirst();

                }

            } catch (ResourceRequest resourceRequest) {
                if (curP.requested.isBorrowed()) {
                    if (DeadLockCheck(curP, curP.requested)) {
                        printLog("Process " + curP +
                                ": Denied access to resource " + curP.requested);
                        for (Process p : allProcessList) {
                            if (p.borrowed.size() == 0) {
                                printLog("Process " + p + " have no borrowed resources");

                            } else {
                                printLog("Process " + p + " borrowing resources: " +
                                        String.join(", ", p.borrowed.stream()
                                                .map(r -> r.toString() + " for " + r.getTimeLeft() + " ms")
                                                .collect(Collectors.toList())
                                        )
                                );
                            }
                            if (p.requested != null) printLog("  and requested " + p.requested);
                        }
                    } else {
                        blocked.get(curP.requested.getId()).offerLast(curP);
                        printLog("Process " + curP + ": Blocked");
                        curP = processList.pollFirst();
                    }
                } else {
                    borrow(curP, curP.requested);
                }
            }

            counter++;
        }
    }

    private void borrow(Process p, Resource r) {
        printLog("Process " + p +
                " borrowing resource " + r +
                " for " + p.requestedResourceUsageTime + " ms");
        r.setBorrowingProcess(p);
        r.setBorrowed(true);
        r.setTimeLeft(p.requestedResourceUsageTime);
        p.requested = null;
        p.borrowed.add(r);
    }

    private void FreeResourcesCheck() {
        for (int i = 0; i < resourcesList.size(); i++) {
            Resource r = resourcesList.get(i);
            Process p;
            if (!r.isBorrowed() && (p = blocked.get(i).pollFirst()) != null) {
                borrow(p, r);
                processList.offerFirst(p);
            }
        }
    }

    private boolean DeadLockCheck(Process head, Resource tail) {
        if (tail == null || tail.getBorrowingProcess() == null) { return false; }
        else if (head == tail.getBorrowingProcess()) { return true; }
        else { return DeadLockCheck(head, tail.getBorrowingProcess().requested); }
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
