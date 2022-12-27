package db2.mappers;

import db2.MovingInfo;

public class MovingInfoMapper extends BaseMapper<MovingInfo> {

    @Override
    protected Class<MovingInfo> getType() {
        return MovingInfo.class;
    }

    @Override
    public String getTableName() {
        return "MovingInfo";
    }
}
