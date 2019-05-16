package laba4_S.View;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewDirectoryDialog extends JDialog {
    private JTextField nameField;

    private String fileName;
    private boolean accepted = false;

    public NewDirectoryDialog(JFrame parent) {
        super(parent, "Create new directory", true);

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

        JLabel lblName = new JLabel("Directory name");
        lblName.setBounds(10, 14, 46, 14);
        this.getContentPane().add(lblName);

        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(e -> {
            String nameText = this.nameField.getText();

            if (nameText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty filename");
                return;
            }

            this.fileName = nameText;
            this.accepted = true;
            dispose();
        });
        btnOk.setBounds(49, 69, 83, 23);
        this.getContentPane().add(btnOk);
    }

    public String getFileName() {
        return this.fileName;
    }

    public boolean isAccepted() {
        return this.accepted;
    }
}

