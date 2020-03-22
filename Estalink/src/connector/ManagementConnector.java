package connector;

import model.AgencyModel;
import model.PropertyModel;

public class ManagementConnector extends Connector {
    // Insert, update, delete on PropertyManagement
        // delete isn't needed here because we have on delete cascade.

    public boolean addManagementForProperty(String agency_name, String address) {
        // TODO: Implement this
        return false;
    }

    public boolean updateManagementForProperty(String agency_name, String address) {
        // TODO: Implement this
        return false;
    }

    public AgencyModel getPropertyManagement(String address) {
        // TODO: Implement this
        return null;
    }

    public PropertyModel[] getAllPropertyForAgency(String name) {
        // TODO: Implement this
        return null;
    }
}
