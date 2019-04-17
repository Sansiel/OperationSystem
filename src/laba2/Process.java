package laba2;

import java.util.*;

public class Process implements Comparable<Process>{
    //коллекция потоков из потока. Comparable для сравнения
    public ArrayList<Thread> threads;
    private int name;
    private int priority;

    public int getPriority() {
        return priority;
    }

    public Process(int name, int priority, int operations) {
        this.name = name;
        this.threads = new ArrayList<>();
        this.priority = priority;
        //рандомятся не потоки, а время работы. До 14 секунд, от 5.
        Random random = new Random();
        for (int operation = 0; operation < operations; operation++) {
            this.threads.add(new Thread(operation, Math.abs(random.nextInt()) % 10 + 5, this.name));
        }
    }


    public boolean isFinished() {
        //проверка на завершённость. Используется в OperationSystem
        for (Thread thread : this.threads) {
            if (!thread.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public int getTime() {
        //название предрекает суть
        int time = 0;
        for (Thread thread : this.threads) {
            time += thread.getTime();
        }
        return time;
    }

    public void exec(int threadId) {
        Thread thread = this.threads.get(threadId);
        for (int i = 0; i < OperationSystem.QUANT; i++) {
            if (!thread.isFinished()) {
                thread.exec();
                this.printDebug(thread);
            }
        }
//        int aliveThreadsCount = 0;
//        for (Thread thread : this.threads) {
//            if (!thread.isFinished())
//                aliveThreadsCount++;
//        }
//
//        //заканчивает работу при 0 работающих потоков
//        if (aliveThreadsCount == 0) {
//            return;
//        }
//
//        //oneThreadTime для первого отображения в консоле до изменения времени работы
//        int oneThreadTime = OperationSystem.QUANT / aliveThreadsCount;
//        for (Thread thread : this.threads) {
//            if (!thread.isFinished()) {
//                for (int t = 0; t < oneThreadTime; t++) {
//                    thread.exec();
//                    this.printDebug(thread);
//                }
//            }
//        }
//
//        //вывод в консоль каждую residueTime, который от 2 до 0
//        int residueTime = OperationSystem.QUANT % aliveThreadsCount;
//        while (residueTime > 0) {
//            for (Thread thread : this.threads) {
//                if (!thread.isFinished() && residueTime > 0) {
//                    residueTime--;
//                    thread.exec();
//                    this.printDebug(thread);
//                }
//            }
//        }
    }

    private void printDebug(Thread thread) {
        //выводит в консоль инфу
        if (!thread.isFinished()) {
            System.out.printf("Process %4s Priority %4s Thread %4s - Time %d\n", this.name, this.priority, thread.getId(), thread.getTime());
        }
        else {
            System.out.printf("Process %4s Priority %4s Thread %4s - Finished\n", this.name, this.priority, thread.getId());
        }
    }

    @Override
    public int compareTo(Process other) {
        //Вот собственно и сравнение времени.
        return Integer.compare(this.getTime(), other.getTime());
    }
}
