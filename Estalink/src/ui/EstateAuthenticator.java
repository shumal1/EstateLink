package ui;
import handler.AuthenticationHandler;
import interact.EstateTransactionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstateAuthenticator extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextField uidText, pwdText, db_uid, db_pwd;
    private JComboBox<String> accountType;
    private AuthenticationHandler handler;

    public EstateAuthenticator(AuthenticationHandler handler) {
        this.handler = handler;
    }

    public void authenticate() {
        frame = new JFrame("Authentication");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel accountLabel = new JLabel("Account type:");
        accountLabel.setBounds(10, 20, 80, 25);
        panel.add(accountLabel);

        String[] accountTypes = { "ADMIN", "AGENT", "GUEST"};
        accountType = new JComboBox<>(accountTypes);
        accountType.setBounds(100, 20, 80, 25);
        panel.add(accountType);


        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 50, 80, 25);
        panel.add(userLabel);

        uidText = new JTextField(20);
        uidText.setBounds(100, 50, 165, 25);
        panel.add(uidText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        pwdText = new JPasswordField(20);
        pwdText.setBounds(100, 80, 165, 25);
        panel.add(pwdText);

        JLabel databaseLabel = new JLabel("Database Username:");
        databaseLabel.setBounds(10, 125, 160, 25);
        panel.add(databaseLabel);

        db_uid = new JTextField(20);
        db_uid.setBounds(175, 125, 165, 25);
        panel.add(db_uid);

        JLabel databasePassword = new JLabel("Database Password:");
        databasePassword.setBounds(10, 150, 160, 25);
        panel.add(databasePassword);

        db_pwd = new JPasswordField(20);
        db_pwd.setBounds(175, 150, 165, 25);
        panel.add(db_pwd);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 175, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("login")) {
           displayResult(handler.HandleLogin(EstateTransactionHandler.getModeByName((String) accountType.getSelectedItem()),
                    uidText.getText(), pwdText.getText(), db_uid.getText(), db_pwd.getText()));
        }
    }

    public void dispose(){
        frame.setVisible(false);
        frame.dispose();
    }

    public void displayResult(boolean result) {
        if (result){
            JOptionPane.showMessageDialog(frame,
                    "Successfully logged-in");
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Incorrect username/password/ Unable to connect to the database",
                    "Authentication warning",
                    JOptionPane.WARNING_MESSAGE);
        }

        pwdText.setText("");
        db_pwd.setText("");
    }
}
