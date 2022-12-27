package db2.mappers;

import db2.Orders;

public class OrdersMapper extends BaseMapper<Orders> {

    @Override
    protected Class<Orders> getType() {
        return Orders.class;
    }

    @Override
    public String getTableName() {
        return "Orders";
    }
}