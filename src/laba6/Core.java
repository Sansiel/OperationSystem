package laba6;

import java.util.ArrayList;
import java.util.Random;

public class Core {

    private static Random rnd = new Random();

    public static void main(String[] args) {
        ArrayList<Resource> resources = new ArrayList<>();
        int resourcesCount = rnd.nextInt(4) + 3;
        for (int i = 0; i < resourcesCount; i++) {
            Resource r = new Resource(i);
            resources.add(r);
            System.out.printf("Resource: %d\n", r.getId());
        }

        ArrayList<Process> processes = new ArrayList<>();
        int processCount = rnd.nextInt(4) + 3;
        for (int i = 0; i < processCount; i++) {
            Process p = new Process(i, rnd.nextInt(20) + 10, rnd, resources);
            processes.add(p);
            System.out.printf("Process: %d, executing time: %d\n", p.getId(), p.getExecutingTime());
        }

        Scheduler s = new Scheduler(resources, processes);
        s.schedule();
    }
}
