package laba4_S.Disk;

import laba4_S.Disk.Files.FileObject;

public class Block {
    private final int id;
    private final int size;
    private FileObject data;
    private Block nextBlock;

    public Block(int id, int size) {
        this.id = id;
        this.size = size;
        this.data = null;
        this.nextBlock = null;
    }

    public int getID() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

    public FileObject getData() {
        return data;
    }

    public void setData(FileObject data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return this.data == null;
    }

    public Block getNextBlock() {
        return this.nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public boolean equals(Block block) {
        return this.getID() == block.getID();
    }
}
