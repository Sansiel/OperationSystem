package laba3_SecondVariant.OperatingSystem;

import java.util.ArrayList;

public class Memory {
    private Page[] pages;

    public Memory(int memorySize, int pageSize) {
        this.pages = new Page[memorySize / pageSize];
    }

    public int getPagesCount() {
        return pages.length;
    }

    public Page getPage(int pageId) {
        return this.pages[pageId];
    }

    public void setPage(int pageId, Page page) {
        this.pages[pageId] = page;
    }

    public int getEmptyPageId() {
        for (int index = 0; index < this.pages.length; index++) {
            if (this.pages[index] == null) {
                return index;
            }
        }
        return -1;
    }
}
