package interact;

import handler.AgentTransactionHandler;
import handler.ListingTransactionHandler;
import handler.ResourceTransactionHandler;
import model.*;
import types.AccountMode;
import types.ListingType;
import types.PropertyType;
import types.ResourceType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EstateTransactionHandler implements AgentTransactionHandler, ListingTransactionHandler, ResourceTransactionHandler {
    private static EstateDatabaseManager manager = EstateDatabaseManager.getInstance();
    private int nextListingID;
    private int nextAgentID;
    private String currUID;

    private static final String SUCCESS_RESPONSE  = "SQL_SUCCESS, requested update completed";

    public boolean changeMode(AccountMode mode, String userID, String password) {
        this.currUID = userID;
        return manager.changeMode(mode, userID, password);
    }

    public static AccountMode getModeByName(String name) {
        for (AccountMode accMode: AccountMode.values()) {
            if (name.equals(accMode.name())) {
                return accMode;
            }
        }
        return AccountMode.INVALID;
    }

    public boolean dbLogon(String uid, String pwd) {
        boolean result = manager.InitializeConnection(uid, pwd);
        if (result) {
            this.nextAgentID = manager.getAgentConnector().getNextAgentID();
            this.nextListingID = manager.getListingConnector().getNextListingID();
        }
        return result;
    }

    public AccountMode getCurrentMode(){
        return manager.getCurrentMode();
    }

    public int getNextListingID(){
        nextListingID++;
        return nextListingID;
    }

    public int getNextAgentID() {
        nextAgentID++;
        return nextAgentID;
    }

    @Override
    public JTable selectAgentByID(String id) {
        Model[] list;
        if (id.equals("*")) {
            list = manager.getAgentConnector().getAllAgents();
        } else {
            try {
                list = new Model[]{manager.getAgentConnector().getAgentByID(Integer.parseInt(id))};
            } catch (NumberFormatException e) {
                list = new Model[0];
            }
        }

        return constructTable(list);
    }

    @Override
    public JTable selectAgencyByName(String id) {
        Model[] list;
        list = new Model[]{manager.getAgentConnector().getAgencyByName(id)};
        return constructTable(list);
    }

    @Override
    public JTable selectAgencyByID(String id) {
        Model[] list;
        try {
            list = new Model[]{manager.getAgentConnector().getAgencyByAgentID(Integer.parseInt(id))};
        } catch (NumberFormatException e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public String insertAgent(String agent_name, String agent_phoneNumber, String agent_agencyName) {
        AgentModel model = new AgentModel(this.getNextAgentID(), agent_name, agent_agencyName, agent_phoneNumber);
        if (manager.getAgentConnector().registerAgent(model)) {
            return SUCCESS_RESPONSE;
        } else {
            return manager.getAgentConnector().getLastError();
        }

    }

    // note that admin has an agent id of 0.
    // in ui, call getCurrentAgentID() to get the id.
    @Override
    public String insertPropertyListing(String property_address, PropertyType property_type, String property_dimension,
                                 String property_postalCode, boolean isDuplex, String property_apartmentNumber, int capacity,
                                 int listing_price, ListingType listing_type, int agent_id) {
        int apartmentNumber = 0;
        try {
            apartmentNumber = Integer.parseInt(property_apartmentNumber);
        } catch (NumberFormatException e) {
            if (property_type == PropertyType.Apartment) {
                // otherwise ignore;
                return "Invalid apartment number";
            }
        }

        int listingID = this.getNextListingID();
        PropertyModel propertyModel = new PropertyModel(property_address, property_dimension, property_postalCode, listingID,
        isDuplex, apartmentNumber, capacity, property_type);

        ListingModel listingModel = new ListingModel(listingID, listing_price,0, agent_id, listing_type);
        // do not invert, listing is the parent table
        if (manager.getListingConnector().InsertListing(listingModel) && manager.getPropertyConnector().InsertProperty(propertyModel)) {
            return SUCCESS_RESPONSE;
        }
        return manager.getListingConnector().getLastError() + " and " + manager.getPropertyConnector();
    }

    @Override
    public String updateListing(int listing_id, int new_price) {
        if(manager.getListingConnector().UpdateListingPrice(listing_id, new_price)) {
            return SUCCESS_RESPONSE;
        }
        return manager.getListingConnector().getLastError();
    }

    @Override
    public String deleteListing(int listing_id) {
        if(manager.getListingConnector().deleteListing(listing_id)) {
            return SUCCESS_RESPONSE;
        }
        return manager.getListingConnector().getLastError();
    }

    @Override
    public JTable getListingByCondition(String id, String price, boolean higher, ListingType type) {
        Model[] list;
        try {
            ListingModel[] listingModels = manager.getListingConnector().selectListingByCondition(id, price, higher, type);
            list = listingModels;
        } catch (Exception e) {
            list = new Model[0];
        }

        return constructTable(list);
    }

    @Override
    public JTable getPropertyByCondition(String address, PropertyType type) {
        Model[] list;
        if (address != null && !address.isEmpty()) {
            list = new Model[]{manager.getPropertyConnector().getPropertyByAddress(address)};
        } else {
            try{
                PropertyModel[] properties = manager.getPropertyConnector().selectPropertybyPropertyType(type);
                list = properties;
            } catch (Exception e){
                list = new Model[0];
            }
        }
        return constructTable(list);
    }

    @Override
    public JTable getResourceByType(ResourceType type) {
        Model[] list;
        try{
            ResourceModel[] resources = manager.getResourcesConnector().selectResourceByType(type);
            list =  resources;
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getPropertyByResourceType(ResourceType type) {
        Model[] list;
        try{
            ResourceModel[] resources = manager.getResourcesConnector().selectResourceByType(type);
            list = resources;
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getPropertyWithResourceID(int id) {
        Model[] list;
        try{
            ListingModel[] propertyModels = manager.getListingConnector().selectListingByResource(id);
            list = propertyModels;
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getResourceByProperty(String address) {
        Model[] list;
        try{
            list = manager.getResourcesConnector().selectResourceByProperty(address);
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getCommuterProperties() {
        Model[] list;
        list = manager.getPropertyConnector().selectCommuterProperties();
        return constructTable(list);
    }

    @Override
    public JTable getPropertyByListing(int id) {
        Model[] list;
        try{
            PropertyModel propertyModel = manager.getPropertyConnector().selectPropertybyListingID(id);
            list = new Model[]{propertyModel};;
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getListingByProperty(String address) {
        Model[] list;
        try{
            ListingModel listingModel = manager.getListingConnector().selectListingByProperty(address);
            list = new Model[]{listingModel};;
        } catch (Exception e) {
            list = new Model[0];
        }
        return constructTable(list);
    }

    @Override
    public JTable getListingStatistics() {
        Model[] list;
        list = new Model[]{manager.getListingConnector().getStatistics()};
        return constructTable(list);
    }

    @Override
    public JTable getListingStatisticsGroup() {
        Model[] list = manager.getListingConnector().getGroupStatistics();
        return constructTable(list);
    }

    @Override
    public int getCurrentAgentID() {
        switch (manager.getCurrentMode()) {
            case ADMIN:
                return 0;
            case AGENT:
                try {
                    return Integer.parseInt(currUID);
                } catch (NumberFormatException e) {
                    // should not happen! All logged in agent must have an integer uid by our login process.
                    return -1;
                }
            default:
                return -1;
        }
    }

    // IMPORTANT! USE THIS HELPER TO RETURN JTABLE
    private JTable constructTable(Model[] models) {
        JTable result;
        if (models != null) {
            ArrayList<Model> modelArray = new ArrayList<>(Arrays.asList(models));
            modelArray.removeAll(Collections.singleton(null));
            models = modelArray.toArray(new Model[0]);
        }

        if (models.length > 0) {
            ArrayList<String[]> data = new ArrayList<String[]>();
            for (Model model : models) {
                data.add(model.getData());
            }
            result = new JTable(data.toArray(new String[0][]), models[0].getFieldNames());
        } else {
            String[] columnNames = {"Error"};
            String[][] message = {{"No rows returned"}};
            result = new JTable(message, columnNames);
        }
        return result;
    }

}
