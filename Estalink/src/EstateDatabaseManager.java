import model.ListingType;
import model.PropertyModel;

import java.sql.*;
import java.util.StringJoiner;

public class EstateDatabaseManager {
    // responsible for creating connections, populating the tables, etc, and execute query.
    private static volatile EstateDatabaseManager instance;
    private Connection connection;

    public static EstateDatabaseManager getInstance() {
        synchronized(EstateDatabaseManager.class) {
            if (instance == null) {
                instance = new EstateDatabaseManager();
            }
        }
        return instance;
    }

    private EstateDatabaseManager(){
        // establish database connection
        InitializeConnection();
    }

    private void InitializeConnection(){
        System.out.println("Establishing connection");
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:stu","ora_shumalll", "a28309169");
            System.out.println("Connected to jdbc:oracle:thin:@localhost:1522:stu, ora_shumalll");
        } catch (Exception e) {
            // silently ignore (for now)
            System.out.println(e);
        }
    }

    private void ExecuteSQLStatement(String query){
        System.out.println("Executing " + query);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                StringJoiner st = new StringJoiner("|");
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    int type = resultSetMetaData.getColumnType(i);
                    if (type == Types.VARCHAR || type == Types.CHAR) {
                        st.add(resultSet.getString(i));
                    } else {
                        st.add(String.valueOf(resultSet.getLong(i)));
                    }
                }
                System.out.println(st.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int InsertListing(int listingID, int agentID, int listingPrice, ListingType listingType){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO listing VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, listingID);
            ps.setInt(2, agentID);
            ps.setInt(3, listingPrice);
            ps.setString(5, listingType.name());

            ps.executeUpdate();
            connection.commit();

            ps.close();

            return 0;
        } catch (SQLException e) {
            return e.getErrorCode();
        }
    }

    public int InsertProperty(PropertyModel propertyModel) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO property VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, propertyModel.getAddress());
            ps.setString(2, propertyModel.getDimension());
            ps.setString(3, propertyModel.getPostalCode());
            ps.setInt(4, propertyModel.getListingID());
            ps.setBoolean(5, propertyModel.isDuplex());
            ps.setInt(6, propertyModel.getApartmentNumber());
            ps.setInt(7,propertyModel.getCapacity());
            ps.setString(8, propertyModel.getType().name());

            ps.executeUpdate();
            connection.commit();

            ps.close();

            return 0;
        } catch (SQLException e) {
            return e.getErrorCode();
        }
    }
}
