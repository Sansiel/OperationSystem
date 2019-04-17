package laba3;

import java.util.ArrayList;

public class Core {

    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<Process>();
        int k = (int) (Math.random() * 20) + 5;
        for (int i = 0; i < k; i++) {
            processes.add(new Process());
            int n = i + 1;
            processes.get(i).setName(n + "");
        }
        Scheduler s = new Scheduler(processes);
        System.out.println(s.makePlan());
    }

}
