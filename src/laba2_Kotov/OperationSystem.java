package laba2_Kotov;

import java.util.*;

public class OperationSystem {
    public static final int QUANT = 5;
    public static final int PRIORITY_COUNT = 3;

    private ArrayDeque<Process> processes;

    public OperationSystem() {
        this.processes = new ArrayDeque<>();

        Random random = new Random();
        ArrayList<Process> processesArrayList = new ArrayList<>();
        int processesCount = Math.abs(random.nextInt()) % 5 + 1;
        for (int i = 0; i < processesCount; i++) {
            processesArrayList.add(new Process(i, Math.abs(random.nextInt()) % 4 + 1,Math.abs(random.nextInt()) % 10 + 1));
        }
        Collections.sort(processesArrayList);

        this.processes.addAll(processesArrayList);
    }

    public void start() {
        while (!processes.isEmpty()) {
            Process process = processes.poll();
            process.exec();
            if (!process.isFinished()) {
                processes.add(process);
            }
        }
    }
}
