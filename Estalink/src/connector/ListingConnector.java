package connector;

import model.ListingModel;
import types.AccountMode;
import types.ListingType;

public class ListingConnector extends Connector{
    // Insert, update, delete on Listing

    public boolean InsertListing(ListingModel listingModel) {
        // TODO: Implement this
        // call addManagementForProperty() here
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    public boolean UpdateListingPrice(int newPrice) {
        // TODO: Implement this
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    public boolean UpdateListingAgent(int agentID) {
        // TODO: Implement this
        // call updateManagementForProperty here
        if (!checkMode(AccountMode.ADMIN)) {
            return false;
        }
        return true;
    }


    public boolean deleteListing(int listing_id) {
        // TODO: Implement this
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    public ListingModel[] getListingsByType(ListingType listingType) {
        // TODO: Implement this
        return null;
    }

    public ListingModel[] getListingByPrice(ListingType type, String filter, int price) {
        // TODO: Implement this
        return null;
    }

    public ListingModel[] getListingByPercentageChange(ListingType type, int price) {
        // TODO: Implement this
        return null;
    }
}
