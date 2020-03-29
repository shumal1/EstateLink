package model;

public class AgencyModel implements Model{
    private String agency_name;
    private String agency_address;
    private String agency_description;
    private double agency_rating;

    public AgencyModel(String agency_name, String agency_address, String agency_description, double agency_rating) {
        this.agency_name = agency_name;
        this.agency_address = agency_address;
        this.agency_description = agency_description;
        this.agency_rating = agency_rating;
    }


    public String getAgency_name() {
        return agency_name;
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

    public String[] getData(){
        return new String[]{agency_name, agency_address, agency_description, Double.toString(agency_rating)};
    }

    public String[] getFieldNames(){
        return new String[]{"agency_name", "agency_address", "agency_description", "agency_rating"};
    }


}
