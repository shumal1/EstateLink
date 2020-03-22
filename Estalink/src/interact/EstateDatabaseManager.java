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
    private static EstateDatabaseManager instance = null;
    private AccountMode mode;
    private Connection connection;
    private AgentConnector agentConnector;
    private ResourcesConnector resourcesConnector;
    private PropertyConnector propertyConnector;
    private ListingConnector listingConnector;
    private final String ADMIN_UID = "ADMIN";
    private final String ADMIN_PWD = "cs304";

    public static synchronized EstateDatabaseManager getInstance() {
        if (instance == null) {
            instance = new EstateDatabaseManager();
            instance.setupConnectors();
        }
        return instance;
    }

    private void setupConnectors() {
        // don't do them in the constructor -- causes synchronization issue.
        this.agentConnector = new AgentConnector();
        this.resourcesConnector = new ResourcesConnector();
        this.propertyConnector = new PropertyConnector();
        this.listingConnector = new ListingConnector();
    }

    private EstateDatabaseManager(){
        // establish database connection
        InitializeConnection();
        // initialize tables with drop table

        this.mode = AccountMode.ADMIN;
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

    public Connection getConnection() {
        return this.connection;
    }


    public AgentConnector getAgentConnector() {
        return agentConnector;
    }

    public ResourcesConnector getResourcesConnector() {
        return resourcesConnector;
    }

    public PropertyConnector getPropertyConnector() {
        return propertyConnector;
    }

    public ListingConnector getListingConnector() {
        return listingConnector;
    }
}
