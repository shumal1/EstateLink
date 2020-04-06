package model;

import types.ListingType;

public class AgentListingModel implements Model {
    private int agent_ID;
    private String agent_name;
    private String agent_agencyName;
    private String agent_phoneNumber;
    private int listing_ID;
    private int listing_Price;
    private int listing_HistPrice;
    private ListingType listing_type;

    public AgentListingModel(int agent_ID, String agent_name, String agent_agencyName, String agent_phoneNumber, int listing_ID, int listing_Price, int listing_HistPrice, ListingType listing_type) {
        this.agent_ID = agent_ID;
        this.agent_name = agent_name;
        this.agent_agencyName = agent_agencyName;
        this.agent_phoneNumber = agent_phoneNumber;
        this.listing_ID = listing_ID;
        this.listing_Price = listing_Price;
        this.listing_HistPrice = listing_HistPrice;
        this.listing_type = listing_type;
    }

    @Override
    public String[] getData() {
        return new String[]{String.valueOf(agent_ID), agent_name, agent_agencyName, agent_phoneNumber,
                String.valueOf(listing_ID), String.valueOf(listing_Price), String.valueOf(listing_HistPrice), listing_type.name()};
    }

    @Override
    public String[] getFieldNames() {
        return new String[]{"agent_ID", "agent_name", "agent_agencyName", "agent_phoneNumber",
                "listing_ID", "listing_Price", "listing_HistPrice", "listing_type"};
    }

}
