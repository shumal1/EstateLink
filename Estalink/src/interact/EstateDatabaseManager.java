package interact;

import connector.AgentConnector;
import connector.ListingConnector;
import connector.PropertyConnector;
import connector.ResourcesConnector;
import types.AccountMode;
import model.AgentModel;
import types.ListingType;
import model.PropertyModel;

import java.sql.*;
import java.util.StringJoiner;

public class EstateDatabaseManager {
    // responsible for creating connections, populating the tables, etc, and execute query.
    private static volatile EstateDatabaseManager instance;
    private AccountMode mode;
    private Connection connection;
    private AgentConnector agentConnector;
    private ResourcesConnector resourcesConnector;
    private PropertyConnector propertyConnector;
    private ListingConnector listingConnector;
    private final String ADMIN_UID = "ADMIN";
    private final String ADMIN_PWD = "cs304";

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
        // initialize tables with drop table

        this.agentConnector = new AgentConnector();
        this.resourcesConnector = new ResourcesConnector();
        this.propertyConnector = new PropertyConnector();
        this.listingConnector = new ListingConnector();
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
        // Sample selection query
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

    // Only admin can perform this
    private int InsertAgent(AgentModel agentModel) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO agent VALUES (?, ?, ?)");
            ps.setString(1, agentModel.getAgencyName());
            ps.setInt(2, agentModel.getAgentID());
            ps.setString(3, agentModel.getAgentName());

            ps.executeUpdate();

            ps = connection.prepareStatement("INSERT INTO agentPhonebook VALUES (?, ?)");
            ps.setString(1, agentModel.getAgentName());
            ps.setString(2, agentModel.getPhoneNumber());

            ps.executeUpdate();
            connection.commit();
            ps.close();

            return 0;
        } catch (SQLException e) {
            return e.getErrorCode();
        }
    }

    public boolean changeMode(AccountMode mode, String username, String password) {
        switch(mode) {
            case ADMIN:
                if (!username.equals(ADMIN_UID) || !password.equals(ADMIN_PWD)) {
                    return false;
                }
                this.mode = AccountMode.ADMIN;
                break;
            case AGENT:
                // in this case username is agent_name, password is agent_id
                if (!this.agentConnector.checkAgentExists(username, password)) {
                    return false;
                }
                this.mode = AccountMode.AGENT;
                break;
            case GUEST:
                this.mode = AccountMode.GUEST;
            default:
                // unsupported type
                break;
        }

        return true;
    }

    public AccountMode getCurrentMode() {
        return this.mode;
    }
}
