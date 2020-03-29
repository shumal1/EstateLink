package ui;

import handler.TransactionHandler;
import model.AgentModel;
import types.AccountMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentPanel extends JPanel implements ActionListener {
    private TransactionHandler handler;
    private AccountMode mode;
    private JRadioButton agentID, agencyName;
    private JTextField key;
    private EstateUI parent;
    public AgentPanel (EstateUI parent, TransactionHandler handler, AccountMode mode) {
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
        this.setLayout(null);
        this.setSize(400,200);

        JButton registerAgent = new JButton("Register agent");
        registerAgent.setBounds(10, 20, 130, 25);
        if(mode == AccountMode.ADMIN) {
            this.add(registerAgent);
        }

        JLabel selectPrompt = new JLabel("Please provide agent's id or agency name");
        selectPrompt.setBounds(10, 50, 300, 25);
        this.add(selectPrompt);

        agentID = new JRadioButton("Agent ID");
        agentID.setBounds(10, 70, 150, 25);
        agencyName = new JRadioButton("Agency Name");
        agencyName.setBounds(10, 90, 150, 25);
        this.add(agentID);
        this.add(agencyName);
        ButtonGroup g = new ButtonGroup();
        g.add(agentID);
        g.add(agencyName);
        g.setSelected(agentID.getModel(), true);

        key = new JTextField();
        key.setBounds(160, 80, 160, 25);
        this.add(key);

        JButton submit = new JButton("Find");
        submit.setBounds(350, 150, 80, 25);
        this.add(submit);
        submit.addActionListener(this);

        JButton mainMenu = new JButton("Menu");
        mainMenu.setBounds(350, 200, 80, 25);
        this.add(mainMenu);
        mainMenu.addActionListener(this);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        JTable result;
        switch(actionEvent.getActionCommand()) {
            case "Find":
                if (agentID.isSelected()) {
                    result = handler.selectAgentByID(key.getText());
                } else {
                    result = handler.selectAgencyByName(key.getText());
                }
                JScrollPane holder = new JScrollPane(result);
                holder.setBounds(10, 250, 400, 100);
                this.add(holder);
                this.repaint();
                break;
            case "Register agent":
                // need to do popup...
                break;
            case "Menu":
                parent.switchPane(EstateUI.states.Menu.name());
            default:
                break;
        }
    }
}
