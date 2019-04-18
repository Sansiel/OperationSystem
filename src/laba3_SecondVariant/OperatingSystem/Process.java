package laba3_SecondVariant.OperatingSystem;

import java.util.ArrayList;

public class Process{
    private int id;
    private ArrayList<Integer> pagesIds;


    public Process(int id) {
        this.id = id;
        this.pagesIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getPagesIds() {
        return pagesIds;
    }
}