import model.AgentModel;
import model.ListingType;
import model.PropertyModel;

public class EstateEventHandler {
    // forwards formatted SQL of corresponding event to database
    // Filter based on
        // resource, listing attributes, community
    // Detail of specific building

    // Actions:
        // InsertPropertyListing
        // InsertAgent
        // InsertAgency
        // UpdatePropertyResources
        // UpdatePropertyCommunity
        // UpdatePropertyManagement
        // UpdateNeighbourInformation (select like...)
        // UpdateListingDetail
        // DeleteListing
    private static EstateDatabaseManager manager = EstateDatabaseManager.getInstance();
    private static int nextID = 1;

    public int InsertPropertyListing(PropertyModel property, int agentID, int listingPrice, ListingType listingType){
        manager.InsertProperty(property);
        manager.InsertListing(property.getListingID(), agentID, listingPrice, listingType);
        return 0;
    }

    public static int getnextID(){
        return ++nextID;
    }

}
