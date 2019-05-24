package laba3_SecondVariant.OperatingSystem.MemoryManager;

import laba3_SecondVariant.OperatingSystem.*;
import laba3_SecondVariant.OperatingSystem.Process;
import laba3_SecondVariant.OperatingSystem.Thread;

import java.util.ArrayList;
import java.util.Random;

public class MemoryManager {

    private ArrayList<Process> processes;
    private TablePage tablePage = new TablePage();
    private Swap swap = new Swap();
    PhysMemory physMemory = new PhysMemory();

    private static Random rnd = new Random();

    public MemoryManager(ArrayList<Process> p) {
        processes = p;
        for (int i = 0; i < processes.size(); i++) {
            for (int j = 0; j < physMemory.getArrayPage().length; j++) {
                processes.get(i).getPageNumbers()
                        .add(tablePage.addRecord(new TablePageRecord(processes.get(i).getName())));
            }
        }
    }

    public String makePlan() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (processes.size() > 0) {
            sb.append("Процесс №");
            sb.append(processes.get(i).getName());
            sb.append(", приритет ");
            sb.append(processes.get(i).getPriority());
            sb.append(", потоков ");
            sb.append(processes.get(i).getThreads().size());
            sb.append("\n");
            sb.append(
                    makePlanThreads(processes.get(i).getThreads(), getTimeProcess(processes.get(i)), processes.get(i)));
            if (processes.get(i).getThreads().size() > 0) {
                sb.append("Oсталось потоков ");
                sb.append(processes.get(i).getThreads().size());
                sb.append("\n");
                i++;
            } else {
                sb.append("Процесс завершен\n");
                processes.remove(i);
            }
            if (i >= processes.size()) {
                i = 0;
            }
            nullFlags();
            sb.append("\n");
        }
        return sb.toString();
    }

    private String makePlanThreads(ArrayList<Thread> threads, int timeProcess, Process process) {
        int timeP = timeProcess;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int timeT = timeP / threads.size();
        while (threads.size() > 0 && timeP > 0) {
            timeP -= timeT;
            if (threads.get(i).getOstTime() > timeT) {
                threads.get(i).setOstTime(threads.get(i).getOstTime() - timeT);
                sb.append("Поток №");
                sb.append(threads.get(i).getName());
                sb.append(", осталось времени ");
                sb.append(threads.get(i).getOstTime());
                sb.append(", время ");
                sb.append(threads.get(i).getTime());
                sb.append("\n");
                sb.append(addNewPage(process));
                i++;
            } else {
                sb.append("Поток №");
                sb.append(threads.get(i).getName());
                sb.append(", поток завершен");
                sb.append(", время ");
                sb.append(threads.get(i).getTime());
                sb.append("\n");
                threads.remove(i);
            }
            if (i >= threads.size()) {
                i = 0;
            }
        }
        return sb.toString();
    }

    private int getTimeProcess(Process pr) {
        int t = 100 + pr.getPriority() * 100;
        return t;
    }

    private String addNewPage(Process pr) {
        StringBuilder sb = new StringBuilder();
        int numNewPage = pr.getPageNumbers().get(rnd.nextInt(pr.getPageNumbers().size()));
        sb.append("Процесс №");
        sb.append(pr.getName());
        sb.append(" запросил ");
        sb.append(numNewPage);
        sb.append(" страницу.\n");
        if (!tablePage.getRecord(numNewPage).isPresence()) {
            addPage(numNewPage);

        } else {
            tablePage.getRecord(numNewPage).setRecours(true);
        }
        currentDiskSpace(sb);
        sb.append("\n");
        return sb.toString();
    }

    private void addPage(int numNewPage){
        int sp = physMemory.getSpacePage();
        if (sp != -1) {
            physMemory.addPage(sp, tablePage.getRecord(numNewPage).getPage());
            tablePage.getRecord(numNewPage).setPresence(true);
            tablePage.getRecord(numNewPage).setIdPhysMemory(sp);
            tablePage.getRecord(numNewPage).setRecours(true);
        } else {
            replace(numNewPage);
        }
        return;
    }

    private void currentDiskSpace(StringBuilder sb){
        sb.append("\nТекущее состояние физической памяти: \n");
        sb.append(swap.writeSwap());
        for (int i = 0; i < physMemory.getArrayPage().length; i++) {
            if (physMemory.getPage(i) != null) {
                sb.append("Страница № ");
                sb.append(physMemory.getPage(i).getId());
                sb.append(": ");
                TablePageRecord tpr = tablePage.getRecord(physMemory.getPage(i).getId());
                sb.append("Бит обращения: ");
                sb.append(tpr.isRecours());
                sb.append(" Процесс: ");
                sb.append(tpr.getProcNumber());
                sb.append(";\n");
            }
        }
    }

    private void nullFlags() {
        for (int i = 0; i < tablePage.getSize(); i++) {
            tablePage.getRecord(i).setRecours(false);
        }
    }

    private int classify(TablePageRecord r) {
        int counter = 0;
        if (r.isModifications()) counter += 1;
        if (r.isRecours()) counter += 2;
        return counter;

    }

    private void replace(int numNewPage) {

        ArrayList<ArrayList<TablePageRecord>> classes = new ArrayList<>();
        for (int i=0;i<4;i++) {classes.add(new ArrayList<TablePageRecord>());}
        for (int i = 0; i < physMemory.getArrayPage().length; i++) {
            TablePageRecord tablePageRecord = tablePage.getRecord(physMemory.getArrayPage()[i].getId());
            classes.get(classify(tablePageRecord)).add(tablePageRecord);
        }
        TablePageRecord tpr = makeTpr(classes, numNewPage);

        tablePage.getRecord(numNewPage).setPresence(true);
        tablePage.getRecord(numNewPage).setIdPhysMemory(tpr.getIdPhisMemory());
        tablePage.getRecord(numNewPage).setRecours(true);
        tpr.setIdSwap(swap.add(tpr.getPage()));
        tpr.setPresence(false);
        tpr.setIdPhysMemory(-1);
    }

    private TablePageRecord makeTpr(ArrayList<ArrayList<TablePageRecord>> classes, int numNewPage){
        TablePageRecord tpr = null;
        for (int i=0;i<4;i++) {
            if (!classes.get(i).isEmpty()) {
                tpr = tablePage.getRecord(classes.get(i).get(0).getPage().getId());
            }
        }
        if (tpr != null && tpr.getIdSwap() != -1) {
            physMemory.addPage(tpr.getIdPhisMemory(), swap.getPage(tpr.getIdSwap()));
        } else {
            physMemory.addPage(tpr.getIdPhisMemory(), tablePage.getRecord(numNewPage).getPage());
        }
        return tpr;
    }
}