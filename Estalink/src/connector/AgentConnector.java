package connector;

import types.AccountMode;
import model.AgencyModel;
import model.AgentModel;

public class AgentConnector extends Connector{
    // Access to Agency and AgencyEmployee
        // Methods:
            // registerAgent (admin mode)
            // getAgentByID (returns agent object)
            // getAgencyByName (returns agency object)
            // getAgencyByAgentID (returns name of agency for the specific agent)

    public boolean checkAgentExists(String username, String password) {
        // Select Count(*) from Agency_Employee where Agent_Name = username and Agent_ID = password;
        // TODO: Implement this
        return false;
    }

    public boolean registerAgent () {
        if (!checkMode(AccountMode.ADMIN)) {
            this.lasterr = "Insufficient privilege, ADMIN mode is required to registerAgent";
            return false;
        }

        // TODO: Implement this
        return false;
    }

    public AgentModel getAgentByID (int agent_id) {
        // TODO: Implement this
        return null;
    }

    public AgencyModel getAgencyByName (String agency_name) {
        // TODO: Implement this
        return null;
    }

    public AgencyModel getAgencyByAgentID (int agent_id) {
        // TODO: Implement this
        return null;
    }
}
