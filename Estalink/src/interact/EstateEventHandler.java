package interact;

import types.AccountMode;
import types.ListingType;
import model.PropertyModel;

public class EstateEventHandler {
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
    private static int nextListingID = 1;
    private static int nextAgentID = 1;

    public void changeMode(AccountMode mode, String userID, String password) {
        manager.changeMode(mode, userID, password);
    }

    public static int getNextListingID(){
        return ++nextListingID;
    }

    public static int getNextAgentID() {
        return ++nextAgentID;
    }
}
