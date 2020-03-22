package connector;

import model.PropertyModel;
import types.AccountMode;

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

}
