package laba4_S.View;

import javax.swing.*;


public class CreateDisk {
    public JFrame frame;
    private JTextField dixkSize;
    private JTextField bloackSize;
    private int diskSize;
    private int blockSize;

    public CreateDisk() {
        frame = new JFrame();
        frame.setBounds(100, 100, 316, 176);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener((event) -> {
            diskSize = Integer.parseInt(dixkSize.getText());
            blockSize = Integer.parseInt(bloackSize.getText());
            frame.setVisible(false);
            Main main = new Main(diskSize, blockSize);
            main.frame.setVisible(true);
        });
        btnOk.setBounds(63, 83, 97, 25);
        frame.getContentPane().add(btnOk);

        dixkSize = new JTextField();
        dixkSize.setBounds(110, 13, 116, 22);
        frame.getContentPane().add(dixkSize);
        dixkSize.setColumns(10);

        JLabel lblFirstName = new JLabel("diskSize");
        lblFirstName.setBounds(12, 16, 86, 16);
        frame.getContentPane().add(lblFirstName);

        bloackSize = new JTextField();
        bloackSize.setBounds(110, 48, 116, 22);
        frame.getContentPane().add(bloackSize);
        bloackSize.setColumns(10);

        JLabel lblMidName = new JLabel("blockSize");
        lblMidName.setBounds(12, 51, 71, 16);
        frame.getContentPane().add(lblMidName);
        frame.setVisible(true);
    }
}
