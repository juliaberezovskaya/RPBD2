package db2.mappers;

import db2.Stuff;

public class StuffMapper extends BaseMapper<Stuff> {

    @Override
    protected Class<Stuff> getType() {
        return Stuff.class;
    }

    @Override
    public String getTableName() {
        return "Stuff";
    }
}