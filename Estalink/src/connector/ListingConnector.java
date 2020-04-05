package connector;

import model.ListingModel;
import model.PropertyModel;
import model.ResourceModel;
import types.AccountMode;
import types.ListingType;
import types.PropertyType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListingConnector extends Connector{
    // Insert, update, delete on Listing

    public boolean InsertListing(ListingModel listingModel) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Listing VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, listingModel.getListingID());
            ps.setInt(2, listingModel.getListingPrice());
            ps.setInt(3, listingModel.getHistPrice());
            ps.setInt(4, listingModel.getAgentID());
            ps.setString(5, listingModel.getListingType().toString());

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public boolean UpdateListingPrice(int listing_id, int newPrice) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps1 = connection.prepareStatement("SELECT listing_price FROM listing WHERE listing_id = (?)");
            ps1.setInt(1, listing_id);
            ResultSet resultSet = ps1.executeQuery();
            int currentPrice = 0;
            if (resultSet.next()) {
                currentPrice = resultSet.getInt(1);
            }

            PreparedStatement ps2 = connection.prepareStatement("UPDATE Listing SET listing_price = (?)," +
                    " historical_price = (?) WHERE listing_id = (?)");
            ps2.setInt(1, newPrice);
            ps2.setInt(2, currentPrice);
            ps2.setInt(3, listing_id);

            ps2.executeUpdate();
            ps2.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public boolean UpdateListingAgent(int listing_id, int agentID) {
        // call updateManagementForProperty here
        if (!checkMode(AccountMode.ADMIN)) {
            return false;
        }

        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Listing SET agent_id = (?) WHERE listing_id = (?)");
            ps.setInt(1, agentID);
            ps.setInt(2, listing_id);

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }


    public boolean deleteListing(int listing_id) {
        Connection connection = this.manager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Listing WHERE listing_id = (?)");
            ps.setInt(1, listing_id);

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return false;
        }
    }

    public ListingModel getListingByID(int id){
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT * FROM listing WHERE listing_id = " + id);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM listing WHERE listing_id = (?)");
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int listing_id = resultSet.getInt(1);
                int listing_price = resultSet.getInt(2);
                int listing_histprice = resultSet.getInt(3);
                int agent_id = resultSet.getInt(4);
                ListingType type = ListingType.valueOf(resultSet.getString(5));

                ps.close();
                ListingModel listingModel = new ListingModel(listing_id, listing_price, listing_histprice, agent_id, type);
                return listingModel;
            }
            lasterr = "There is no listing with this id";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel[] getListingsByType(ListingType listingType) {
        Connection connection = this.manager.getConnection();
        try {
            ListingModel[] listingModels = new ListingModel[999];
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Listing WHERE listing_type = (?)");

            String type = "RENTAL";
            if (listingType == ListingType.SELLING)
                type = "SELLING";

            ps.setString(1, type);
            ResultSet resultSet = ps.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                listingModels[i] = getListingModel(resultSet);
                i++;
            }

            return listingModels;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel[] getListingByPrice(ListingType type, int price) {
        Connection connection = this.manager.getConnection();
        try {
            ListingModel[] listingModels = new ListingModel[999];
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Listing WHERE listing_price = (?) AND" +
                    " listing_type = (?)");
            ps.setInt(1, price);
            ps.setString(2, type.toString());
            ResultSet resultSet = ps.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                listingModels[i] = getListingModel(resultSet);
                i++;
            }

            return listingModels;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel[] getListingByPercentageChange(ListingType type, double priceChange, boolean isGreater) {
        Connection connection = this.manager.getConnection();
        try {
            ListingModel[] listingModels = new ListingModel[999];
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Listing " +
                    "WHERE listing_type = (?) AND listing_pirce/historical_price > (?)");

            if (!isGreater){
                ps = connection.prepareStatement("SELECT * FROM Listing " +
                        "WHERE listing_type = (?) AND listing_pirce/historical_price <= (?)");
            }

            ps.setString(1, type.toString());
            ps.setDouble(2, priceChange);
            ResultSet resultSet = ps.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                listingModels[i] = getListingModel(resultSet);
                i++;
            }
            return listingModels;

        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel selectListingByProperty(String property_address) {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT listing_id, listing_price, historical_price, agent_id, listing_type FROM listing JOIN property WHERE property_address = " + property_address);
            PreparedStatement ps = connection.prepareStatement("SELECT listing_id, listing_price, historical_price, agent_id, listing_type FROM listing NATURAL JOIN property WHERE property_address = ?");
            ps.setString(1, property_address);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int listing_id = resultSet.getInt(1);
                int listing_price = resultSet.getInt(2);
                int listing_histprice = resultSet.getInt(3);
                int agent_id = resultSet.getInt(4);
                ListingType type = ListingType.valueOf(resultSet.getString(5));

                ps.close();
                ListingModel listingModel = new ListingModel(listing_id, listing_price, listing_histprice, agent_id, type);
                return listingModel;
            }
            lasterr = "There is no listing with this id";
            return null;
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel[] selectListingByCondition(String id, String price, boolean higher, ListingType type) {
        Connection connection = this.manager.getConnection();
        ArrayList<ListingModel> models = new ArrayList<>();
        try {
            String typeCondition = "";
            if (type != ListingType.ANY)
                typeCondition = "AND listing_type = " + type.toString();
            PreparedStatement ps;
            if (!id.equals("") && !price.equals("")) {
                if (higher) {
                    ps = connection.prepareStatement("SELECT * FROM listing WHERE listing_id = ? AND listing_price > ? " + typeCondition);
                } else {
                    ps = connection.prepareStatement("SELECT * FROM listing WHERE listing_id = ? AND listing_price < ? " + typeCondition);
                }
                ps.setInt(1, Integer.parseInt(id));
                ps.setInt(2, Integer.parseInt(price));
            } else if (!id.equals("")) {
                ps  = connection.prepareStatement("SELECT * FROM listing WHERE listing_id = ?" + typeCondition);
                ps.setInt(1, Integer.parseInt(id));
            } else if (!price.equals("")){
                ps  = connection.prepareStatement("SELECT * FROM listing WHERE listing_price ? ? " + typeCondition);
                if (higher) {
                    ps.setString(1, ">");
                } else {
                    ps.setString(1, "<");
                }
                ps.setInt(2, Integer.parseInt(price));
            } else {
                ps  = connection.prepareStatement("SELECT * FROM listing " + typeCondition);
            }

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int listing_id = resultSet.getInt(1);
                int listing_price = resultSet.getInt(2);
                int listing_histprice = resultSet.getInt(3);
                int agentID = resultSet.getInt(4);
                ListingType listing_type = ListingType.valueOf(resultSet.getString(5));

                ListingModel listingModel = new ListingModel(listing_id, listing_price, listing_histprice, agentID, listing_type);
                models.add(listingModel);
            }
            ps.close();
            return models.toArray(new ListingModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    public ListingModel[] selectListingByResource(int id) {
        Connection connection = this.manager.getConnection();
        ArrayList<ListingModel> models = new ArrayList<>();

        try {
            System.out.println("Executing select * from listing where listing_id in (select listing_id from has_property_and_resources where resource_id  = " + id);
            PreparedStatement ps = connection.prepareStatement("select * from listing where listing_id in (select listing_id from has_property_and_resources where resource_id = (?))");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                models.add(getListingModel(resultSet));
            }

            return models.toArray(new ListingModel[0]);
        } catch (SQLException e) {
            lasterr = e.getMessage();
            return null;
        }
    }

    private ListingModel getListingModel(ResultSet resultSet) throws  SQLException {
        int id = resultSet.getInt(1);
        int price = resultSet.getInt(2);
        int historical_price = resultSet.getInt(3);
        int agent_id = resultSet.getInt(4);
        String type = resultSet.getString(5);
        ListingType listingType = ListingType.SELLING;
        if (type.equals("RENTAL"))
            listingType = ListingType.RENTAL;
        return new ListingModel(id, price, historical_price, agent_id, listingType);

    }

    //Helper Method requested by Jason
    public int getNextListingID() {
        Connection connection = this.manager.getConnection();
        try {
            System.out.println("Executing SELECT MAX(listing_id) FROM Listing");
            PreparedStatement ps = connection.prepareStatement("SELECT MAX(listing_id) FROM Listing");

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
