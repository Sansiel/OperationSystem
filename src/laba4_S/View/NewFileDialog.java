package laba4_S.View;

import javax.swing.*;
import java.awt.event.*;

public class NewFileDialog extends JDialog {
    private JTextField nameField;
    private JTextField sizeField;

    private String fileName;
    private int fileSize;
    private boolean accepted = false;

    public NewFileDialog(JFrame parent) {
        super(parent, "Create new file", true);

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
            }
        });
        setBounds(800, 300, 205, 142);
        getContentPane().setLayout(null);

        this.nameField = new JTextField();
        this.nameField.setBounds(69, 11, 106, 20);
        this.nameField.setColumns(10);
        this.getContentPane().add(nameField);

        this.sizeField = new JTextField();
        this.sizeField.setBounds(69, 38, 106, 20);
        this.sizeField.setColumns(10);
        this.getContentPane().add(sizeField);

        JLabel lblName = new JLabel("File name");
        lblName.setBounds(10, 14, 46, 14);
        this.getContentPane().add(lblName);

        JLabel lblSize = new JLabel("Size");
        lblSize.setBounds(10, 40, 46, 17);
        this.getContentPane().add(lblSize);

        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(e -> {
            String nameText = this.nameField.getText();
            String sizeText = this.sizeField.getText();

            if (nameText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty file name");
                return;
            }

            if (sizeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty file size");
                return;
            }

            int size = 0;
            try {
                size = Integer.valueOf(sizeText);
            }
            catch (Exception ex) {}
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Incorrect file size");
                return;
            }

            this.fileName = nameText;
            this.fileSize = size;
            this.accepted = true;
            dispose();
        });
        btnOk.setBounds(49, 69, 83, 23);
        this.getContentPane().add(btnOk);
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public boolean isAccepted() {
        return this.accepted;
    }
}
