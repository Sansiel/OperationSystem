package laba4_S.View;

import laba4_S.Disk.*;
import laba4_S.Disk.Files.*;

import javax.swing.*;
import java.awt.*;

public class DiskPanel extends JPanel {
    private Disk disk;
    private File selectedFile;
    private Directory selectedDirectory;

    public DiskPanel(Disk disk, File selectedFile, Directory selectedDirectory) {
        this.disk = disk;
        this.selectedFile = selectedFile;
        this.selectedDirectory = selectedDirectory;
    }

    public void update(Disk disk, File selectedFile, Directory selectedDirectory) {
        this.disk = disk;
        this.selectedFile = selectedFile;
        this.selectedDirectory = selectedDirectory;
        this.repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        FAT fat = this.disk.getFat();

        int blocksCount = 1;
        while (blocksCount * blocksCount < fat.getBlocksCount()) {
            blocksCount++;
        }
        int blockSide = Math.min(
                (int)(0.9 * this.getWidth()) / blocksCount,
                (int)(0.9 * this.getHeight()) / blocksCount
        );
        int space = Math.min(
                (int)(0.1 * this.getWidth()) / (blocksCount - 1),
                (int)(0.1 * this.getHeight()) / (blocksCount - 1)
        );

        g.setColor(new Color(255, 255, 255));
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        for (int w = 0; w < blocksCount; w++) {
            for (int h = 0; h < blocksCount; h++) {
                int blockID = w * (blocksCount + 1) + h;
                if (blockID >= fat.getBlocksCount()) {
                    continue;
                }
                Block block = fat.getBlock(blockID);

                boolean isSelected = false;
                if (this.selectedFile != null) {
                    isSelected = this.selectedFile.hasBlock(block);
                }
                if (this.selectedDirectory != null) {
                    isSelected = this.selectedDirectory.hasBlock(block);
                }

                g.setColor(new Color(160, 160, 160));
                if (block.getData() != null) {
                    if (block.getData() instanceof Directory) {
                        g.setColor(new Color(67, 180, 44));
                    }
                    if (block.getData() instanceof File) {
                        g.setColor(new Color(32, 64, 200));
                    }
                    if (isSelected) {
                        g.setColor(new Color(200, 0, 0));
                    }
                }

                g.fillRect(h * (blockSide + space), w * (blockSide + space), blockSide, blockSide);
            }
        }
    }
}
