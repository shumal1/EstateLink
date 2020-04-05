package connector;

import model.AgencyModel;
import model.PropertyModel;
import model.ResourceModel;
import types.AccountMode;
import types.PropertyType;

import java.sql.*;

public class PropertyConnector extends Connector {
    // Insert, delete, update to Property, PropertyinCommunity, and Neighbour

    // Property
    public boolean InsertProperty(PropertyModel propertyModel) {
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }

        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO property VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, propertyModel.getAddress());
            ps.setInt(2, propertyModel.getListingID());
            ps.setString(3, propertyModel.getType().toString());
            ps.setString(4, propertyModel.getDimension());
            ps.setString(5, propertyModel.getPostalCode());
            ps.setInt(6, propertyModel.isDuplex() ? 1 : 0);
            ps.setInt(7, propertyModel.getApartmentNumber());
            ps.setInt(8, propertyModel.getCapacity());

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public PropertyModel getPropertyByAddress(String address) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT * FROM property WHERE property_address = " + address);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM property WHERE property_address = (?)");
            ps.setString(1, address);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String property_address = resultSet.getString(1);
                int listing_id = resultSet.getInt(2);
                PropertyType property_type = PropertyType.valueOf(resultSet.getString(3));
                String dimension = resultSet.getString(4);
                String postal_code = resultSet.getString(5);
                boolean is_duplex = false;
                if(resultSet.getInt(6) == 1) {
                    is_duplex = true;
                }

                int apartment_number = resultSet.getInt(7);
                int capacity = resultSet.getInt(8);

                ps.close();
                PropertyModel propertyModel = new PropertyModel(property_address, dimension, postal_code, listing_id, is_duplex,
                apartment_number, capacity, property_type);
                return propertyModel;
            }
            lasterr = "There is no property with this property address";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public boolean UpdatePropertyInfo(PropertyModel propertyModel) {
        // select old property by address and listing id, and do update.
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE property SET property_type = ?, dimension = ?, postal_code = ?," +
                    "is_duplex = ?, apartment_number = ?, capcity = ? WHERE property_address = ? AND listing_id = ?");

            ps.setString(1, propertyModel.getType().toString());
            ps.setString(2, propertyModel.getDimension());
            ps.setString(3, propertyModel.getPostalCode());
            ps.setInt(4, propertyModel.isDuplex() ? 1 : 0);
            ps.setInt(5, propertyModel.getApartmentNumber());
            ps.setInt(6, propertyModel.getCapacity());
            ps.setString(7, propertyModel.getAddress());
            ps.setInt(8, propertyModel.getListingID());

            ps.executeUpdate();
            connection.commit();

            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public boolean DeleteProperty(String address) {
        // get address, and execute update instead of insert
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        // DELETE FROM table_name WHERE condition;
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM property WHERE property_address = (?)");
            ps.setString(1, address);

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public PropertyModel[] selectPropertybyPropertyType(PropertyType type) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT property_address FROM property WHERE property_type = " + type);
            PreparedStatement ps = connection.prepareStatement("SELECT property_address FROM property WHERE property_type = (?)");
            ps.setString(1, type.toString());

            ResultSet resultSet = ps.executeQuery();
            int totalRowCount = 0;
            if(resultSet.last()) {
                totalRowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            PropertyModel[] propertyModels = new PropertyModel[totalRowCount];
            while (resultSet.next()) {
                String property_address = resultSet.getString(1);
                PropertyModel propertyModel = this.manager.getPropertyConnector().getPropertyByAddress(property_address);
                propertyModels[resultSet.getRow()] = propertyModel;
            }
            ps.close();
            if(propertyModels.length != 0) {
                return propertyModels;
            }
            lasterr = "There is no property with this property address";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    // community
    public boolean addPropertyToCommunity(String property_address, String community_name, String community_city) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO property_in_community VALUES (?, ?, ?)");
            ps.setString(1, property_address);
            ps.setString(2, community_name);
            ps.setString(3, community_city);

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public boolean removePropertyFromCommunity(String property_address, String community_name, String community_city) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM property_in_community WHERE property_address = ? " +
                    "AND community_name = ? AND community_city = ?");
            ps.setString(1, property_address);
            ps.setString(2, community_name);
            ps.setString(3, community_city);

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public PropertyModel[] selectPropertyByResource(ResourceModel resourceModel) {
        Connection connection = this.manager.getConnection();

        try {
            System.out.println("Executing SELECT listing_id FROM has_property_and_resources WHERE resource_id = " + resourceModel.getResource_ID());
            PreparedStatement ps = connection.prepareStatement("SELECT listing_id FROM has_property_and_resources WHERE resource_id = ?");
            ps.setInt(1, resourceModel.getResource_ID());
            ResultSet resultSet = ps.executeQuery();
            int totalRowCount = 0;

            if(resultSet.last()) {
                totalRowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            PropertyModel[] propertyModels = new PropertyModel[totalRowCount];
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                propertyModels[resultSet.getRow()] = selectPropertybyListingID(id);
            }

            return propertyModels;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public PropertyModel[] selectPropertyByCommunity(String community_name, String community_city) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT property_address FROM property_in_community WHERE community_name = "
                    + community_name + "AND community_city = " + community_city);
            PreparedStatement ps = connection.prepareStatement("SELECT property_address " +
                    "FROM property_in_community WHERE community_name = ? AND community_city = ?");
            ps.setString(1, community_name);
            ps.setString(2, community_city);

            ResultSet resultSet = ps.executeQuery();

            int totalRowCount = 0;
            if(resultSet.last()) {
                totalRowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            PropertyModel[] propertyModels = new PropertyModel[totalRowCount];
            while (resultSet.next()) {
                String property_address = resultSet.getString(1);
                PropertyModel propertyModel = this.manager.getPropertyConnector().getPropertyByAddress(property_address);
                propertyModels[resultSet.getRow()] = propertyModel;
            }
            ps.close();
            if(propertyModels.length != 0) {
                return propertyModels;
            }
            lasterr = "The property is not in the table or the community does not have any properties";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public PropertyModel[] selectPropertyByCity(String community_city) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT property_address FROM property_in_community WHERE community_city = " + community_city);
            PreparedStatement ps = connection.prepareStatement("SELECT property_address " +
                    "FROM property_in_community WHERE community_city = ?");
            ps.setString(1, community_city);

            ResultSet resultSet = ps.executeQuery();

            int totalRowCount = 0;
            if(resultSet.last()) {
                totalRowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            PropertyModel[] propertyModels = new PropertyModel[totalRowCount];
            while (resultSet.next()) {
                String property_address = resultSet.getString(1);
                PropertyModel propertyModel = this.manager.getPropertyConnector().getPropertyByAddress(property_address);
                propertyModels[resultSet.getRow()] = propertyModel;
            }
            ps.close();
            if(propertyModels.length != 0) {
                return propertyModels;
            }
            lasterr = "The property is not in the table or the community does not have any properties";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    // neighbour
    public PropertyModel[] getNeighbouringProperties(String address) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT neighbour_address FROM neighbour WHERE building_address = " + address);
            PreparedStatement ps = connection.prepareStatement("SELECT neighbour_address " +
                    "FROM neighbour WHERE building_address = ?");
            ps.setString(1, address);

            ResultSet resultSet = ps.executeQuery();

            int totalRowCount = 0;
            if(resultSet.last()) {
                totalRowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            PropertyModel[] propertyModels = new PropertyModel[totalRowCount];
            while (resultSet.next()) {
                String property_address = resultSet.getString(1);
                PropertyModel propertyModel = this.manager.getPropertyConnector().getPropertyByAddress(property_address);
                propertyModels[resultSet.getRow()] = propertyModel;
            }
            ps.close();
            if(propertyModels.length != 0) {
                return propertyModels;
            }
            lasterr = "The property does not have any neighbour";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public boolean addNeighbourForProperty(String property_address, String neighbour_address) {
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }

        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO neighbour VALUES (?, ?)");
            ps.setString(1, property_address);
            ps.setString(2, neighbour_address);

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public boolean removeNeighbourForProperty(String property_address, String neighbour_address) {
        if (!checkMode(AccountMode.AGENT)) {
            return false;
        }
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM neighbour WHERE property_address = ? " +
                    "AND neighbour_address = ?");
            ps.setString(1, property_address);
            ps.setString(2, neighbour_address);

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public PropertyModel selectPropertybyListingID(int id) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT * FROM property WHERE listing_id = " + id);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM property WHERE listing_id = (?)");
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String property_address = resultSet.getString(1);
                int listing_id = resultSet.getInt(2);
                PropertyType property_type = PropertyType.valueOf(resultSet.getString(3));
                String dimension = resultSet.getString(4);
                String postal_code = resultSet.getString(5);
                boolean is_duplex = false;
                if(resultSet.getInt(6) == 1) {
                    is_duplex = true;
                }

                int apartment_number = resultSet.getInt(7);
                int capacity = resultSet.getInt(8);

                ps.close();
                PropertyModel propertyModel = new PropertyModel(property_address, dimension, postal_code, listing_id, is_duplex,
                        apartment_number, capacity, property_type);
                return propertyModel;
            }
            lasterr = "There is no property with this property address";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }
}
