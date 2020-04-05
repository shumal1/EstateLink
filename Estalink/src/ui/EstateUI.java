package ui;
import handler.AgentTransactionHandler;
import handler.ListingTransactionHandler;
import handler.ResourceTransactionHandler;
import handler.TransactionHandler;
import types.AccountMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstateUI extends JFrame implements ActionListener {
    private TransactionHandler handler;
    private JPanel mainMenu;
    private AccountMode mode;
    private JFrame mainFrame, updateFrame, listingFrame, resourceFrame, agentFrame;
    enum states  {Menu, Listings, Resources, Agents};

    public EstateUI(AgentTransactionHandler handler, AccountMode mode) {
        this.handler = handler;
        this.mode = mode;
    }

    public void showUI(){
        mainFrame = new JFrame("EstateLink");
        mainFrame.setSize(800,400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updateFrame = new UpdateFrame(this, (ListingTransactionHandler) handler);
        resourceFrame = new ResourceFrame(this, (ResourceTransactionHandler) handler);
        listingFrame = new ListingFrame(this, (ListingTransactionHandler) handler, mode);
        agentFrame = new AgentFrame(this, (AgentTransactionHandler) handler, mode);
        setupMainMenu();


        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        mainFrame.add(mainMenu);
        // make the window visible
        mainFrame.setVisible(true);
    }

    private void setupMainMenu(){
        mainMenu = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        mainMenu.setLayout(gb);

        JButton updateView = new JButton("Update");
        JButton resourceView = new JButton("Resources");
        JButton listingView = new JButton("Listings");
        JButton agentView = new JButton("Agents");

        try{
            Icon updateIcon = new ImageIcon("resources/edit.png");
            Icon listingIcon = new ImageIcon("resources/home.png");
            Icon resourceIcon = new ImageIcon("resources/park.png");
            Icon agentIcon = new ImageIcon("resources/agent.png");

            updateView.setIcon(updateIcon);
            listingView.setIcon(listingIcon);
            resourceView.setIcon(resourceIcon);
            agentView.setIcon(agentIcon);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " I AM HERE");
            // silently ignore, don't add icon
        }


        c.fill = GridBagConstraints.NONE;
        c.gridy = 6;
        c.gridx = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;
        c.ipady = 0;
        gb.setConstraints(updateView, c);

        c.gridx = GridBagConstraints.RELATIVE;
        gb.setConstraints(resourceView, c);
        gb.setConstraints(agentView, c);
        gb.setConstraints(listingView, c);

        c.gridy =  7;


        mainMenu.add(updateView);
        if (mode == AccountMode.GUEST) {
            updateView.setVisible(false);
        }
        mainMenu.add(resourceView);
        mainMenu.add(listingView);
        mainMenu.add(agentView);

        listingView.addActionListener(this);
        resourceView.addActionListener(this);
        agentView.addActionListener(this);
        updateView.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // switch panels here
        System.out.println(actionEvent.getActionCommand());
        switchFrame(actionEvent.getActionCommand());
    }

    public void switchFrame(String id) {
        switch (id) {
            case "Update":
                mainFrame.setVisible(false);
                updateFrame.setVisible(true);
                break;
            case "Listings":
                mainFrame.setVisible(false);
                listingFrame.setVisible(true);
                break;
            case "Resources":
                mainFrame.setVisible(false);
                resourceFrame.setVisible(true);
                break;
            case "Agents":
                mainFrame.setVisible(false);
                agentFrame.setVisible(true);
                break;
            case "Menu":
                mainFrame.setVisible(true);
                break;
        }
    }
}
