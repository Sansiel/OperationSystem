package laba3;

import java.util.*;

public class TablePage {

    private ArrayList<TablePageRecord> listPageTable = new ArrayList<>();

    public int addRecord(TablePageRecord tr) {
        listPageTable.add(tr);
        tr.getPage().setId(listPageTable.indexOf(tr));
        return listPageTable.indexOf(tr);
    }

    public TablePageRecord getRecord(int id) {
        return listPageTable.get(id);
    }

    public int getSize() {
        return listPageTable.size();
    }

}
