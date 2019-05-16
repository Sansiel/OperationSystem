package laba4_S.Disk.Files;

public class File extends FileObject {
    public File(String name, int size, Directory parent){
        super(name, size, parent);
    }

    @Override
    public Directory getParent() {
        return (Directory) super.getParent();
    }
}
