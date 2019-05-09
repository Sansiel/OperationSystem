package laba5;

import java.util.ArrayList;
import java.util.Random;

public class Core {

    private static Random rnd = new Random();

    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        int processCount = rnd.nextInt(4) + 3;
        for (int i = 0; i < processCount; i++) {
            Process p = new Process(i, rnd.nextInt(20) + 10, rnd);
            processes.add(p);
            System.out.printf("Process: %d, executing time: %d\n", p.getId(), p.getExecutingTime());
        }

        System.out.println("\nBlockingScheduler:");
        BlockingScheduler bs = new BlockingScheduler(processes);
        bs.schedule();
        int bsTime = bs.getCounter();

        System.out.println("\nNonblockingScheduler:");
        NonblockingScheduler ns = new NonblockingScheduler(processes);
        ns.schedule();
        int nsTime = ns.getCounter();

        System.out.println("\nBlockingScheduler's total time:" + bsTime);
        System.out.println("NonblockingScheduler's total time:" + nsTime);
    }
}
