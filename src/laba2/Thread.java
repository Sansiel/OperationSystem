package laba2;


public class Thread implements Comparable<Thread>{
    private int id;
    private int time;
    private int procId;

    public Thread(int id, int time, int procId) {
        this.id = id;
        this.time = time;
        this.procId = procId;
    }

    public int getProcId() {
        return procId;
    }

    public int getId() {
        //получение Id из объекта
        return id;
    }

    public int getTime() {
        //получение Time из объекта
        return time;
    }

    public boolean isFinished() {
        //получение T/F на законченность из объекта
        return this.time <= 0;
    }

    public void exec() {
        //получение команды на отключение из объекта
        if (this.time > 0) {
            this.time--;
        }
    }

    @Override
    public int compareTo(Thread o) {
        return Integer.compare(this.getTime(), o.getTime());
    }
}

