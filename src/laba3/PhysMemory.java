package laba3;

public class PhysMemory {

    private Page[] arrayPage;

    public PhysMemory() {
        this.arrayPage = new Page[12];
    }

    public Page[] getArrayPage() {
        return arrayPage;
    }

    public void addPage(int number, Page page) {
        arrayPage[number] = page;
    }

    public Page getPage(int i) {
        return arrayPage[i];
    }

    public int getSpacePage() {
        for (int i = 0; i < arrayPage.length; i++) {
            if (arrayPage[i] == null) {
                return i;
            }
        }
        return -1;
    }

}
