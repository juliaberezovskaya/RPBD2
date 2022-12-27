package db2.mappers;

        import db2.Cars;

public class CarsMapper extends BaseMapper<Cars> {

    @Override
    protected Class<Cars> getType() {
        return Cars.class;
    }

    @Override
    public String getTableName() {
        return "Cars";
    }
}