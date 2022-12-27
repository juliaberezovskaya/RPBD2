package db2.mappers;

import db2.Condition;

public class ConditionMapper extends BaseMapper<Condition> {

    @Override
    protected Class<Condition> getType() {
        return Condition.class;
    }

    @Override
    public String getTableName() {
        return "Condition";
    }
}