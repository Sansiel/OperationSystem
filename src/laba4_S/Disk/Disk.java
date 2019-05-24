package laba4_S.Disk;

import laba4_S.Disk.Files.Directory;
import laba4_S.Disk.Files.File;
import laba4_S.Disk.Files.FileObject;

import javax.swing.*;
import java.util.Iterator;

public class Disk {
    private FAT fat;
    public Directory root;
    private FileObject buffer;
    private int blockSize;

    public Disk(int memorySize, int blockSize) {
        this.fat = new FAT(memorySize, blockSize);
        this.root = new Directory();
        Block block = this.fat.getEmptyBlock();
        this.blockSize = block.getSize();
        block.setData(this.root);
        this.root.setDirectoryBlock(block);
        this.buffer = null;
    }

    public FAT getFat() {
        return fat;
    }

    public Directory addDirectory(Directory parent, String name) {
        if(!this.fat.hasEnoughSpace(1)) {
            JOptionPane.showMessageDialog(new JFrame(),"No enough place");
            return null;
        }

        int index = 0;
        String newName = name;
        for (Iterator<Directory> iterator = parent.getDirectories(); iterator.hasNext(); ) {
            Directory directory = iterator.next();
            if (newName.equals(directory.getName())) {
                index++;
                newName = String.format("%s (%d)", name, index);
            }
        }
        name = newName;

        Directory directory = new Directory(name, parent);
        parent.addFileObject(directory);

        Block block = this.fat.getEmptyBlock();
        block.setData(directory);
        directory.setDirectoryBlock(block);

        return directory;
    }

    public File addFile(Directory parent, String name, int size){
        if(!this.fat.hasEnoughSpace(size)) {
            JOptionPane.showMessageDialog(new JFrame(),"No enough place");
            return null;
        }

        int index = 0;
        String newName = name;
        for (Iterator<File> iterator = parent.getFiles(); iterator.hasNext(); ) {
            File file = iterator.next();
            if (newName.equals(file.getName())) {
                index++;
                newName = String.format("%s (%d)", name, index);
            }
        }
        name = newName;

        File file = new File(name, size, parent);
        parent.addFileObject(file);

        int blockCount = size / blockSize;
        if (size % blockSize > 0) blockCount += 1;
        for (int i = 0; i < blockCount; i++) {
            Block block = this.fat.getEmptyBlock();
            block.setData(file);
            file.addBlock(block);
        }

//        while (size > 0) {
//            Block nextBlock = this.fat.getEmptyBlock();
//            block.setNextBlock(nextBlock);
//
//            block = nextBlock;
//            size -= block.getSize();
//            block.setData(file);
//        }

        return file;
    }

    public File deleteFile(File file) {
        for (Block block : file.getBlocks()) {
            block.setData(null);
        }
        file.setBlocks(null);
//        while (block != null) {
//            block.setData(null);
//            block = block.getNextBlock();
//        }
        file.getParent().deleteFileObject(file);
        return file;
    }

    public Directory deleteDirectory(Directory directory){
        for (Iterator<File> iterator = directory.getFiles(); iterator.hasNext(); ) {
            this.deleteFile(iterator.next());
        }
        for (Iterator<Directory> iterator = directory.getDirectories(); iterator.hasNext(); ) {
            this.deleteDirectory(iterator.next());
        }

        directory.getBlock().setData(null);
        directory.getParent().deleteFileObject(directory);
        return directory;
    }

//    public void copy(FileObject fileObject) {
//        if (fileObject == null) {
//            return;
//        }
//        this.buffer = this.copy(fileObject, null);
//    }
//
//    private FileObject copy(FileObject fileObject, Directory parent) {
//        if (fileObject instanceof File) {
//            File file = (File) fileObject;
//            return new File(file.getName(), file.getSize(), parent);
//        }
//        if (fileObject instanceof Directory) {
//            Directory directory = (Directory) fileObject;
//            Directory copy = new Directory(directory.getName(), parent);
//
//            for (Iterator<FileObject> iterator = directory.getFileObjects(); iterator.hasNext(); ) {
//                copy.addFileObject(this.copy(iterator.next(), copy));
//            }
//            return copy;
//        }
//        return null;
//    }
//
//    public void paste(Directory parent) {
//        if (parent == null || this.buffer == null) {
//            return;
//        }
//        if (!this.fat.hasEnoughSpace(this.buffer.getSize())) {
//            JOptionPane.showMessageDialog(new JFrame(),"No enough place");
//            return;
//        }
//
//        this.paste(this.buffer, parent);
//    }
//
//    private void paste(FileObject fileObject, Directory parent) {
//        if (fileObject instanceof File) {
//            File file = (File) fileObject;
//            this.addFile(parent, file.getName(), file.getSize());
//        }
//        if (fileObject instanceof Directory) {
//            Directory directory = (Directory) fileObject;
//            Directory newDirectory = this.addDirectory(parent, directory.getName());
//
//            for (Iterator<FileObject> iterator = directory.getFileObjects(); iterator.hasNext(); ) {
//                this.paste(iterator.next(), newDirectory);
//            }
//        }
//    }
}
