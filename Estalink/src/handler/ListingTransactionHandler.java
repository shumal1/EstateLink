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

    String insertListing(int listing_Price, int agent_id, String type);
    String updateListing(int listing_id, int new_price);
    boolean deleteListing(int listing_id);

    // note that this one should use LIKE %address% to perform match, so empty string is treated as wildcard
    JTable getListingByCondition(int id, int price, boolean higher, ListingType type);
    // ListingModel[] getListingByPercentageChange(double percentage, boolean higher);

    JTable getPropertyByCondition(String address, PropertyType type);
}
