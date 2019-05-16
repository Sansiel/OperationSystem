package laba4_S.View;

import laba4_S.Disk.Files.Directory;
import laba4_S.Disk.Files.File;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class TreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof Directory) {
                Directory directory = (Directory) node.getUserObject();
                if (directory.getParent() == null) {
                    this.setIcon(UIManager.getIcon("FileView.computerIcon"));
                }
                else {
                    this.setIcon(UIManager.getIcon("FileView.directoryIcon"));
                }
            }
            else if (node.getUserObject() instanceof File) {
                this.setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
        }
        return this;
    }
}
