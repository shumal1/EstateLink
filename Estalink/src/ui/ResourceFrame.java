package ui;

import handler.ListingTransactionHandler;
import handler.ResourceTransactionHandler;
import types.AccountMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourceFrame extends JFrame implements ActionListener {
    private EstateUI parent;
    private ResourceTransactionHandler handler;
    private JComboBox<String> resourceType;
    private JRadioButton searchResource, searchProperty;
    public ResourceFrame(EstateUI parent, ResourceTransactionHandler handler) {
        this.parent = parent;
        this.handler = handler;
        setupPanel();
    }

    private void setupPanel(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gb);


        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 12);
        JLabel typePrompt = new JLabel("Please select resource and search type: ");
        this.add(typePrompt);

        c. gridy = 1;
        String[] resourceTypes = {"BUS", "PARK", "HOSPITAL", "SKYTRAM", "GREENWAY"};
        resourceType = new JComboBox<>(resourceTypes);
        resourceType.setSelectedIndex(0);
        this.add(resourceType, c);

        searchResource = new JRadioButton("Resource");
        searchProperty = new JRadioButton("Property");
        ButtonGroup g = new ButtonGroup();
        g.add(searchResource);
        g.add(searchProperty);
        g.setSelected(searchResource.getModel(), true);

        c.gridx = 1;
        this.add(searchResource, c);
        c.gridx = 2;
        this.add(searchProperty, c);


        c.gridy = 2;
        c.gridx = 0;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.CENTER;
        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        this.add(submit, c);

        c.gridy = 2;
        c.gridx = 1;
        c.anchor = GridBagConstraints.EAST;
        JButton mainMenu = new JButton("Menu");
        mainMenu.addActionListener(this);
        this.add(mainMenu, c);;

        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        this.pack();
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
