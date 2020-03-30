package connector;

import model.ListingModel;
import types.AccountMode;
import types.ListingType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    //Helper Method requested by Jason
    public int getNextListingID() {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT MAX(listing_id) FROM Listings");
            PreparedStatement ps = connection.prepareStatement("SELECT MAX(listing_id) FROM Listings");

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int maxID = resultSet.getInt(1);
                ps.close();
                return maxID;
            }
            lasterr = "max listingID not found";
            return -1;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return -1;
        }
    }
}
