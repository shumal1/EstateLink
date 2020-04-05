package model;

import types.ListingType;

public class GroupStatisticsModel implements Model {
    ListingType group_type;
    int group_count;
    double group_avg;
    int group_min;
    int group_max;

    public GroupStatisticsModel(ListingType type, int count, double avg, int min, int max) {
        this.group_type = type;
        this.group_count = count;
        this.group_avg = avg;
        this.group_min = min;
        this.group_max = max;
    }

    @Override
    public String[] getData() {
        return new String[]{group_type.name(), String.valueOf(group_count), String.valueOf(group_avg), String.valueOf(group_min), String.valueOf(group_max)};
    }

    @Override
    public String[] getFieldNames() {
        return new String[]{"group_name", "group_count", "group_avg", "group_min", "group_max"};
    }
}
