package model;

public class StatisticsModel implements Model {
    private int stat_count;
    private int stat_maxPrice;
    private int stat_minPrice;
    private int stat_sumPrice;

    public StatisticsModel(int stat_count, int stat_minPrice, int stat_maxPrice, int stat_sumPrice) {
        this.stat_count = stat_count;
        this.stat_minPrice = stat_minPrice;
        this.stat_maxPrice = stat_maxPrice;
        this.stat_sumPrice = stat_sumPrice;
    }

    public int getStat_count() {
        return stat_count;
    }

    public int getStat_maxPrice() {
        return stat_maxPrice;
    }

    public int getStat_minPrice() {
        return stat_minPrice;
    }

    public int getStat_sumPrice() {
        return stat_sumPrice;
    }

    @Override
    public String[] getData() {
        return new String[]{String.valueOf(stat_count), String.valueOf(stat_minPrice), String.valueOf(stat_maxPrice), String.valueOf(stat_sumPrice)};
    }

    @Override
    public String[] getFieldNames() {
        return new String[]{"stat_count", "stat_minPrice", "stat_maxPrice", "stat_sumPrice"};
    }
}
