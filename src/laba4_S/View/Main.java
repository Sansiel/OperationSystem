package laba4_S.View;

import laba4_S.Disk.*;
import laba4_S.Disk.Files.*;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public JFrame frame;
    private DiskPanel diskPanel;
    private JTree tree;

    private JTextField dixkSize;
    private JTextField bloackSize;

    private Disk disk;
    private File selectedFile;
    private Directory selectedDirectory;

    public Main(int diskSize, int blockSize) {
        this.disk = new Disk(diskSize, blockSize);

        this.frame = new JFrame();
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        this.diskPanel = new DiskPanel(this.disk, this.selectedFile, this.selectedDirectory);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 3;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.frame.getContentPane().add(this.diskPanel, gridBagConstraints);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        this.tree = new JTree();
        this.tree.setCellRenderer(new TreeCellRenderer());
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node != null) {
                    Object nodeInfo = node.getUserObject();
                    if (nodeInfo instanceof Directory) {
                        selectedDirectory = (Directory) nodeInfo;
                        selectedFile = null;
                    }
                    if (nodeInfo instanceof File) {
                        selectedDirectory = null;
                        selectedFile = (File) nodeInfo;
                    }
                    updateDisk();
                }
            }
        });
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 40;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        leftPanel.add(this.tree, gridBagConstraints);

        JButton btnNewFile = new JButton("Create new file");
        btnNewFile.addActionListener(event -> {
            NewFileDialog fileDialog = new NewFileDialog(this.frame);
            fileDialog.setVisible(true);
            if (fileDialog.isAccepted()) {
                Directory parent = this.selectedDirectory;
                if (parent == null && this.selectedFile != null) {
                    parent = this.selectedFile.getParent();
                }
                if (parent == null) {
                    parent = this.disk.root;
                }
                this.disk.addFile(parent, fileDialog.getFileName(), fileDialog.getFileSize());
            }

            this.updateDisk();
            this.updateTree();
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        leftPanel.add(btnNewFile, gridBagConstraints);

        JButton btnNewDirectory = new JButton("Create new directory");
        btnNewDirectory.addActionListener(event -> {
            NewDirectoryDialog directoryDialog = new NewDirectoryDialog(this.frame);
            directoryDialog.setVisible(true);
            if (directoryDialog.isAccepted()) {
                Directory parent = this.selectedDirectory;
                if (parent == null && this.selectedFile != null) {
                    parent = this.selectedFile.getParent();
                }
                if (parent == null) {
                    parent = this.disk.root;
                }
                this.disk.addDirectory(parent, directoryDialog.getFileName());
            }

            this.updateDisk();
            this.updateTree();
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        leftPanel.add(btnNewDirectory, gridBagConstraints);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(event -> {
            if (this.selectedFile != null){
                this.disk.deleteFile(this.selectedFile);
                this.selectedFile = null;
            }
            if (this.selectedDirectory != null) {
                this.disk.deleteDirectory(this.selectedDirectory);
                this.selectedDirectory = null;
            }

            this.updateDisk();
            this.updateTree();
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        leftPanel.add(btnDelete, gridBagConstraints);

        JButton btnCopy = new JButton("Copy");
        btnCopy.addActionListener(event -> {
            if (this.selectedFile != null){
                this.disk.copy(this.selectedFile);
            }
            if (this.selectedDirectory != null) {
                this.disk.copy(this.selectedDirectory);
            }
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        leftPanel.add(btnCopy, gridBagConstraints);

        JButton btnCut = new JButton("Cut");
        btnCut.addActionListener(event -> {
            if (this.selectedFile != null){
                this.disk.copy(this.selectedFile);
                this.disk.deleteFile(this.selectedFile);
                this.selectedFile = null;
            }
            if (this.selectedDirectory != null) {
                this.disk.copy(this.selectedDirectory);
                this.disk.deleteDirectory(this.selectedDirectory);
                this.selectedDirectory = null;
            }

            this.updateDisk();
            this.updateTree();
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        leftPanel.add(btnCut, gridBagConstraints);

        JButton btnPaste = new JButton("Paste");
        btnPaste.addActionListener(event -> {
            Directory parent = this.selectedDirectory;
            if (parent == null && this.selectedFile != null) {
                parent = this.selectedFile.getParent();
            }
            if (parent == null) {
                parent = this.disk.root;
            }
            this.disk.paste(parent);

            this.updateDisk();
            this.updateTree();
        });
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        leftPanel.add(btnPaste, gridBagConstraints);


        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.frame.getContentPane().add(leftPanel, gridBagConstraints);

        this.updateDisk();
        this.updateTree();
    }

    private void updateDisk() {
        this.diskPanel.update(this.disk, this.selectedFile, this.selectedDirectory);
    }

    private void updateTree() {
        DefaultTreeModel treeModel = (DefaultTreeModel) this.tree.getModel();
        treeModel.setRoot(this.disk.root.getTree());
        this.expandAllTreeNodes(this.tree, 0, this.tree.getRowCount());
    }

    private void expandAllTreeNodes(JTree tree, int startingIndex, int rowCount){
        for (int i = startingIndex; i < rowCount; i++) {
            tree.expandRow(i);
        }
        if (tree.getRowCount() != rowCount) {
            this.expandAllTreeNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CreateDisk createDisk = new CreateDisk();
                createDisk.frame.setVisible(true);
                //Main main = new Main();
                //main.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
