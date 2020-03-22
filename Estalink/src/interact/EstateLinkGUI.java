package interact;

public class EstateLinkGUI {
    public static void main(String[] args){
        // Performs logon and stuff
            // Change mode and get a new event handler upon changing account type.
        EstateDatabaseManager manager = EstateDatabaseManager.getInstance();
        System.out.println(manager.getAgentConnector().getAgencyByAgentID(1).getAgency_name());
    }
}
