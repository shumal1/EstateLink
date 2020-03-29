package interact;

import handler.TransactionHandler;
import model.AgentModel;
import model.Model;
import types.AccountMode;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class EstateTransactionHandler implements TransactionHandler {
    // This class should contain the corresponding handler for UI interactions, and call the corresponding methods in
    // database manager to perform desired update.

    // Possible actions include:
        // As customer:
            // Search property
                // By resources
                // By agency
                // By Community
                // By city
                // By type
                // By price
            // Search public resources
            // Search agency/ agent

        // As an agent:
            // InsertPropertyListing
            // UpdatePropertyResources
            // UpdatePropertyCommunity
            // UpdatePropertyManagement
            // UpdateNeighbourInformation (select like...)
            // UpdateListingDetail
            // Take Down Listing

        // As administrator:
            // InsertAgent
            // InsertAgency
    private static EstateDatabaseManager manager = EstateDatabaseManager.getInstance();
    private int nextListingID;
    private int nextAgentID;

    public EstateTransactionHandler() {
        this.nextListingID = manager.getAgentConnector().getNextAgentID();
    }
    public boolean changeMode(AccountMode mode, String userID, String password) {
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

    public AccountMode getCurrentMode() {
        return manager.getCurrentMode();
    }


    public int getNextListingID(){
        return ++nextListingID;
    }

    public int getNextAgentID() {
        return ++nextAgentID;
    }

    @Override
    public JTable selectAgentByID(String id) {
        JTable result;
        Model[] list;
        try {
            list = new Model[]{manager.getAgentConnector().getAgentByID(Integer.parseInt(id))};
        } catch (NumberFormatException e) {
            list = new Model[0];
        }

        result = constructTable(list);
        return result;
    }

    @Override
    public JTable selectAgencyByName(String id) {
        JTable result;
        Model[] list;
        try {
            list = new Model[]{manager.getAgentConnector().getAgencyByName(id)};
        } catch (NumberFormatException e) {
            list = new Model[0];
        }

        result = constructTable(list);
        return result;
    }

    @Override
    public boolean insertAgent() {
        return false;
    }

    private JTable constructTable(Model[] models) {
        JTable result;
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
