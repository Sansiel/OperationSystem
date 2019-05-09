package laba6;

public class Resource {
    private int id;
    private int timeLeft;
    private Process borrowingProcess;
    private boolean borrowed = false;

    public Resource(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Process getBorrowingProcess() {
        return borrowingProcess;
    }

    public void setBorrowingProcess(Process borrowingProcess) {
        this.borrowingProcess = borrowingProcess;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public boolean isFinished() {
        return --timeLeft == 0;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
