package db2.mappers;

import db2.Clients;

public class ClientsMapper extends BaseMapper<Clients> {

    @Override
    protected Class<Clients> getType() {
        return Clients.class;
    }

    @Override
    public String getTableName() {
        return "Clients";
    }
}