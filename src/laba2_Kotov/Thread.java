package laba2_Kotov;

public class Thread {
    private int id;
    private int time;

    public Thread(int id, int time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public boolean isFinished() {
        return this.time <= 0;
    }

    public void exec() {
        if (this.time > 0) {
            this.time--;
        }
    }
}
