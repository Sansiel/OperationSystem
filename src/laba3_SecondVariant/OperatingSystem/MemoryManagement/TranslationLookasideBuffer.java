package laba3_SecondVariant.OperatingSystem.MemoryManagement;

import laba3_SecondVariant.OperatingSystem.Page;

import java.util.ArrayList;

public class TranslationLookasideBuffer {
    private ArrayList<Page> table;

    public TranslationLookasideBuffer() {
        this.table = new ArrayList<>();
    }

    public int addPage(Page page) {
        this.table.add(page);
        return this.table.indexOf(page);
    }

    public Page getPage(int pageId) {
        return this.table.get(pageId);
    }
}
