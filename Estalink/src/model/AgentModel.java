package model;

public class AgentModel {
    private String agent_agencyName;
    private int agent_ID;
    private String agent_name;
    private String agent_phoneNumber;

    public AgentModel(String agent_agencyName, int agent_ID, String agent_name, String agent_phoneNumber) {
        this.agent_agencyName = agent_agencyName;
        this.agent_ID = agent_ID;
        this.agent_name = agent_name;
        this.agent_phoneNumber = agent_phoneNumber;
    }

    public String getAgencyName() {
        return agent_agencyName;
    }

    public int getAgentID() {
        return agent_ID;
    }

    public String getAgentName() {
        return agent_name;
    }

    public String getPhoneNumber() {
        return agent_phoneNumber;
    }

}
