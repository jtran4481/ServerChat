package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Creates a jpanel window where user inputs username and password
 */

public class LoginWindow extends JFrame {

    private final ChatClient client;
    JTextField loginField = new JTextField();
    JPasswordField passworldField = new JPasswordField();
    JButton loginButton =  new JButton("Login");

    public LoginWindow() {
        super("Login");

        this.client = new ChatClient( "localHost", 8818);
        client.connect();

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(loginField);
        p.add(passworldField);
        p.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        getContentPane().add(p, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin() {
        String login = loginField.getText();
        String password = passworldField.getText();
        System.out.print(login);
        System.out.print(password);
        try {
            if (client.login(login, password)) {
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login/password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        LoginWindow loginWin = new LoginWindow();
        loginWin.setVisible(true);


    }
}

