package laba4_S.Disk.Files;

import laba4_S.Disk.Block;

import java.util.Stack;

public abstract class FileObject {
    private String name;
    private FileObject parent;

    private final int size;
    private Block block;

    public FileObject(String name, int size, FileObject parent) {
        this.name = name;
        this.parent = parent;

        this.size = size;
        this.block = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileObject getParent() {
        return parent;
    }

    public void setParent(FileObject parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public Block getBlock() {
        return block;
    }

    public boolean hasBlock(Block findBlock) {
        Block block = this.getBlock();
        while (block != null) {
            if (block.equals(findBlock)) {
                return true;
            }
            block = block.getNextBlock();
        }
        return false;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getFullName(){
        Stack<String> stack = new Stack<>();
        FileObject fileObject = this;
        while (fileObject.getParent() != null) {
            stack.add(fileObject.getName());
            fileObject = fileObject.getParent();
        }

        String sep = System.getProperty("path.separator");
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.empty()) {
            stringBuilder.append(stack.pop()).append(sep);
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

