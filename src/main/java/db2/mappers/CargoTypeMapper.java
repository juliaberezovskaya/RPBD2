package db2.mappers;

import db2.MyFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import db2.CargoType;

import java.util.List;
public class CargoTypeMapper extends BaseMapper<CargoType> {

//    public List<CargoType> findAllByName(Object name){
//        return findByField(".byName", name);
//    }

    @Override
    protected Class<CargoType> getType() {
        return CargoType.class;
    }

    @Override
    public String getTableName() {
        return "CargoType";
    }
}
