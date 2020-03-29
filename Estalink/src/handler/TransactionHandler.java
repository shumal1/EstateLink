package handler;

import javax.swing.*;

public interface TransactionHandler {
    public JTable selectAgentByID(String id);
    public JTable selectAgencyByName(String id);
    public boolean insertAgent();
}
