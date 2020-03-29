package model;

import types.PropertyType;

public class PropertyModel {
    private String property_address;
    private String property_dimension;
    private String property_postalCode;
    private int property_listingID;
    private boolean property_isDuplex;
    private int property_apartmentNumber;
    private int property_capacity;
    private PropertyType proeprty_type;

    public PropertyModel(String address, String dimension, String postalCode,
                         int listingID, boolean isDuplex, int apartmentNumber,
                         int capacity, PropertyType type){
        this.property_address = address;
        this.property_dimension = dimension;
        this.property_postalCode = postalCode;
        this.property_listingID = listingID;
        this.proeprty_type = type;
        switch (type) {
            case House:
                this.property_isDuplex = isDuplex;
                break;
            case Apartment:
                this.property_apartmentNumber = apartmentNumber;
                break;
            case Office:
                this.property_capacity = capacity;
                break;
            default:
                // throw some exceptions here
        }
    }

    public String getAddress() {
        return property_address;
    }

    public String getDimension() {
        return property_dimension;
    }

    public String getPostalCode() {
        return property_postalCode;
    }

    public int getListingID() {
        return property_listingID;
    }

    public boolean isDuplex() {
        return property_isDuplex;
    }

    public int getApartmentNumber() {
        return property_apartmentNumber;
    }

    public int getCapacity() {
        return property_capacity;
    }

    public PropertyType getType() {
        return proeprty_type;
    }

    public String[] getData(){
        return new String[]{property_address, property_dimension, property_postalCode, Integer.toString(property_listingID),
                String.valueOf(property_isDuplex), Integer.toString(property_apartmentNumber),
                Integer.toString(property_capacity), proeprty_type.name()};
    }

    public String[] getFieldNames(){
        return new String[]{"property_address", "property_dimension", "property_postalCode", "property_listingID",
                "property_isDuplex", "property_apartmentNumber", "property_capacity", proeprty_type.name()};
    }
}
