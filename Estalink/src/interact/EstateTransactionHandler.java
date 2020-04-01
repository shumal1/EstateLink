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
        return nextListingID++;
    }

    public int getNextAgentID() {
        return nextAgentID++;
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

    // TODO Implement methods here for usage in UI, feel free to update if needed.
    @Override
    public String insertPropertyListing(String property_address, PropertyType property_type, String property_dimension,
                                 String property_postalCode, boolean isDuplex, String property_apartmentNumber, int capacity,
                                 int listing_id, int listing_price, ListingType listing_type, int agent_id) {
        // note that admin has an agent id of 0.
        // in ui, call getCurrentAgentID() to get the id.
        return null;
    }

    @Override
    public String updateListing(int listing_id, int new_price) {
        return null;
    }

    @Override
    public boolean deleteListing(int listing_id) {
        return false;
    }

    @Override
    public JTable getListingByCondition(int id, int price, boolean higher, ListingType type) {
        return null;
    }

    @Override
    public JTable getPropertyByCondition(String address, PropertyType type) {
        return null;
    }

    @Override
    public JTable getResourceByType(ResourceType type) {
        return null;
    }

    @Override
    public JTable getPropertyByResourceType(ResourceType type) {
        return null;
    }

    @Override
    public JTable getPropertyWithResourceID(int id) {
        return null;
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
