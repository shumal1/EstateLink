package connector;

import model.PropertyModel;
import model.ResourceModel;
import types.AccountMode;
import types.ResourceType;

public class ResourcesConnector extends Connector{
    // Access to HasPropertyandResources, and selection on Public Resources
        // Methods:
            // getResourceByID
            // selectResourceByType
            // selectResourceByName
            // selectResourceByProperty
            // updatePropertyResource

    public ResourceModel getResourceByID(int resource_id) {
        // TODO: Implement this
        return null;
    }

    public ResourceModel[] selectResourceByType(ResourceType resourceType) {
        // TODO: Implement this
        return null;
    }

    public ResourceModel[] selectResourceByName(String name) {
        // TODO: Implement this
        return null;
    }

    public ResourceModel[] selectResourceByProperty(PropertyModel property) {
        // TODO: Implement this
        return null;
    }

    public boolean updatePropertyResource(String address, int resource_id) {
        // TODO: Implement this
        return false;
    }
}
