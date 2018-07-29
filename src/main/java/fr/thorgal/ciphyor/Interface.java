package fr.thorgal.ciphyor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Interface extends JFrame implements ActionListener {

    private JTextArea key = new JTextArea("Key");
    private JTextArea message = new JTextArea("Message");
    private JButton boutonCipher = new JButton("Cipher");
    private JButton boutonDecipher = new JButton("Decipher");

    public Interface() {

        this.setTitle("Ciphyor");
        this.setSize(550, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(new JScrollPane(key));
        container.add(new JScrollPane(message));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(boutonCipher);
        bottomPanel.add(boutonDecipher);
        panel.add(container);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        boutonCipher.addActionListener(this);
        boutonDecipher.addActionListener(this);

        this.setContentPane(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonCipher) {
            message.setText(Ciphyor.encode(key.getText(), message.getText()));
        } else if (e.getSource() == boutonDecipher) {
            message.setText(Ciphyor.decode(key.getText(), message.getText()));
        }
    }
}