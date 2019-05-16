package laba3_SecondVariant;

import laba3_SecondVariant.OperatingSystem.Process;
import laba3_SecondVariant.OperatingSystem.MemoryManager.MemoryManager;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<Process>();
        int k = (int) (Math.random() * 20) + 5;
        for (int i = 0; i < k; i++) {
            processes.add(new Process());
            int n = i + 1;
            processes.get(i).setName(n + "");
        }
        MemoryManager s = new MemoryManager(processes);
        System.out.println(s.makePlan());
    }
}