package connector;

import model.AgencyEmployeeModel;
import model.AgentListingModel;
import types.AccountMode;
import model.AgencyModel;
import model.AgentModel;
import types.ListingType;

import java.sql.*;
import java.util.ArrayList;

public class AgentConnector extends Connector{
    // Access to Agency and AgencyEmployee
        // Methods:
            // registerAgent (admin mode)
            // getAgentByID (returns agent object)
            // getAgencyByName (returns agency object)
            // getAgencyByAgentID (returns name of agency for the specific agent)

    public boolean checkAgentExists(String username, String password) {
        // Select Count(*) from Agency_Employee where Agent_Name = username and Agent_ID = password;
        // used for login
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) from Agency_Employee where Agent_Name = (?) and Agent_ID = (?)");

            ps.setString(1, username);
            ps.setInt(2, Integer.parseInt(password));

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    ps.close();
                    return true;
                }
            }

            ps.close();
            lasterr = "No matching agent found!";
            return false;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getNextAgentID() {
        // Select Count(*) from Agency_Employee where Agent_Name = username and Agent_ID = password;
        // used for login
        Connection connection = this.manager.getConnection();
        int result = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT MAX(Agent_id) from Agency_Employee");

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                result = resultSet.getInt(1);
                System.out.println("returning " + result);
            }

            ps.close();
            return result;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return -1;
        }
    }

    public boolean registerAgent (AgentModel agentModel) {
        if (!checkMode(AccountMode.ADMIN)) {
            this.lasterr = "Insufficient privilege, ADMIN mode is required to registerAgent";
            return false;
        }

        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Agency_Employee VALUES (?, ?, ?, ?)");
            ps.setInt(1, agentModel.getAgentID());
            ps.setString(2, agentModel.getAgentName());
            ps.setString(3, agentModel.getAgencyName());
            ps.setString(4, agentModel.getPhoneNumber());

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public AgencyEmployeeModel[] getAllAgents() {
        Connection connection = this.manager.getConnection();
        ArrayList<AgencyEmployeeModel> models = new ArrayList<>();
        try {
            System.out.println("Executing SELECT * FROM Agency_Employee natural join Agency");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Agency_Employee natural join Agency");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String agent_agencyName = resultSet.getString(1);
                int agent_id = resultSet.getInt(2);
                String agent_name = resultSet.getString(3);
                String agent_phoneNumber = resultSet.getString(4);
                String agency_address = resultSet.getString(5);
                String agency_description = resultSet.getString(6);
                double agency_rating = resultSet.getDouble(7);
                models.add(new AgencyEmployeeModel(agent_agencyName, agent_id, agent_name, agent_phoneNumber,
                        agency_address, agency_description, agency_rating));
            }

            ps.close();
            return models.toArray(new AgencyEmployeeModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public AgentListingModel[] getAllAgentListings() {
        Connection connection = this.manager.getConnection();
        ArrayList<AgentListingModel> models = new ArrayList<>();
        try {
            System.out.println("Executing SELECT * FROM Agency_Employee natural join Listing");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Agency_Employee natural join Listing");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int agent_id = resultSet.getInt(1);
                String agent_name = resultSet.getString(2);
                String agent_agencyName = resultSet.getString(3);
                String agent_phoneNumber = resultSet.getString(4);
                int listing_id = resultSet.getInt(5);
                int listing_price = resultSet.getInt(6);
                int listing_histprice = resultSet.getInt(7);
                String listing_typeID = resultSet.getString(8);
                ListingType listing_type = ListingType.SELLING;
                switch (listing_typeID) {
                    case "SELL":
                        listing_type = ListingType.SELLING;
                        break;
                    case "RENTAL":
                        listing_type = ListingType.RENTAL;
                        break;
                }
                models.add(new AgentListingModel(agent_id, agent_name, agent_agencyName, agent_phoneNumber,
                        listing_id, listing_price, listing_histprice, listing_type));
            }

            ps.close();
            return models.toArray(new AgentListingModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public AgentModel getAgentByID (int lookup_id) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT * FROM Agency_Employee WHERE agent_id = " + lookup_id);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Agency_Employee WHERE agent_id = (?)");
            ps.setInt(1, lookup_id);

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            if (resultSet.next()) {
                int agent_id = resultSet.getInt(1);
                String agent_name = resultSet.getString(2);
                String agent_agencyName = resultSet.getString(3);
                String agent_phoneNumber = resultSet.getString(4);

                ps.close();
                return new AgentModel(agent_id, agent_name, agent_agencyName, agent_phoneNumber);
            }

            lasterr = "Result not found";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public AgencyModel getAgencyByName (String lookup_name) {
        System.out.println("Executing SELECT * FROM Agency WHERE Agency_Name = " + lookup_name);
        Connection connection = this.manager.getConnection();
        PreparedStatement ps;
        try {
            if (lookup_name.equals("*")) {
                ps = connection.prepareStatement("SELECT * FROM Agency");
            } else {
                ps = connection.prepareStatement("SELECT * FROM Agency WHERE Agency_Name = (?)");
                ps.setString(1, lookup_name);
            }

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String agency_name = resultSet.getString(1);
                String agency_address = resultSet.getString(2);
                String agency_description = resultSet.getString(3);
                double agency_rating = resultSet.getDouble(4);
                ps.close();
                return new AgencyModel(agency_name, agency_address, agency_description, agency_rating);
            }

            lasterr = "Result not found";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public AgencyModel getAgencyByAgentID (int agent_id) {
        System.out.println("Retrieving agency by agent_id = " + agent_id);
        AgentModel agent = getAgentByID(agent_id);
        return getAgencyByName(agent.getAgencyName());
    }
}
