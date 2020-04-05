package ui;

import handler.ListingTransactionHandler;
import handler.ResourceTransactionHandler;
import types.AccountMode;
import types.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ResourceFrame extends JFrame implements ActionListener {
    private EstateUI parent;
    private ResourceTransactionHandler handler;
    private JComboBox<String> resourceType;
    private JRadioButton searchResource, searchProperty;
    private JScrollPane leftholder, rightholder;
    private String currkey;
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

        c.gridy = 3;
        c.gridx = 0;
        leftholder = new JScrollPane();
        this.add(leftholder, c);

        c.gridx = 1;
        rightholder = new JScrollPane();
        this.add(rightholder, c);

        // centers the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        this.pack();
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch(actionEvent.getActionCommand()) {
            case "Menu":
                this.setVisible(false);
                parent.switchFrame(EstateUI.states.Menu.name());
                break;
            case "Submit" :
                // TODO SELECT RESOURCE OR PROPERTY BY TYPE
                // Obtain result here
                // Use functions in this.handler to do lookup
                ResourceType type = ResourceType.BUS;
                switch (resourceType.getSelectedItem().toString()){
                    case "GREENWAY":
                        type = ResourceType.GREENWAY;
                        break;
                    case "HOSPITAL":
                        type = ResourceType.HOSPITAL;
                        break;
                    case "SKYTRAIN":
                        type = ResourceType.SKYTRAIN;
                        break;
                    case "PARK":
                        type = ResourceType.PARK;
                        break;
                    default:
                        break;
                }
                JTable result = new JTable(); // stub
                if (searchProperty.isSelected()){
                    result = handler.getPropertyByResourceType(type);
                } else {
                    result = handler.getResourceByType(type);
                }
                leftholder = new JScrollPane(result);
                leftholder.setBounds(10, 150, 200, 100);
                this.add(leftholder);
                this.repaint();
                leftholder.getViewport().removeAll();
                leftholder.getViewport().add(result);
                leftholder.repaint();
                this.pack();

                JTable finalResult = result;
                result.addMouseMotionListener(new MouseMotionListener() {
                    int hoveredRow = -1, hoveredColumn = -1;
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        Point p = e.getPoint();
                        hoveredRow = finalResult.rowAtPoint(p);
                        hoveredColumn = finalResult.columnAtPoint(p);
                        finalResult.setRowSelectionInterval(hoveredRow, hoveredRow);
                        reverseLookup(finalResult.getValueAt(hoveredRow, 0).toString(), searchProperty.isSelected());
                        finalResult.repaint();
                    }
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        hoveredRow = hoveredColumn = -1;
                        finalResult.repaint();
                    }
                });
                break;
        }
    }

    private void reverseLookup(String key, boolean isProperty) {
        // TODO REVERSE LOOKUP
        // Implement this
        if (!key.equals(currkey)) {
            currkey = key;
            this.remove(rightholder);
            JTable result = new JTable();
            if (isProperty){
                int id = Integer.parseInt(key);
                result = this.handler.getPropertyWithResourceID(id);
            } else {
                result = this.handler.getResourceByProperty(key);
            }
            rightholder = new JScrollPane(result);
            rightholder.setBounds(220, 150, 150, 100);
            this.add(rightholder);
            this.repaint();
            // If searching property, then on right panel display all resources that the property has
            // If searching resource, then on right panel display all property that have this resource
            // Use functions in this.handler to do lookup
        }
    }
}
