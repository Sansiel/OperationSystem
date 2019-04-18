package laba3_SecondVariant.OperatingSystem;


import laba3_SecondVariant.OperatingSystem.MemoryManagement.MemoryManagementUnit;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OperatingSystem {
    public static Memory memory = null;
    public static Swap swap = null;

    private MemoryManagementUnit MMU;
    private ArrayList<Process> processes;

    public OperatingSystem(int memorySize, int pageSize) {
        this.memory = new Memory(memorySize, pageSize);
        this.swap = new Swap();
        this.MMU = new MemoryManagementUnit();
        this.processes = new ArrayList<>();
    }

    public void addProcess() {
        Process process = new Process(this.processes.size());
        this.processes.add(process);
        System.out.printf("Create new process #%d\n", process.getId());
    }

    public Process getProcess(int processId) {
        for (Process process : this.processes) {
            if (process.getId() == processId) {
                return process;
            }
        }
        return null;
    }

    public void printProcess(int processId) {
        Process process = this.getProcess(processId);
        if (process != null) {
            String pages = process.getPagesIds().stream().map(Object::toString).collect(Collectors.joining(" "));
            System.out.printf("Process #%3d Pages: %s\n", process.getId(), pages);
        }
    }

    public void printProcesses() {
        System.out.print("Processes:\n");
        System.out.print("     PID    PAGES:\n");
        for (Process process : this.processes) {
            String pages = process.getPagesIds().stream().map(Object::toString).collect(Collectors.joining(" "));
            System.out.printf("    %4d    %s\n", process.getId(), pages);
        }
    }


    public void addPage(int processId) {
        Process process = this.getProcess(processId);
        if (process != null) {
            int pageId = this.MMU.addPage(process);
            System.out.printf("Create new page #%d for process #%d\n", pageId, process.getId());
        }
    }

    public void getPage(int processId, int pageId) {
        Process process = this.getProcess(processId);
        if (process != null) {
            if (process.getPagesIds().contains(pageId)) {
                this.MMU.getPage(pageId);
                System.out.printf("Process #%d request page #%d\n", process.getId(), pageId);
            }
            else {
                System.out.printf("The process #%d does not have a page #%d\n", process.getId(), pageId);
            }
        }
    }

    public void printMemory() {
        System.out.print("Memory:\n");
        System.out.print("    PAGE    PROCESS    RECOURSE\n");
        for (int pageId = 0; pageId < this.memory.getPagesCount(); pageId++) {
            Page page = this.memory.getPage(pageId);
            if (page == null) {
                System.out.printf("    %4d\n", pageId);
            }
            else {
                Process process = this.getProcess(page.getProcessId());
                System.out.printf("    %4d    %4d       %s\n", pageId, process.getId(), page.isRecourse());
            }
        }
    }
}

