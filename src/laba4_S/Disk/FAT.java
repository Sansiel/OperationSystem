package laba4_S.Disk;

public class FAT {
    private Block[] blocks;

    public FAT(int memorySize, int blockSize) {
        this.blocks = new Block[memorySize / blockSize];
        for (int blockID = 0; blockID < this.getBlocksCount(); blockID++) {
            this.blocks[blockID] = new Block(blockID, blockSize);
        }
    }

    public int getBlocksCount() {
        return this.blocks.length;
    }

    public Block getBlock(int id) {
        return blocks[id];
    }

    public Block getEmptyBlock() {
        for(int blockID = 0; blockID < blocks.length; blockID++) {
            Block block = this.getBlock(blockID);
            if (block.isEmpty()) {
                return block;
            }
        }
        return null;
    }

    public boolean hasEnoughSpace(int size) {
        int emptySize = 0;
        for(int blockID = 0; blockID < blocks.length; blockID++) {
            Block block = this.getBlock(blockID);
            if (block.isEmpty()) {
                emptySize += block.getSize();
                if (emptySize >= size) {
                    return true;
                }
            }
        }
        return emptySize >= size;
    }
}
