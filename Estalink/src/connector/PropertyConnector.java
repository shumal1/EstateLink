package connector;

import model.AgencyModel;
import model.PropertyModel;
import types.AccountMode;

import java.sql.*;

public class PropertyConnector extends Connector {
    // Insert, delete, update to Property, PropertyinCommunity, and Neighbour

    // Property
    public boolean InsertProperty(PropertyModel propertyModel) {
        // TODO: Implement this
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    public PropertyModel getPropertyByAddress(String address) {
        // TODO: Implement this
        return null;
    }

    public boolean UpdatePropertyInfo(PropertyModel propertyModel) {
        // TODO: Implement this
        // select old property by address, and do update.
        // If address is different, also call removeNeighbourForProperty() and deletePropertyFromCommunity()
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    public boolean DeleteProperty(String address) {
        // TODO: Implement this
        // get address, and execute update instead of insert
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        return true;
    }

    // community
    public boolean addPropertyToCommunity(String property_address, String community_name, String community_city) {
        // TODO: Implement this
        return false;
    }

    public boolean removePropertyFromCommunity(String property_address, String community_name, String community_city) {
        // TODO: Implement this
        return false;
    }

    public PropertyModel[] selectPropertyByCommunity(String community_name, String community_city) {
        // TODO: Implement this
        return null;
    }

    public PropertyModel[] selectPropertyByCity(String community_city) {
        // TODO: Implement this
        return null;
    }

    // neighbour
    public PropertyModel[] getNeighbouringProperties(String address) {
        // TODO: Implement this
        return null;
    }

    public PropertyModel[] addNeighbourForProperty(String property_address, String neighbour_address) {
        // TODO: Implement this
        return null;
    }

    public PropertyModel[] removeNeighbourForProperty(String property_address, String neighbour_address) {
        // TODO: Implement this
        return null;
    }

    //Helper Method requested by Jason
    private int maxListingIDForProperty() {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT MAX(listing_id) FROM property");
            PreparedStatement ps = connection.prepareStatement("SELECT MAX(listing_id) FROM property");

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
