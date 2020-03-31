package handler;
import types.ResourceType;

import javax.swing.*;

public interface ResourceTransactionHandler extends TransactionHandler{
    JTable getResourceByType(ResourceType type);
    JTable getPropertyByResourceType(ResourceType type);
    JTable getPropertyWithResourceID(int id);
}
