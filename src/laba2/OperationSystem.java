package laba2;

import java.util.*;

public class OperationSystem {
    public static final int QUANT = 5;
    public static final int PRIORITY_COUNT = 3;
    //двунаправленная очередь
//    private ArrayDeque<Process> processes;
    private ArrayList<Process> processes;
    private ArrayList<ArrayDeque<Thread>> threadsDeques;

    public OperationSystem() {
        //Генерирование случайного кол-ва процессов от 1 до 5 в коллекцию (отображаются они с нуля)
        Random random = new Random();
        processes = new ArrayList<>();
        int processesCount = Math.abs(random.nextInt()) % 5 + 1;
        for (int i = 0; i < processesCount; i++) {
            //потоки от 1 до 10 (отображатся будут с 0)
            processes.add(new Process(i, Math.abs(random.nextInt()) % 3,Math.abs(random.nextInt()) % 10 + 1));
        }
//        Collections.sort(processesArrayList);
        ArrayList<ArrayList<Thread>> threads = new ArrayList<>();
        for (int i = 0; i < PRIORITY_COUNT; i++) {
            threads.add(new ArrayList<Thread>());
        }

        for (Process proc : this.processes) {
            threads.get(proc.getPriority()).addAll(proc.threads);
        }
        for (ArrayList<Thread> thread : threads) {
            Collections.sort(thread);
        }

        threadsDeques = new ArrayList<>();
        for (int i = 0; i < PRIORITY_COUNT; i++) {
            threadsDeques.add(new ArrayDeque<>());
            threadsDeques.get(i).addAll(threads.get(i));
        }

    }
    public void start() {
        for (ArrayDeque<Thread> threadsDeque : threadsDeques) {
            while (!threadsDeque.isEmpty()) {
                Thread t = threadsDeque.poll();
                Process process = this.processes.get(t.getProcId());
                //эта верхушка запускается
                process.exec(t.getId());
                //Если не завершенно, то в конец очереди
                if (!process.isFinished()) {
                    threadsDeque.add(t);
                }
            }
        }
    }
}
