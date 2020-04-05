package connector;

import types.AccountMode;
import types.BusType;
import model.PropertyModel;
import model.ResourceModel;
import types.HospitalType;
import types.ResourceType;

import java.sql.*;
import java.util.ArrayList;

public class ResourcesConnector extends Connector{
    // Access to HasPropertyandResources, and selection on Public Resources
        // Methods:
            // getResourceByID
            // selectResourceByType
            // selectResourceByName
            // selectResourceByProperty
            // updatePropertyResource

    public ResourceModel getResourceByID(int resource_id) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT * FROM public_resources WHERE resource_id = " + resource_id);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM public_resources WHERE resource_id = (?)");

            ps.setInt(1, resource_id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getResourceModel(ps, resultSet);
            }
            lasterr = "Result not found";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ResourceModel[] selectResourceByType(ResourceType resourceType) {
        Connection connection = this.manager.getConnection();
        ArrayList<ResourceModel> resourceModels = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM public_resources WHERE resource_type = (?)");

            int resourceTypeIndex = getResourceTypeIndex(resourceType);
            ps.setInt(1, resourceTypeIndex);
            ResultSet resultSet = ps.executeQuery();

            System.out.println("Executing SELECT * FROM public_resources WHERE resource_type = " + resourceTypeIndex);

            while (resultSet.next()) {
                resourceModels.add(getResourceModel(ps, resultSet));
            }

            ps.close();
            return resourceModels.toArray(new ResourceModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ResourceModel[] selectResourceByName(String name) {
        Connection connection = this.manager.getConnection();
        ArrayList<ResourceModel> resourceModels = new ArrayList<>();
        try {
            System.out.println("Executing SELECT * FROM public_resources WHERE resource_name = " + name);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM public_resources WHERE resource_name = (?)");

            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                resourceModels.add(getResourceModel(ps, resultSet));
            }

            ps.close();
            return resourceModels.toArray(new ResourceModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ResourceModel[] selectResourceByProperty(PropertyModel property) {
        Connection connection = this.manager.getConnection();
        ArrayList<ResourceModel> resourceModels = new ArrayList<>();
        try {
            String addr = property.getAddress();
            System.out.println("Executing SELECT resource_id, resource_name," +
                    "resource_type, transit_type, park_description, hospital_type FROM public_resources, " +
                    "has_property_and_resources WHERE property_address = " + addr + " AND public_resources.resource_id = " +
                    "has_property_and_resources.id");
            PreparedStatement ps = connection.prepareStatement("SELECT resource_id, resource_name," +
                    "resource_type, transit_type, park_description, hospital_type FROM public_resources, " +
                    "has_property_and_resources WHERE property_address = (?) AND public_resources.resource_id = " +
                    "has_property_and_resources.id");

            ps.setString(1, addr);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                resourceModels.add(getResourceModel(ps, resultSet));
            }

            ps.close();
            return resourceModels.toArray(new ResourceModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }


    public boolean updatePropertyResource(String address, int resource_id) {
        // INSERT not Update
        if (!checkMode(AccountMode.ADMIN)) {
            this.lasterr = "Insufficient privilege, ADMIN mode is required to registerAgent";
            return false;
        }

        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO has_property_and_resources VALUES (?, ?)");
            ps.setInt(1, resource_id);
            ps.setString(2, address);

            ps.executeUpdate();
            connection.commit();

            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    private ResourceType getResourceType (int i){
        ResourceType type = ResourceType.GREENWAY;
        switch (i) {
            case 1:
                type = ResourceType.BUS;
                break;
            case 2:
                type = ResourceType.PARK;
                break;
            case 3:
                type = ResourceType.HOSPITAL;
                break;
            case 4:
                type = ResourceType.SKYTRAIN;
                break;
            case 5:
                type = ResourceType.GREENWAY;
                break;
        }
        return type;
    }

    private int getResourceTypeIndex (ResourceType resourceType){
        int index = 0;
        switch (resourceType){
            case BUS:
                index = 1;
                break;
            case PARK:
                index = 2;
                break;
            case HOSPITAL:
                index = 3;
                break;
            case SKYTRAIN:
                index = 4;
                break;
            case GREENWAY:
                index = 5;
                break;
        }
        return index;
    }


    private BusType getBusType (int i){
        BusType type = BusType.REG;
        switch (i) {
            case 1:
                type = BusType.REG;
                break;
            case 2:
                type = BusType.EXPRESS;
                break;
            case 3:
                type = BusType.RAPID;
                break;
        }
        return type;
    }

    private HospitalType getHospitalType (int i) {
        HospitalType type = HospitalType.ANIMAL;
        switch (i) {
            case 1:
                type = HospitalType.WALKIN;
                break;
            case 2:
                type = HospitalType.PUBLIC;
                break;
            case 3:
                type = HospitalType.ANIMAL;
                break;
            case 4:
                type = HospitalType.PHARM;
                break;
        }
        return type;
    }

    private ResourceModel getResourceModel(PreparedStatement ps, ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String resource_name = resultSet.getString(2);
        int resource_type_int = resultSet.getInt(3);
        int bus_type_int = resultSet.getInt(4);
        int hospital_type_int = resultSet.getInt(6);
        ResourceType resource_type = getResourceType(resource_type_int);
        BusType transit_type = getBusType(bus_type_int);
        String description = resultSet.getString(5);
        HospitalType hospital_type = getHospitalType(hospital_type_int);

        return new ResourceModel(id, resource_name, resource_type, transit_type, description,
                hospital_type);
    }


}
