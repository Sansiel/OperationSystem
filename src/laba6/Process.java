package laba6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class Process {
    private int id;
    private int executingTime;
    private int remainingTime;
    private Random rnd;
    private int quantum;
    public ArrayList<Resource> borrowed;
    public Resource requested;
    public int requestedResourceUsageTime;
    public ArrayList<Resource> allResources;

    public Process(int id, int executingTime, Random rnd, ArrayList<Resource> allResources) {
        this.id = id;
        this.executingTime = executingTime;
        this.rnd = rnd;
        this.allResources = allResources;
        borrowed = new ArrayList<>();
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

    public void exec() throws ResourceRequest {
        if (remainingTime > 1 && rnd.nextInt(5) < 1)
        {
            ArrayList<Resource> notBorrowedResources = (ArrayList<Resource>) allResources.stream()
                    .filter(e -> e.getBorrowingProcess() == null || e.getBorrowingProcess().getId() != id)
                    .collect(Collectors.toList());
            if (notBorrowedResources.size() > 0) {
                requested = notBorrowedResources.get(rnd.nextInt(notBorrowedResources.size()));
                requestedResourceUsageTime = rnd.nextInt(remainingTime - 1) + 1;
                throw new ResourceRequest();
            }
        }
        remainingTime -= 1;
        quantum -= 1;
        for (int i = borrowed.size() - 1; i >= 0; i--) {
            Resource r = borrowed.get(i);
            if (r.isFinished()) {
                r.setBorrowed(false);
                r.setBorrowingProcess(null);
                borrowed.remove(r);
            }
        }
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
