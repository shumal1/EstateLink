package connector;

import model.AgencyModel;
import model.PropertyModel;

import java.sql.*;

public class ManagementConnector extends Connector {
    // Insert, update, delete on PropertyManagement
        // delete isn't needed here because we have on delete cascade.

    public boolean addManagementForProperty(String agency_name, String address) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO property_management VALUES (?, ?)");
            ps.setString(1, agency_name);
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

    public boolean updateManagementForProperty(String agency_name, String property_address) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE property_management SET property_address = ? WHERE agency_name = ?");
            ps.setString(1, property_address);
            ps.setString(2, agency_name);

            ps.executeUpdate();
            connection.commit();

            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public AgencyModel getPropertyManagement(String property_address) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT agency_name FROM property_management WHERE property_address = " + property_address);
            PreparedStatement ps = connection.prepareStatement("SELECT agency_name FROM property_management WHERE property_address = (?)");
            ps.setString(1, property_address);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String agency_name = resultSet.getString(1);
                ps.close();
                AgencyModel agencyModel = this.manager.getAgentConnector().getAgencyByName(agency_name);
                return agencyModel;
            }
            lasterr = "There is no agency with this property address";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public PropertyModel[] getAllPropertyForAgency(String agency_name) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT property_address FROM property_management WHERE agency_name = " + agency_name);
            PreparedStatement ps = connection.prepareStatement("SELECT property_address FROM property_management WHERE agency_name = (?)");
            ps.setString(1, agency_name);

            ResultSet resultSet = ps.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
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
            lasterr = "The agency name is not in the table or the agency does not have any properties";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }
}
