package laba3_SecondVariant.OperatingSystem;

public class Page {
    private int processId;
    private int physicalAddress;
    private int virtualAddress;

    private boolean recourse;  // Обращение
    private boolean presence;  // Присутствие
    private boolean modifications;  // Модификация

    public Page(int processId) {
        this.processId = processId;
        this.physicalAddress = -1;
        this.virtualAddress = -1;

        this.recourse = false;
        this.presence = false;
        this.modifications = false;
    }

    public boolean isRecourse() {
        return recourse;
    }

    public void setRecourse(boolean recourse) {
        this.recourse = recourse;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public boolean isModifications() {
        return modifications;
    }

    public void setModifications(boolean modifications) {
        this.modifications = modifications;
    }

    public int getProcessId() {
        return processId;
    }

    public int getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(int physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(int virtualAddress) {
        this.virtualAddress = virtualAddress;
    }
}

