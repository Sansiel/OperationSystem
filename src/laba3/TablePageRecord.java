package laba3;

public class TablePageRecord {

    private Page page;
    private int idPhysMemory;
    private int idSwap;
    private boolean presence;// бит присутсвия-отсутствия
    private boolean recours;// бит обращения
    private boolean modifications;// бит модификации
    private String procName;

    public TablePageRecord(String procName) {
        page = new Page();
        this.idPhysMemory = -1;
        this.idSwap = -1;
        this.procName = procName;
        this.presence = false;
        this.recours = false;
        this.modifications = false;
    }

    public String getProcNumber() {
        return procName;
    }

    public void setProcNumber(String procName) {
        this.procName = procName;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getIdPhisMemory() {
        return idPhysMemory;
    }

    public void setIdPhysMemory(int idPhysMemory) {
        this.idPhysMemory = idPhysMemory;
    }

    public int getIdSwap() {
        return idSwap;
    }

    public void setIdSwap(int idSwap) {
        this.idSwap = idSwap;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public boolean isRecours() {
        return recours;
    }

    public void setRecours(boolean recours) {
        this.recours = recours;
    }

    public boolean isModifications() {
        return modifications;
    }

    public void setModifications(boolean modifications) {
        this.modifications = modifications;
    }

}