package laba4_S.Disk.Files;


import laba4_S.Disk.Block;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends FileObject {
    private ArrayList<FileObject> fileObjects;

    public Directory() {
        super("/", 1, null);
        this.fileObjects = new ArrayList<>();
    }

    public Directory(String name, Directory parent){
        super(name, 1, parent);
        this.fileObjects = new ArrayList<>();
    }

    public void addFileObject(FileObject fileObject) {
        this.fileObjects.add(fileObject);
    }

    public void deleteFileObject(FileObject fileObject) {
        if (this.fileObjects.contains(fileObject)) {
            this.fileObjects.remove(fileObject);
        }
    }

    public Iterator<FileObject> getFileObjects(){
        return this.fileObjects.iterator();
    }

    public Iterator<Directory> getDirectories(){
        ArrayList<Directory> directories = new ArrayList<>();
        for (FileObject fileObject : this.fileObjects) {
            if (fileObject instanceof Directory) {
                directories.add((Directory) fileObject);
            }
        }
        return directories.iterator();
    }

    public Iterator<File> getFiles(){
        ArrayList<File> files = new ArrayList<>();
        for (FileObject fileObject : this.fileObjects) {
            if (fileObject instanceof File) {
                files.add((File) fileObject);
            }
        }
        return files.iterator();
    }

    public int getSize(){
        int size = 1;
        for (FileObject fileObject : this.fileObjects) {
            size += fileObject.getSize();
        }
        return size;
    }

    @Override
    public Directory getParent() {
        return (Directory) super.getParent();
    }

    public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(this);
        for (Iterator<Directory> iterator = this.getDirectories(); iterator.hasNext();) {
            Directory directory = iterator.next();
            tree.add(directory.getTree());
        }
        for (Iterator<File> iterator = this.getFiles(); iterator.hasNext();) {
            File file = iterator.next();
            tree.add(new DefaultMutableTreeNode(file, false));
        }
        return tree;
    }

    public boolean hasBlock(Block findBlock) {
        for (Iterator<File> iterator = this.getFiles(); iterator.hasNext(); ) {
            File file = iterator.next();
            if (file.hasBlock(findBlock)) {
                return true;
            }
        }
        for (Iterator<Directory> iterator = this.getDirectories(); iterator.hasNext(); ) {
            Directory directory = iterator.next();
            if (directory.hasBlock(findBlock)) {
                return true;
            }
        }
        return super.hasBlock(findBlock);
    }
}
