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
    public HashSet<Resource> borrowed;
    public Resource requested;
    public int requestedResourceUsageTime;
    public ArrayList<Resource> allResources;

    public Process(int id, int executingTime, Random rnd, ArrayList<Resource> allResources) {
        this.id = id;
        this.executingTime = executingTime;
        this.rnd = rnd;
        this.allResources = allResources;
        borrowed = new HashSet<>();
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
        if (remainingTime > 1 && rnd.nextInt(15) < 1)
        {
            ArrayList<Resource> notBorrowedResources = (ArrayList<Resource>) allResources.stream()
                    .filter(e -> e.getBorrowingProcess().getId() != id)
                    .collect(Collectors.toList());
            requested = notBorrowedResources.get(rnd.nextInt(notBorrowedResources.size()));
            requestedResourceUsageTime = rnd.nextInt(remainingTime - 1) + 1;
            throw new ResourceRequest();
        }
        remainingTime -= 1;
        quantum -= 1;
        borrowed.forEach(e -> {
            if (e.isFinished()) {
                borrowed.remove(e);
                e.setBorrowed(false);
                e.setBorrowingProcess(null);
            }
        });
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
