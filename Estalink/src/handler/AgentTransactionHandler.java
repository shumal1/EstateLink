package handler;

import javax.swing.*;

public interface AgentTransactionHandler extends TransactionHandler{
    JTable selectAgentByID(String id);
    JTable selectAgencyByName(String id);
    JTable selectAgencyByID(String id);
    JTable selectAllAgencyEmployee();
    JTable selectAllAgentListing();
    String insertAgent(String agent_name, String agent_phoneNumber, String agent_agencyName);

}
