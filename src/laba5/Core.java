package laba5;

import java.util.ArrayList;
import java.util.Random;

public class Core {

    private static Random rnd = new Random();

    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        int processCount = rnd.nextInt(4) + 3;
        for (int i = 0; i < processCount; i++) {
            processes.add(new Process(i, rnd.nextInt(20) + 10, rnd));
        }

        BlockingScheduler bs = new BlockingScheduler(processes);
        bs.schedule();
        int bsTime = bs.getCounter();

//        NonblockingScheduler ns = new NonblockingScheduler(processes);
//        ns.schedule();
//        int nsTime = ns.getCounter();
    }
}
