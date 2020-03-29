package ui;
import handler.AuthenticationHandler;
import interact.EstateTransactionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstateAuthenticator extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextField uidText, pwdText;
    private JComboBox<String> accountType;
    private AuthenticationHandler handler;

    public EstateAuthenticator(AuthenticationHandler handler) {
        this.handler = handler;
    }

    public void authenticate() {
        frame = new JFrame("Authentication");
        frame.setSize(350, 200);
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

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 110, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        handler.HandleLogin(EstateTransactionHandler.getModeByName((String) accountType.getSelectedItem()),
                uidText.getText(), pwdText.getText());
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
                    "Incorrect username/password",
                    "Authentication warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
