package ui;

import handler.AgentTransactionHandler;
import types.AccountMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentFrame extends JFrame implements ActionListener {
    private AgentTransactionHandler handler;
    private JPanel registerPanel;
    private AccountMode mode;
    private JRadioButton select_agentID, select_agencyName, select_agencyByID;
    private JTextField key, agentName, phoneNumber, agencyName;
    private JScrollPane holder;
    private EstateUI parent;

    public AgentFrame(EstateUI parent, AgentTransactionHandler handler, AccountMode mode) {
        this.handler = handler;
        this.mode = mode;
        this.parent = parent;
        setupPanel();
    }

    private void setupPanel(){
        // registerAgent (admin mode)
        // getAgentByID (returns agent object)
        // getAgencyByName (returns agency object)
        // getAgencyByAgentID (returns name of agency for the specific agent)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,400);

        JButton registerAgent = new JButton("Register agent");
        registerAgent.setBounds(10, 20, 130, 25);
        if(mode == AccountMode.ADMIN) {
            this.add(registerAgent);
            registerAgent.addActionListener(this);
        }

        JLabel selectPrompt = new JLabel("Please provide agent's id or agency name");
        selectPrompt.setBounds(10, 50, 300, 25);
        this.add(selectPrompt);

        select_agentID = new JRadioButton("Agent ID");
        select_agentID.setBounds(10, 70, 150, 25);
        select_agencyName = new JRadioButton("Agency Name");
        select_agencyName.setBounds(10, 90, 150, 25);
        select_agencyByID = new JRadioButton("Agency by ID");
        select_agencyByID.setBounds(10, 110, 150, 25);

        this.add(select_agentID);
        this.add(select_agencyName);
        this.add(select_agencyByID);
        ButtonGroup g = new ButtonGroup();
        g.add(select_agentID);
        g.add(select_agencyName);
        g.add(select_agencyByID);
        g.setSelected(select_agentID.getModel(), true);

        key = new JTextField();
        key.setBounds(160, 80, 160, 25);
        this.add(key);

        JButton submit = new JButton("Find");
        submit.setBounds(300, 120, 80, 25);
        this.add(submit);
        submit.addActionListener(this);

        JButton mainMenu = new JButton("Menu");
        mainMenu.setBounds(380, 120, 80, 25);
        this.add(mainMenu);
        mainMenu.addActionListener(this);
        holder = new JScrollPane();
        this.add(holder);

        setupRegister();

        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        JTable result = new JTable();
        switch(actionEvent.getActionCommand()) {
            case "Find":
                this.remove(holder);
                registerPanel.setVisible(false);
                if (select_agentID.isSelected()) {
                    result = handler.selectAgentByID(key.getText());
                } else if (select_agencyName.isSelected()){
                    result = handler.selectAgencyByName(key.getText());
                } else if (select_agencyByID.isSelected()) {
                    result = handler.selectAgencyByID(key.getText());
                }
                holder = new JScrollPane(result);
                holder.setBounds(10, 150, 400, 100);
                this.add(holder);
                this.repaint();
                break;
            case "Register agent":
                registerPanel.setVisible(true);
                this.repaint();
                break;
            case "Submit":
                registerPanel.setVisible(false);
                JOptionPane.showMessageDialog(registerPanel,
                        handler.insertAgent(agentName.getText(), phoneNumber.getText(), agencyName.getText()));
                break;
            case "Menu":
                this.setVisible(false);
                parent.switchFrame(EstateUI.states.Menu.name());
            default:
                break;
        }
    }

    private void setupRegister(){
        registerPanel = new JPanel();
        registerPanel.setLayout(null);
        JLabel registerPrompt = new JLabel("Please enter associated information to register an agent");
        registerPrompt.setBounds(10,0, 400, 25);

        JLabel namePrompt = new JLabel("Name: ");
        namePrompt.setBounds(10,25, 80, 25);
        agentName = new JTextField();
        agentName.setBounds(110,25, 100, 25);

        JLabel phonePrompt = new JLabel("Phone Number: ");
        phonePrompt.setBounds(10,50, 100, 25);
        phoneNumber = new JTextField();
        phoneNumber.setBounds(110,50, 100, 25);

        JLabel agencyPrompt = new JLabel("Agency Name: ");
        agencyPrompt.setBounds(10,75, 100, 25);
        agencyName = new JTextField();
        agencyName.setBounds(110,75, 100, 25);

        JButton submitRegister = new JButton("Submit");
        submitRegister.setBounds(300, 110, 80,25);
        submitRegister.addActionListener(this);

        registerPanel.add(registerPrompt);
        registerPanel.add(namePrompt);
        registerPanel.add(agentName);
        registerPanel.add(phonePrompt);
        registerPanel.add(phoneNumber);
        registerPanel.add(agencyPrompt);
        registerPanel.add(agencyName);
        registerPanel.add(submitRegister);
        registerPanel.setBounds(10,150, 400, 200);
        this.add(registerPanel);
        registerPanel.setVisible(false);
    }
}
