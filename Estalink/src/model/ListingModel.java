package model;

import types.ListingType;

public class ListingModel implements Model{
    private int listing_ID;
    private int listing_Price;
    private int listing_HistPrice;
    private int listing_agentID;
    private ListingType listing_type;

    public ListingModel(int listing_ID, int listing_Price, int listing_HistPrice, int agent_ID, ListingType type) {
        this.listing_ID = listing_ID;
        this.listing_Price = listing_Price;
        this.listing_HistPrice = listing_HistPrice;
        this.listing_agentID = agent_ID;
        this.listing_type = type;
    }

    public int getListingID() {
        return listing_ID;
    }

    public int getListingPrice() {
        return listing_Price;
    }

    public int getHistPrice() {
        return listing_HistPrice;
    }

    public int getAgentID() {
        return listing_agentID;
    }

    public ListingType getListingType() {
        return listing_type;
    }


    public String[] getData(){
        return new String[]{Integer.toString(listing_ID), Integer.toString(listing_Price),
                Integer.toString(listing_HistPrice), Integer.toString(listing_agentID), listing_type.name()};
    }

    public String[] getFieldNames(){
        return new String[]{"listing_ID", "listing_Price", "listing_HistPrice", "listing_agentID", "listing_type"};
    }
}
