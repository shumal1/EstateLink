package ui;


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
    private CardLayout currCard;
    private JFrame frame;
    static enum states  {Menu, Listings, Resources, Agents};

    public EstateUI(TransactionHandler handler, AccountMode mode, String uid) {
        this.handler = handler;
        this.mode = mode;
    }

    public void showUI(){
        frame = new JFrame("EstateLink");
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currCard = new CardLayout();
        frame.setLayout(currCard);

        JPanel listingPanel = new JPanel();
        JPanel resourcePanel = new JPanel();
        JPanel agentPanel = new AgentPanel(this, handler, mode);
        setupMainMenu();


        frame.add(states.Menu.name(), mainMenu);
        frame.add(states.Listings.name(), listingPanel);
        frame.add(states.Resources.name(), resourcePanel);
        frame.add(states.Agents.name(), agentPanel);

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        frame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        currCard.show(frame.getContentPane(), states.Menu.name());
        // make the window visible
        frame.setVisible(true);
    }

    private void setupMainMenu(){
        mainMenu = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        mainMenu.setLayout(gb);


        JButton listingView = new JButton("Listings");
        JButton resourceView = new JButton("Resources");
        JButton agentView = new JButton("Agents");

        try{
            Icon listingIcon = new ImageIcon("resources/home.png");
            Icon resourceIcon = new ImageIcon("resources/park.png");
            Icon agentIcon = new ImageIcon("resources/agent.png");
            listingView.setIcon(listingIcon);
            resourceView.setIcon(resourceIcon);
            agentView.setIcon(agentIcon);
        } catch (Exception e) {
            // silently ignore, don't add icon
        }


        c.fill = GridBagConstraints.NONE;
        c.gridy = 6;
        c.gridx = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.WEST;
        c.ipady = 0;
        gb.setConstraints(listingView, c);

        c.gridx = GridBagConstraints.RELATIVE;
        gb.setConstraints(resourceView, c);
        gb.setConstraints(agentView, c);

        mainMenu.add(listingView);
        mainMenu.add(resourceView);
        mainMenu.add(agentView);
        listingView.addActionListener(this);
        resourceView.addActionListener(this);
        agentView.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // switch panels here
        System.out.println(actionEvent.getActionCommand());
        switchPane(actionEvent.getActionCommand());
    }

    public void switchPane(String id) {
        currCard.show(frame.getContentPane(), id);
    }
}
