package laba3;

import java.util.ArrayList;

public class Swaping {

    private ArrayList<Page> listPageSwapping = new ArrayList<>();

    public void setListPage(ArrayList<Page> listPage) {
        this.listPageSwapping = listPage;
    }

    public int add(Page page) {
        listPageSwapping.add(page);
        return listPageSwapping.indexOf(page);
    }

    public Page getPage(int i) {
        return listPageSwapping.get(i);
    }

    public String writeSwap() {
        return "Страниц на диске : " + listPageSwapping.size() + '\n';
    }

}