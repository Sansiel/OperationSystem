package laba5;

public class IoOperation {
    private int timeLeft;
    private int processId;

    public IoOperation(int timeLeft, int processId) {
        this.timeLeft = timeLeft;
        this.processId = processId;
    }

    public int getProcessId() {
        return processId;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public boolean isFinished() {
        return --timeLeft == 0;
    }
}
