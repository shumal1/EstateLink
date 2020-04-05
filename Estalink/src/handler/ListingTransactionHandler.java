package handler;

import model.ListingModel;
import model.PropertyModel;
import types.ListingType;
import types.PropertyType;

import javax.swing.*;

public interface ListingTransactionHandler extends TransactionHandler {
    // Insert listing, update listing
    // Search listing
        // Address
        // Price
        // Percentage change (Investor toolkit)
    // Search property
        // Address
        // Type
        // Resource
        // Community

    String insertPropertyListing(String property_address, PropertyType property_type, String property_dimension,
                                 String property_postalCode, boolean isDuplex, String property_apartmentNumber, int capacity,
                                 int listing_id, int listing_price, ListingType listing_type, int agent_id);
    String updateListing(int listing_id, int new_price);
    boolean deleteListing(int listing_id);

    // note that this one should use LIKE %address% to perform match, so empty string is treated as wildcard
    JTable getListingByCondition(int id, int price, boolean higher, ListingType type);
    // ListingModel[] getListingByPercentageChange(double percentage, boolean higher);

    JTable getPropertyByCondition(String address, PropertyType type);
    JTable getPropertyByListing(int id);
    JTable getListingByProperty(String address);
}
