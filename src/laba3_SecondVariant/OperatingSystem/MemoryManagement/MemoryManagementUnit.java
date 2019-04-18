package laba3_SecondVariant.OperatingSystem.MemoryManagement;

import laba3_SecondVariant.OperatingSystem.OperatingSystem;
import laba3_SecondVariant.OperatingSystem.Page;
import laba3_SecondVariant.OperatingSystem.Process;

import java.util.ArrayDeque;

public class MemoryManagementUnit {
    private TranslationLookasideBuffer TLB;
    private ArrayDeque<Page> clock;

    public MemoryManagementUnit() {
        this.TLB = new TranslationLookasideBuffer();
        this.clock = new ArrayDeque<>();
    }

    public int addPage(Process process) {
        int pageId = this.TLB.addPage(new Page(process.getId()));
        process.getPagesIds().add(pageId);
        this.getPage(pageId);
        return pageId;
    }

    public Page getPage(int pageId) {
        Page page = this.TLB.getPage(pageId);
        if (page.isPresence()) {
            page.setRecourse(true);
        }
        else {
            int emptyPageId = OperatingSystem.memory.getEmptyPageId();
            if (emptyPageId != -1) {
                OperatingSystem.memory.setPage(emptyPageId, page);
                page.setRecourse(true);
                page.setPresence(true);
                page.setPhysicalAddress(emptyPageId);
                this.clock.addLast(page);
            }
            else {
                while (true) {
                    Page replacePage = this.clock.pollFirst();
                    if (replacePage.isRecourse()) {
                        replacePage.setRecourse(false);
                        this.clock.addLast(replacePage);
                    }
                    else {
                        if (replacePage.getVirtualAddress() != -1) {
                            OperatingSystem.memory.setPage(replacePage.getPhysicalAddress(), OperatingSystem.swap.getPage(replacePage.getVirtualAddress()));
                        }
                        else {
                            OperatingSystem.memory.setPage(replacePage.getPhysicalAddress(), page);
                        }

                        page.setRecourse(true);
                        page.setPresence(true);
                        page.setPhysicalAddress(replacePage.getPhysicalAddress());
                        this.clock.addLast(page);

                        replacePage.setPresence(false);
                        replacePage.setVirtualAddress(OperatingSystem.swap.addPage(replacePage));
                        replacePage.setPhysicalAddress(-1);
                        break;
                    }
                }
            }
        }
        return page;
    }
}

