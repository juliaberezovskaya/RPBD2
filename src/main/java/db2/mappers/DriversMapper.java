package db2.mappers;

import db2.Drivers;

public class DriversMapper extends BaseMapper<Drivers> {

    @Override
    protected Class<Drivers> getType() {
        return Drivers.class;
    }

    @Override
    public String getTableName() {
        return "Drivers";
    }
}
