package model;

import types.HospitalType;
import types.ResourceType;

public class ResourceModel {
    private int resource_ID;
    private String resource_name;
    private ResourceType resource_type;
    private BusType bus_type;
    private String park_description;
    private HospitalType hospital_type;

    public ResourceModel(int resource_ID, String resource_name, ResourceType resource_type, BusType bus_type, String park_description, HospitalType hospital_type) {
        this.resource_ID = resource_ID;
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.bus_type = bus_type;
        this.park_description = park_description;
        this.hospital_type = hospital_type;
    }


    public int getResource_ID() {
        return resource_ID;
    }

    public String getResource_name() {
        return resource_name;
    }

    public ResourceType getResource_type() {
        return resource_type;
    }

    public BusType getBus_type() {
        return bus_type;
    }

    public String getPark_description() {
        return park_description;
    }

    public HospitalType getHospital_type() {
        return hospital_type;
    }


}
