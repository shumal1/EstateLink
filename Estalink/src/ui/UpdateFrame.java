package ui;

import connector.AgentConnector;
import connector.ListingConnector;
import handler.ListingTransactionHandler;
import handler.TransactionHandler;
import interact.EstateTransactionHandler;
import types.ListingType;
import types.PropertyType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateFrame extends JFrame implements ActionListener {

    private EstateUI parent;
    private ListingTransactionHandler handler;
    private JPanel insertPanel, deletePanel, updatePanel;
    private JTextField addressField, deleteID, updateID, dimensionField, postalCodeField, apartmentNumberField, capacityField, priceField;
    private JComboBox<String> operation, propertyType, listingType, isDuplex;
    private JLabel isDuplexPrompt, capacityPrompt, apartmentPrompt;

    public UpdateFrame(EstateUI parent, ListingTransactionHandler handler) {
        this.parent = parent;
        this.handler = handler;
        setupPanel();
    }

    private void setupPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gb);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 12);
        JLabel typePrompt = new JLabel("Please select your operation: ");
        this.add(typePrompt);

        c. gridx = 1;
        String[] resourceTypes = {"INSERT", "DELETE", "UPDATE"};
        operation = new JComboBox<>(resourceTypes);
        operation.setSelectedIndex(0);
        operation.addActionListener(this);
        this.add(operation, c);

        c.gridx = 2;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.EAST;
        JButton mainMenu = new JButton("Menu");
        mainMenu.addActionListener(this);
        this.add(mainMenu, c);;

        c.gridy = 1;
        c.gridx = 0;
        c.weighty = 0.5;
        setupInsertPanel();
        setupDeletePanel();
        setupUpdatePanel();
        deletePanel.setVisible(false);
        updatePanel.setVisible(false);
        this.add(insertPanel, c);
        this.add(deletePanel, c);
        this.add(updatePanel, c);

        c.gridy = 2;
        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        this.add(submit, c);

        // centers the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        this.pack();
        this.setVisible(false);
    }

    private void setupInsertPanel(){
        insertPanel = new JPanel();
        insertPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 12);
        c.anchor = GridBagConstraints.WEST;
        JLabel idPrompt = new JLabel("Address: ");
        insertPanel.add(idPrompt, c);

        c.gridx = 1;
        addressField = new JTextField(10);
        insertPanel.add(addressField, c);

        c.gridy = 1;
        c.gridx = 0;

        JLabel typePrompt = new JLabel("Property type: ");
        insertPanel.add(typePrompt, c);

        c.gridx = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        String[] propertyTypes = {"HOUSE", "APARTMENT", "OFFICE"};
        propertyType = new JComboBox<>(propertyTypes);
        propertyType.setSelectedIndex(0);
        propertyType.addActionListener(this);
        insertPanel.add(propertyType, c);

        c.gridy = 2;
        c.gridx = 0;

        JLabel listingTypePrompt = new JLabel("Listing type: ");
        insertPanel.add(listingTypePrompt, c);

        c.gridx = 1;
        c.gridwidth = GridBagConstraints.RELATIVE;
        String[] listingTypes = {"SELL", "RENTAL"};
        listingType = new JComboBox<>(listingTypes);
        listingType.setSelectedIndex(0);
        insertPanel.add(listingType, c);

        c.gridy = 3;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 12);
        JLabel dimensionPrompt = new JLabel("Dimension: ");
        insertPanel.add(dimensionPrompt, c);

        c.gridx = 1;
        dimensionField = new JTextField(10);
        insertPanel.add(dimensionField, c);

        c.gridy = 4;
        c.gridx = 0;
        JLabel postalPrompt = new JLabel("Postal Code: ");
        insertPanel.add(postalPrompt, c);

        c.gridx = 1;
        postalCodeField = new JTextField(10);
        insertPanel.add(postalCodeField, c);

        c.gridy = 5;
        c.gridx = 0;
        capacityPrompt = new JLabel("Capacity: ");
        capacityPrompt.setVisible(false);
        insertPanel.add(capacityPrompt, c);

        apartmentPrompt = new JLabel("Apartment#: ");
        apartmentPrompt.setVisible(false);
        insertPanel.add(apartmentPrompt, c);

        isDuplexPrompt = new JLabel("Is Duplex: ");
        insertPanel.add(isDuplexPrompt, c);


        c.gridx = 1;
        capacityField = new JTextField(10);
        capacityField.setVisible(false);
        insertPanel.add(capacityField, c);

        apartmentNumberField = new JTextField(10);
        apartmentNumberField.setVisible(false);
        insertPanel.add(apartmentNumberField, c);

        String[] duplexKeys = {"YES", "NO"};
        isDuplex = new JComboBox<>(duplexKeys);
        isDuplex.setSelectedIndex(0);
        insertPanel.add(isDuplex, c);
    }

    private void setupDeletePanel(){
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 12);
        c.anchor = GridBagConstraints.WEST;
        JLabel idPrompt = new JLabel("Listing ID: ");
        deletePanel.add(idPrompt, c);

        c.gridx = 1;
        deleteID = new JTextField(10);
        deletePanel.add(deleteID, c);
    }

    private void setupUpdatePanel(){
        updatePanel = new JPanel();
        updatePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 12);
        c.anchor = GridBagConstraints.WEST;
        JLabel idPrompt = new JLabel("Listing ID: ");
        updatePanel.add(idPrompt, c);

        c.gridx = 1;
        updateID = new JTextField(10);
        updatePanel.add(updateID, c);

        c.gridy = 1;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 12);
        c.anchor = GridBagConstraints.WEST;
        JLabel pricePrompt = new JLabel("New Price: ");
        updatePanel.add(pricePrompt, c);

        c.gridx = 1;
        priceField = new JTextField(10);
        updatePanel.add(priceField, c);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        String response;
        switch(actionEvent.getActionCommand()) {
            case "comboBoxChanged" :
                if (actionEvent.getSource() == operation) {
                    disableAllPanels();
                    switch (operation.getSelectedItem().toString()) {
                        case "INSERT":
                            insertPanel.setVisible(true);
                            break;
                        case "DELETE":
                            deletePanel.setVisible(true);
                            break;
                        case "UPDATE":
                            updatePanel.setVisible(true);
                            break;
                    }
                } else if (actionEvent.getSource() == propertyType) {
                    switch (propertyType.getSelectedItem().toString()) {
                        case "HOUSE":
                            disableAllPrompts();
                            isDuplexPrompt.setVisible(true);
                            isDuplex.setVisible(true);
                            break;
                        case "APARTMENT":
                            disableAllPrompts();
                            apartmentPrompt.setVisible(true);
                            apartmentNumberField.setVisible(true);
                            break;
                        case "OFFICE":
                            disableAllPrompts();
                            capacityPrompt.setVisible(true);
                            capacityField.setVisible(true);
                            break;
                    }
                }
                this.pack();
                this.repaint();
                break;
            case "Submit" :
                switch (operation.getSelectedItem().toString()){
                    case "INSERT":
                        PropertyType type = PropertyType.House;
                        boolean duplex = false;
                        String apartmentNum = "";
                        int capacity = 0;
                        switch (propertyType.getSelectedItem().toString()){
                            case "APARTMENT":
                                type = PropertyType.Apartment;
                                apartmentNum = apartmentNumberField.getText();
                                break;
                            case "OFFICE":
                                type = PropertyType.Office;
                                capacity = Integer.parseInt(capacityField.getText());
                                break;
                            default:
                                duplex = Boolean.parseBoolean(isDuplex.getSelectedItem().toString());
                                break;
                        }
                        int agentID = handler.getCurrentAgentID();
                        ListingType listing = ListingType.RENTAL;
                        if (listingType.getSelectedItem().toString().equals("SELL")){
                            listing = ListingType.SELLING;
                        }
                        response =
                        handler.insertPropertyListing(addressField.getText(), type,
                                dimensionField.getText(), postalCodeField.getText(), duplex, apartmentNum, capacity, 0, listing, agentID);

                        JOptionPane.showMessageDialog(this, response);
                        break;
                    case "DELETE":
                        response = handler.deleteListing(Integer.parseInt(deleteID.getText()));
                        JOptionPane.showMessageDialog(this, response);
                        break;
                    case "UPDATE":
                        response = handler.updateListing(Integer.parseInt(updateID.getText()), Integer.parseInt(priceField.getText()));
                        JOptionPane.showMessageDialog(this, response);
                        break;
                }

                //TODO handle transaction here
                break;
            case "Menu" :
                this.setVisible(false);
                parent.switchFrame(EstateUI.states.Menu.name());
                break;
        }
    }

    private void disableAllPanels(){
        insertPanel.setVisible(false);
        deletePanel.setVisible(false);
        updatePanel.setVisible(false);
    }

    private void disableAllPrompts(){
        capacityPrompt.setVisible(false);
        isDuplexPrompt.setVisible(false);
        apartmentPrompt.setVisible(false);

        capacityField.setVisible(false);
        isDuplex.setVisible(false);
        apartmentNumberField.setVisible(false);
    }
}
