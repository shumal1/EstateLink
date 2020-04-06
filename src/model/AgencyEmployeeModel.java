package model;

public class AgencyEmployeeModel implements Model {
    private String agent_agencyName;
    private int agent_ID;
    private String agent_name;
    private String agent_phoneNumber;
    private String agency_address;
    private String agency_description;
    private double agency_rating;


    public AgencyEmployeeModel(String agent_agencyName, int agent_ID, String agent_name, String agent_phoneNumber, String agency_address, String agency_description, double agency_rating) {
        this.agent_agencyName = agent_agencyName;
        this.agent_ID = agent_ID;
        this.agent_name = agent_name;
        this.agent_phoneNumber = agent_phoneNumber;
        this.agency_address = agency_address;
        this.agency_description = agency_description;
        this.agency_rating = agency_rating;
    }

    public String getAgent_agencyName() {
        return agent_agencyName;
    }

    public int getAgent_ID() {
        return agent_ID;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public String getAgent_phoneNumber() {
        return agent_phoneNumber;
    }

    public String getAgency_address() {
        return agency_address;
    }

    public String getAgency_description() {
        return agency_description;
    }

    public double getAgency_rating() {
        return agency_rating;
    }

    @Override
    public String[] getData() {
        return new String[]{agent_agencyName, String.valueOf(agent_ID), agent_name, agent_phoneNumber,
                agency_address, agency_description, Double.toString(agency_rating)};
    }

    @Override
    public String[] getFieldNames() {
        return new String[]{"agent_agencyName", "agent_ID", "agent_name", "agent_phoneNumber",
                "agency_address", "agency_description", "agency_rating"};
    }
}
